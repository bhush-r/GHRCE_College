package bhushan.org.GHRCEUSER.chating;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhushan.org.GHRCEUSER.R;

public class chat extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageEditText;
    private ImageButton sendButton, attachmentButton;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private DatabaseReference chatDatabaseReference;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private String currentUserName;
    private String currentUserId;

    private final int REQ = 1;
    private Uri attachmentUri;
    private String attachmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Discussion Chat");

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        currentUserId = currentUser.getUid(); // Get current user ID
        currentUserName = getIntent().getStringExtra("userName");

        chatDatabaseReference = FirebaseDatabase.getInstance().getReference().child("chats").child("chat_room_1");
        storageReference = FirebaseStorage.getInstance().getReference().child("chat_attachments");

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        attachmentButton = findViewById(R.id.attachmentButton);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, currentUserId); // Pass currentUserId to adapter
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(messageAdapter);

        loadMessages();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        attachmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilePicker();
            }
        });
    }

    private void loadMessages() {
        chatDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    messageList.add(message);
                }
                messageAdapter.notifyDataSetChanged();
                chatRecyclerView.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(chat.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        String messageText = messageEditText.getText().toString();
        if (TextUtils.isEmpty(messageText) && attachmentUri == null) {
            Toast.makeText(this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
            return;
        }

        String senderId = currentUser.getUid();

        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("senderId", senderId);
        messageMap.put("senderName", currentUserName); // Here we use the username obtained from the intent
        messageMap.put("messageText", messageText);
        messageMap.put("timestamp", System.currentTimeMillis());

        if (attachmentUri != null) {
            uploadAttachment(messageMap);
        } else {
            chatDatabaseReference.push().setValue(messageMap);
            messageEditText.setText("");
        }
    }

    private void uploadAttachment(Map<String, Object> messageMap) {
        StorageReference reference = storageReference.child(attachmentName);
        reference.putFile(attachmentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        messageMap.put("attachmentUrl", uri.toString());
                        chatDatabaseReference.push().setValue(messageMap);
                        messageEditText.setText("");
                        attachmentUri = null;
                        attachmentName = null;
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(chat.this, "Failed to upload attachment", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Attachment"), REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK && data != null && data.getData() != null) {
            attachmentUri = data.getData();

            if (attachmentUri.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(attachmentUri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (columnIndex != -1) {
                            attachmentName = cursor.getString(columnIndex);
                        } else {
                            Toast.makeText(this, "Failed to get file name", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error reading file information", Toast.LENGTH_SHORT).show();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            } else if (attachmentUri.toString().startsWith("file://")) {
                attachmentName = new File(attachmentUri.toString()).getName();
            }

            if (attachmentName != null) {
                Toast.makeText(this, "Attachment selected: " + attachmentName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to select attachment", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
