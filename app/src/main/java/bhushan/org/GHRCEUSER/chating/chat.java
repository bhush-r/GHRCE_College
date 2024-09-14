//package bhushan.org.GHRCEUSER.chating;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import bhushan.org.GHRCEUSER.R;
//import bhushan.org.GHRCEUSER.menuitems.Profile;
//
//public class chat extends AppCompatActivity {
//
//    private RecyclerView chatRecyclerView;
//    private EditText messageEditText;
//    private Button sendButton;
//    private DatabaseReference chatDatabaseReference;
//    private FirebaseUser currentUser;
//    private List<Message> messageList;
//    private MessageAdapter messageAdapter;
//    private String currentUserId;
//    private String currentUserName; // Added to store the username
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        // Get the user's name from the intent
//        Intent intent = getIntent();
//        currentUserName = intent.getStringExtra("userName");
//
//        chatRecyclerView = findViewById(R.id.chatRecyclerView);
//        messageEditText = findViewById(R.id.messageEditText);
//        sendButton = findViewById(R.id.sendButton);
//
//        currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        currentUserId = currentUser.getUid();
//        chatDatabaseReference = FirebaseDatabase.getInstance().getReference().child("chats").child("chat_room_1");
//
//        messageList = new ArrayList<>();
//        messageAdapter = new MessageAdapter(messageList, currentUserId);
//        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        chatRecyclerView.setAdapter(messageAdapter);
//
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendMessage();
//            }
//        });
//
//        loadMessages();
//    }
//
//    private void sendMessage() {
//        String messageText = messageEditText.getText().toString();
//        if (messageText.isEmpty()) {
//            Toast.makeText(this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Use the currentUserName as senderName in the message
//        Message message = new Message(
//                currentUserId,
//                currentUserName, // Use the fetched username here
//                currentUser.getEmail(),
//                messageText,
//                null,
//                System.currentTimeMillis()
//        );
//
//        chatDatabaseReference.push().setValue(message);
//        messageEditText.setText("");
//    }
//
//    private void loadMessages() {
//        chatDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                messageList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Message message = dataSnapshot.getValue(Message.class);
//                    messageList.add(message);
//                }
//                messageAdapter.notifyDataSetChanged();
//                chatRecyclerView.scrollToPosition(messageList.size() - 1);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(chat.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
