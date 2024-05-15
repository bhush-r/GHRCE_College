package bhushan.org.GHRCEUSER.menuitems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import bhushan.org.GHRCEUSER.R;

public class Profile extends AppCompatActivity {

    private TextView pr_name, pr_email, pr_branch, pr_year, pr_sem, pr_gender,pr_phone;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        // Initialize TextViews
        pr_name = findViewById(R.id.pr_name);
        pr_email = findViewById(R.id.pr_email);
        pr_branch = findViewById(R.id.pr_branch);
        pr_year = findViewById(R.id.pr_year);
        pr_sem = findViewById(R.id.pr_sem);
        pr_gender = findViewById(R.id.pr_gender);
        pr_phone = findViewById(R.id.pr_phone);

        // Get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Get UID of the current user
            String uid = user.getUid();

            // Get reference to the user node in the database
            dbRef = FirebaseDatabase.getInstance().getReference().child("user").child(uid);

            // Add ValueEventListener to retrieve user data
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Get user data as a Map
                        Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();

                        // Set user data to TextViews
                        pr_name.setText(userData.get("name").toString());
                        pr_email.setText(userData.get("email").toString());
                        pr_branch.setText(userData.get("branch").toString());
                        pr_year.setText(userData.get("year").toString());
                        pr_sem.setText(userData.get("semester").toString());
                        pr_gender.setText(userData.get("gender").toString());
                        pr_phone.setText(userData.get("phone").toString());
                    } else {
                        // Log statement to indicate that dataSnapshot doesn't exist
                        Log.d("ProfileActivity", "Data doesn't exist");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Log statement for any database error
                    Log.d("ProfileActivity", "Database Error: " + databaseError.getMessage());
                }
            });
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
