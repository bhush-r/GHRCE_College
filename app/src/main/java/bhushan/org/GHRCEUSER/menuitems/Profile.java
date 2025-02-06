package bhushan.org.GHRCEUSER.menuitems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

import bhushan.org.GHRCEUSER.R;
import bhushan.org.GHRCEUSER.authentication.LoginActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private TextView pr_name, pr_email, pr_branch, pr_year, pr_sem, pr_gender, pr_phone;
    private CircleImageView pr_image;
    private Button logout_button;

    public static String currentUserName;
    public static String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        // Initialize Views
        pr_name = findViewById(R.id.pr_name);
        pr_email = findViewById(R.id.pr_email);
        pr_branch = findViewById(R.id.pr_branch);
        pr_year = findViewById(R.id.pr_year);
        pr_sem = findViewById(R.id.pr_sem);
        pr_gender = findViewById(R.id.pr_gender);
        pr_phone = findViewById(R.id.pr_phone);
        pr_image = findViewById(R.id.pr_image);

        logout_button = findViewById(R.id.logout_button);
        Button update_button = findViewById(R.id.update_button);

        logout_button.setOnClickListener(v -> new AlertDialog.Builder(Profile.this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    openLogin();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show());

        update_button.setOnClickListener(v ->
                Toast.makeText(Profile.this, "Coming soon...", Toast.LENGTH_SHORT).show()
        );

        // Get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean userFound = false;

                    for (DataSnapshot branchSnapshot : dataSnapshot.getChildren()) {
                        if (branchSnapshot.hasChild(uid)) {
                            userFound = true;
                            DataSnapshot userSnapshot = branchSnapshot.child(uid);
                            Map<String, Object> userData = (Map<String, Object>) userSnapshot.getValue();

                            if (userData != null) {
                                pr_name.setText(userData.get("name").toString());
                                pr_email.setText(userData.get("email").toString());
                                pr_branch.setText(userData.get("branch").toString());
                                pr_year.setText(userData.get("year").toString());
                                pr_sem.setText(userData.get("semester").toString());
                                pr_gender.setText(userData.get("gender").toString());
                                pr_phone.setText(userData.get("phone").toString());

                                currentUserName = userData.get("name").toString();
                                currentUserEmail = userData.get("email").toString();

                                if (userData.containsKey("profileImage")) {
                                    String profileImageUrl = userData.get("profileImage").toString();
                                    loadProfileImage(profileImageUrl);
                                } else {
                                    loadProfileImageFromStorage(uid);
                                }
                            }
                            break;
                        }
                    }

                    if (!userFound) {
                        Log.d("ProfileActivity", "User data not found");
                        Toast.makeText(Profile.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("ProfileActivity", "Database Error: " + databaseError.getMessage());
                    Toast.makeText(Profile.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadProfileImage(String url) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.profile) // Default profile picture
                .into(pr_image);
    }

    private void loadProfileImageFromStorage(String uid) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("ProfileImages/" + uid + ".jpg");

        storageReference.getDownloadUrl().addOnSuccessListener(uri ->
                Glide.with(Profile.this)
                        .load(uri)
                        .placeholder(R.drawable.profile)
                        .into(pr_image)
        ).addOnFailureListener(e -> Log.d("ProfileActivity", "Profile image not found: " + e.getMessage()));
    }

    private void openLogin() {
        Intent intent = new Intent(Profile.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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



