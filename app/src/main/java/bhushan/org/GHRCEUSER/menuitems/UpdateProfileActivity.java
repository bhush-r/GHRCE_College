package bhushan.org.GHRCEUSER.menuitems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import de.hdodenhof.circleimageview.CircleImageView;
import bhushan.org.GHRCEUSER.R;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText editName, editBranch, editYear, editSem, editGender, editPhone;
    private Button saveButton;
    private ImageView profileImage;
    private DatabaseReference dbRef;
    private StorageReference storageRef;
    private Uri imageUri;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // Initialize Views
        editName = findViewById(R.id.edit_name);
        editBranch = findViewById(R.id.edit_branch);
        editYear = findViewById(R.id.edit_year);
        editSem = findViewById(R.id.edit_sem);
        editGender = findViewById(R.id.edit_gender);
        editPhone = findViewById(R.id.edit_phone);
        saveButton = findViewById(R.id.save_button);
        profileImage = findViewById(R.id.profile_image);
        progressDialog = new ProgressDialog(this);

        // Firebase References
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String branch = "";
        dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(branch).child(uid);
        storageRef = FirebaseStorage.getInstance().getReference("ProfileImages/").child(uid + ".jpg");

        // Load User Data
        loadUserData();

        // Select Image from Gallery
        profileImage.setOnClickListener(view -> selectImage());

        // Save Updated Profile
        saveButton.setOnClickListener(view -> updateProfile());
    }

    private void loadUserData() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    editName.setText(snapshot.child("name").getValue(String.class));
                    editBranch.setText(snapshot.child("branch").getValue(String.class));
                    editYear.setText(snapshot.child("year").getValue(String.class));
                    editSem.setText(snapshot.child("semester").getValue(String.class));
                    editGender.setText(snapshot.child("gender").getValue(String.class));
                    editPhone.setText(snapshot.child("phone").getValue(String.class));

                    // Load Profile Image (if available)
                    String imageUrl = snapshot.child("profileImage").getValue(String.class);
                    if (imageUrl != null) {
                        Glide.with(UpdateProfileActivity.this).load(imageUrl).into(profileImage);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }

    private void updateProfile() {
        progressDialog.setMessage("Updating Profile...");
        progressDialog.show();

        String name = editName.getText().toString();
        String branch = editBranch.getText().toString();
        String year = editYear.getText().toString();
        String sem = editSem.getText().toString();
        String gender = editGender.getText().toString();
        String phone = editPhone.getText().toString();

        if (name.isEmpty() || branch.isEmpty() || year.isEmpty() || sem.isEmpty() || gender.isEmpty() || phone.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        dbRef.child("name").setValue(name);
        dbRef.child("branch").setValue(branch);
        dbRef.child("year").setValue(year);
        dbRef.child("semester").setValue(sem);
        dbRef.child("gender").setValue(gender);
        dbRef.child("phone").setValue(phone);

        if (imageUri != null) {
            uploadImage();
        } else {
            progressDialog.dismiss();
            Toast.makeText(UpdateProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void uploadImage() {
        storageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                dbRef.child("profileImage").setValue(uri.toString());
                progressDialog.dismiss();
                Toast.makeText(UpdateProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(UpdateProfileActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
        });
    }
}
