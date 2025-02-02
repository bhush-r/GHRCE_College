package bhushan.org.GHRCEUSER.Chating;

import android.os.Bundle;
import android.view.View;
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

import bhushan.org.GHRCEUSER.R;

public class PersonalChatActivity extends AppCompatActivity {

    private EditText messageInput;
    private ImageButton sendButton;
    private RecyclerView messagesRecyclerView;

    private String userId, chatPartnerId;
    private FirebaseFirestore firestore;
    private DocumentReference personalChatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat);

        // Get the chat partner ID and user ID from the intent
        userId = getIntent().getStringExtra("userId");
        chatPartnerId = getIntent().getStringExtra("chatPartnerId");

        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);

        // Initialize Firestore instance
        firestore = FirebaseFirestore.getInstance();

        // Set up Firestore reference for personal chat
        personalChatRef = firestore.collection("chats")
                .document("personalChats")
                .collection(userId)
                .document(chatPartnerId);

        // Initialize RecyclerView
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Listen for send button click
        sendButton.setOnClickListener(view -> sendMessage());
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
            personalChatRef.collection("messages").add(messageMap)
                    .addOnSuccessListener(documentReference -> {
                        messageInput.setText("");  // Clear input field
                        Toast.makeText(PersonalChatActivity.this, "Message sent!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(PersonalChatActivity.this, "Error sending message", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(PersonalChatActivity.this, "Message cannot be empty!", Toast.LENGTH_SHORT).show();
        }
    }
}