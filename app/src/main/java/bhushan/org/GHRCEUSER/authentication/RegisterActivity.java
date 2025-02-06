package bhushan.org.GHRCEUSER.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.UUID;

import bhushan.org.GHRCEUSER.MainActivity;
import bhushan.org.GHRCEUSER.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText regName, regEmail, regPassword, regPhone;
    private Spinner regBranchSpinner, regYearSpinner, regSemesterSpinner, regGenderSpinner;
    private Button register;
    private TextView openLog;
    private ImageView profile_img;

    private String name, email, pass, branch, year, sem, gender, phone, token;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private StorageReference storageReference;

    private Uri imageUri;  // Stores selected image URI

    // Declare the ProgressBar
    private ProgressBar progressBar;

    private ArrayAdapter<CharSequence> branchAdapter, yearAdapter, semesterAdapter, genderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference("ProfileImages");  // Firebase Storage path


        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPass);
        regPhone = findViewById(R.id.regPhone);

        regBranchSpinner = findViewById(R.id.regBranchSpinner);
        regYearSpinner = findViewById(R.id.regYearSpinner);
        regSemesterSpinner = findViewById(R.id.regSemesterSpinner);
        regGenderSpinner = findViewById(R.id.regGenderSpinner);

        register = findViewById(R.id.register);
        openLog = findViewById(R.id.openLog);
        profile_img = findViewById(R.id.profile_img);  // If needed, for profile image

        // Setting up adapters for spinners
        branchAdapter = ArrayAdapter.createFromResource(this, R.array.branch_options, android.R.layout.simple_spinner_item);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regBranchSpinner.setAdapter(branchAdapter);

        yearAdapter = ArrayAdapter.createFromResource(this, R.array.year_options, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regYearSpinner.setAdapter(yearAdapter);

        semesterAdapter = ArrayAdapter.createFromResource(this, R.array.semester_options, android.R.layout.simple_spinner_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regSemesterSpinner.setAdapter(semesterAdapter);

        genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_options, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regGenderSpinner.setAdapter(genderAdapter);

        // Register Button Click Listener
        register.setOnClickListener(view -> validateData());

        // Open Login Activity
        openLog.setOnClickListener(v -> openLogin());

        profile_img.setOnClickListener(v -> openGallery());

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profile_img.setImageURI(imageUri);
        }
    }


    private void openLogin() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            openMain();
        } else if (user != null) {
            Toast.makeText(this, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
        }
    }


    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void validateData() {
        name = regName.getText().toString();
        email = regEmail.getText().toString();
        phone = regPhone.getText().toString();
        pass = regPassword.getText().toString();
        branch = regBranchSpinner.getSelectedItem().toString();
        year = regYearSpinner.getSelectedItem().toString();
        sem = regSemesterSpinner.getSelectedItem().toString();
        gender = regGenderSpinner.getSelectedItem().toString();

        // Input validation for user fields
        if (name.isEmpty()) {
            regName.setError("Required");
            regName.requestFocus();
        } else if (phone.isEmpty()) {
            regPhone.setError("Required");
            regPhone.requestFocus();
        } else if (email.isEmpty()) {
            regEmail.setError("Required");
            regEmail.requestFocus();
        } else if (!isValidEmail(email)) {
            Toast.makeText(RegisterActivity.this, "Enter valid Email ID", Toast.LENGTH_SHORT).show();
            regEmail.setError("Invalid Email");
            regEmail.requestFocus();
        } else if (!isStrongPassword(pass)) {
            Toast.makeText(RegisterActivity.this, "Password must be strong (min 8 chars, 1 uppercase, 1 special char)", Toast.LENGTH_SHORT).show();
            regPassword.setError("Weak Password");
            regPassword.requestFocus();
        } else if (branch.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Select Branch", Toast.LENGTH_SHORT).show();
        } else if (year.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Select Year", Toast.LENGTH_SHORT).show();
        } else if (gender.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
        } else if (sem.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Select Semester", Toast.LENGTH_SHORT).show();
        } else {
            token = UUID.randomUUID().toString();  // Generate a unique token
            createUser();  // Proceed to user creation in Firebase
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^(.+)@(gmail\\.com|ghrce\\.raisoni\\.net)$";  // College-specific email pattern
        return email.matches(emailPattern);
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&   // At least one uppercase letter
                password.matches(".*[a-z].*") &&   // At least one lowercase letter
                password.matches(".*\\d.*") &&     // At least one digit
                password.matches(".*[!@#$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?].*");  // At least one special character
    }

    private void createUser() {
        progressBar.setVisibility(View.VISIBLE);  // Show progress bar while creating user

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);  // Hide progress bar when task is complete

                    if (task.isSuccessful()) {
                        sendEmailVerification();  // Send verification email
                        saveUserDataToDatabase();  // Save user data to Firebase Database
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveUserDataToDatabase() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference branchRef = FirebaseDatabase.getInstance().getReference().child("users").child(branch).child(uid);
            HashMap<String, Object> userData = new HashMap<>();
            userData.put("name", name);
            userData.put("email", email);
            userData.put("branch", branch);
            userData.put("year", year);
            userData.put("semester", sem);
            userData.put("phone", phone);
            userData.put("gender", gender);
            userData.put("token", token);

            // Upload profile image if selected
            if (imageUri != null) {
                StorageReference imgRef = storageReference.child(uid + ".jpg");
                imgRef.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                        imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            userData.put("profileImage", uri.toString());
                            branchRef.setValue(userData)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                            openMain();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        })
                );
            } else {
                branchRef.setValue(userData).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                        openMain();
                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void getUsersByBranch(String branch) {
        DatabaseReference branchRef = FirebaseDatabase.getInstance().getReference().child("users").child(branch);

        branchRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    // Handle user data
                    // For example, you can add the user to a list and display it in a RecyclerView
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisterActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUsersFromBranch() {
        String selectedBranch = "CSE"; // Replace with dynamic branch selection
        getUsersByBranch(selectedBranch);
    }
}


