package bhushan.org.GHRCEUSER.Chating;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.List;

import bhushan.org.GHRCEUSER.R;

public class GroupChatActivity extends AppCompatActivity {

    private EditText messageInput;
    private ImageButton sendButton;
    private RecyclerView messagesRecyclerView;

    private String branch, userId, chatId;
    private FirebaseFirestore firestore;
    private DocumentReference groupChatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        // Get the branch and userId from previous activity or shared preferences
        branch = getIntent().getStringExtra("branch");
        userId = getIntent().getStringExtra("userId");

        // Check if branch is null
        if (branch == null) {
            Toast.makeText(this, "Branch is null", Toast.LENGTH_SHORT).show();
            return;  // Exit the method if branch is null
        }

        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);

        // Initialize Firestore instance
        firestore = FirebaseFirestore.getInstance();

        // Dynamically create a unique chatId
        chatId = branch + "_" + userId;

        // Ensure chatId is not null
        if (chatId == null) {
            Toast.makeText(this, "Chat ID is null", Toast.LENGTH_SHORT).show();
            return;  // Exit the method if chatId is null
        }

        groupChatRef = firestore.collection("chats")
                .document("groupChats")
                .collection(branch)
                .document(chatId);

        // Set up RecyclerView
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Listen for send button click
        sendButton.setOnClickListener(view -> sendMessage());

        // Listen for messages in real-time
        listenForMessages();
    }

    private void sendMessage() {
        String message = messageInput.getText().toString().trim();
        if (!message.isEmpty()) {
            // Create message object
            HashMap<String, Object> messageMap = new HashMap<>();
            messageMap.put("senderId", userId);
            messageMap.put("message", message);
            messageMap.put("timestamp", FieldValue.serverTimestamp());
            messageMap.put("messageType", "text");

            // Add message to Firestore collection
            groupChatRef.collection("messages").add(messageMap)
                    .addOnSuccessListener(documentReference -> {
                        messageInput.setText("");  // Clear input field
                        Toast.makeText(GroupChatActivity.this, "Message sent!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(GroupChatActivity.this, "Error sending message", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(GroupChatActivity.this, "Message cannot be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    private void listenForMessages() {
        groupChatRef.collection("messages")
                .orderBy("timestamp")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        return;
                    }
                    if (queryDocumentSnapshots != null) {
                        List<Message> messages = queryDocumentSnapshots.toObjects(Message.class);
                        MessageAdapter adapter = new MessageAdapter(messages);
                        messagesRecyclerView.setAdapter(adapter);
                    }
                });
    }
}
