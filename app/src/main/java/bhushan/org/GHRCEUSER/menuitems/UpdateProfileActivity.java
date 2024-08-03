package bhushan.org.GHRCEUSER.menuitems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import bhushan.org.GHRCEUSER.R;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText editName, editBranch, editYear, editSem, editGender, editPhone;
    private Button saveButton;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // Initialize EditTexts and Button
        editName = findViewById(R.id.edit_name);
        editBranch = findViewById(R.id.edit_branch);
        editYear = findViewById(R.id.edit_year);
        editSem = findViewById(R.id.edit_sem);
        editGender = findViewById(R.id.edit_gender);
        editPhone = findViewById(R.id.edit_phone);
        saveButton = findViewById(R.id.save_button);

        // Get reference to the user node in the database
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        String name = editName.getText().toString();
        String branch = editBranch.getText().toString();
        String year = editYear.getText().toString();
        String sem = editSem.getText().toString();
        String gender = editGender.getText().toString();
        String phone = editPhone.getText().toString();

        if (!name.isEmpty() && !branch.isEmpty() && !year.isEmpty() && !sem.isEmpty() && !gender.isEmpty() && !phone.isEmpty()) {
            dbRef.child("name").setValue(name);
            dbRef.child("branch").setValue(branch);
            dbRef.child("year").setValue(year);
            dbRef.child("semester").setValue(sem);
            dbRef.child("gender").setValue(gender);
            dbRef.child("phone").setValue(phone);

            Toast.makeText(UpdateProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();

            // Return to the Profile activity
            Intent intent = new Intent(UpdateProfileActivity.this, Profile.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(UpdateProfileActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }
}
