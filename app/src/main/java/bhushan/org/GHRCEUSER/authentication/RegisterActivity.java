//package bhushan.org.GHRCEUSER.authentication;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.HashMap;
//import java.util.UUID;
//import bhushan.org.GHRCEUSER.MainActivity;
//import bhushan.org.GHRCEUSER.R;
//import bhushan.org.GHRCEUSER.Chating.ChatActivity;
//
//public class RegisterActivity extends AppCompatActivity {
//
//    private EditText regName, regEmail, regPassword, regPhone;
//    private Spinner regBranchSpinner, regYearSpinner, regSemesterSpinner, regGenderSpinner;
//    private Button register;
//    private TextView openLog;
//    private ImageView profile_img;
//
//    private String name, email, pass, branch, year, sem, gender, phone,token;
//
//    private FirebaseAuth auth;
//
//    private DatabaseReference reference;
//    private DatabaseReference dbRef;
//
//    // Declare the ProgressBar
//    private ProgressBar progressBar;
//
//    private ArrayAdapter<CharSequence> branchAdapter, yearAdapter, semesterAdapter, genderAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        progressBar = findViewById(R.id.progressBar);
//        auth = FirebaseAuth.getInstance();
//        reference = FirebaseDatabase.getInstance().getReference();
//
//        regName = findViewById(R.id.regName);
//        regEmail = findViewById(R.id.regEmail);
//        regPassword = findViewById(R.id.regPass);
//        regPhone = findViewById(R.id.regPhone);
//
//        regBranchSpinner = findViewById(R.id.regBranchSpinner);
//        regYearSpinner = findViewById(R.id.regYearSpinner);
//        regSemesterSpinner = findViewById(R.id.regSemesterSpinner);
//        regGenderSpinner = findViewById(R.id.regGenderSpinner);
//
//        register = findViewById(R.id.register);
//        openLog = findViewById(R.id.openLog);
//
//        // Setting up adapters for spinners
//        branchAdapter = ArrayAdapter.createFromResource(this, R.array.branch_options, android.R.layout.simple_spinner_item);
//        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        regBranchSpinner.setAdapter(branchAdapter);
//
//        yearAdapter = ArrayAdapter.createFromResource(this, R.array.year_options, android.R.layout.simple_spinner_item);
//        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        regYearSpinner.setAdapter(yearAdapter);
//
//        semesterAdapter = ArrayAdapter.createFromResource(this, R.array.semester_options, android.R.layout.simple_spinner_item);
//        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        regSemesterSpinner.setAdapter(semesterAdapter);
//
//        genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_options, android.R.layout.simple_spinner_item);
//        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        regGenderSpinner.setAdapter(genderAdapter);
//
//        register.setOnClickListener((view) -> {
//            validateData();
//        });
//
//        openLog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openLogin();
//            }
//        });
//    }
//
//    private void openLogin() {
//        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//        finish();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (auth.getCurrentUser() != null) {
//            openMain();
//        }
//    }
//
//    private void openMain() {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//    }
//
//    private void validateData() {
//        name = regName.getText().toString();
//        email = regEmail.getText().toString();
//        phone = regPhone.getText().toString();
//        pass = regPassword.getText().toString();
//        branch = regBranchSpinner.getSelectedItem().toString();
//        year = regYearSpinner.getSelectedItem().toString();
//        sem = regSemesterSpinner.getSelectedItem().toString();
//        gender = regGenderSpinner.getSelectedItem().toString();
//
//        if (name.isEmpty()) {
//            regName.setError("Required");
//            regName.requestFocus();
//        } else if (phone.isEmpty()) {
//            regPhone.setError("Required");
//            regPhone.requestFocus();
//        } else if (email.isEmpty()) {
//            regEmail.setError("Required");
//            regEmail.requestFocus();
//        } else if (!isValidEmail(email)) {
//            Toast.makeText(RegisterActivity.this, "Enter college Email id", Toast.LENGTH_SHORT).show();
//            regEmail.setError("Invalid Email");
//            regEmail.requestFocus();
//        } else if (!isStrongPassword(pass)) {
//            Toast.makeText(RegisterActivity.this, "Enter a strong password Eg Pass@123", Toast.LENGTH_SHORT).show();
//            regPassword.setError("Weak Password");
//            regPassword.requestFocus();
//        } else if (branch.isEmpty()) {
//            Toast.makeText(RegisterActivity.this, "Select Branch", Toast.LENGTH_SHORT).show();
//        } else if (year.isEmpty()) {
//            Toast.makeText(RegisterActivity.this, "Select Year", Toast.LENGTH_SHORT).show();
//        } else if (gender.isEmpty()) {
//            Toast.makeText(RegisterActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
//        } else if (sem.isEmpty()) {
//            Toast.makeText(RegisterActivity.this, "Select Semester", Toast.LENGTH_SHORT).show();
//        } else {
//            // Generate token
//            token = UUID.randomUUID().toString();
//            createUser();
//        }
//    }
//
//    private boolean isValidEmail(String email) {
//        String emailPattern = "^(.+)@(gmail\\.com|ghrce\\.raisoni\\.net)$";
//        return email.matches(emailPattern);
//    }
//
//    private boolean isStrongPassword(String password) {
//        return password.length() >= 8 &&
//                password.matches(".*[A-Z].*") &&
//                password.matches(".*[a-z].*") &&
//                password.matches(".*\\d.*") &&
//                password.matches(".*[!@#$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?].*");
//    }
//
//    private void createUser() {
//        progressBar.setVisibility(View.VISIBLE);
//
//        auth.createUserWithEmailAndPassword(email, pass)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressBar.setVisibility(View.GONE);
//
//                        if (task.isSuccessful()) {
//                            sendEmailVerification();
//                            saveUserDataToDatabase(); // Save user data to Firebase Realtime Database
//                        } else {
//                            Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void sendEmailVerification() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (user != null) {
//            user.sendEmailVerification()
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(RegisterActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(RegisterActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void saveUserDataToDatabase() {
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if (currentUser != null) {
//            String uid = currentUser.getUid();
//            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child(uid);
//            HashMap<String, Object> userData = new HashMap<>();
//            userData.put("name", name);
//            userData.put("email", email);
//            userData.put("branch", branch);
//            userData.put("year", year);
//            userData.put("semester", sem);
//            userData.put("phone", phone);
//            userData.put("gender", gender);
//            userData.put("token", token); // Adding token to user data
//
//            userRef.setValue(userData)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
//                                openMain();
//                            } else {
//                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
////        // Start the ChatActivity activity and pass the user's name as an extra
////        Intent intent = new Intent(RegisterActivity.this, ChatActivity.class);
////        intent.putExtra("userName", name); // Pass the user's name
////        startActivity(intent);
//    }
//}





//////////////////////11111111111111111////////////////////////////////////////////////////////


//package bhushan.org.GHRCEUSER.authentication;
//
//        import androidx.annotation.NonNull;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.view.View;
//        import android.widget.AdapterView;
//        import android.widget.ArrayAdapter;
//        import android.widget.Button;
//        import android.widget.EditText;
//        import android.widget.ProgressBar;
//        import android.widget.Spinner;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import com.google.android.gms.tasks.OnCompleteListener;
//        import com.google.android.gms.tasks.OnFailureListener;
//        import com.google.android.gms.tasks.Task;
//        import com.google.firebase.auth.AuthResult;
//        import com.google.firebase.auth.FirebaseAuth;
//        import com.google.firebase.auth.FirebaseUser;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//
//        import java.util.HashMap;
//
//        import bhushan.org.GHRCEUSER.MainActivity;
//        import bhushan.org.GHRCEUSER.R;
//
//public class RegisterActivity extends AppCompatActivity {
//
//    private EditText regName, regEmail, regPassword;
//    private Spinner regBranchSpinner, regYearSpinner, regSemesterSpinner;
//    private Button register;
//    private TextView openLog;
//
//    private String name, email, pass, branch, year, sem;
//
//    private FirebaseAuth auth;
//
//    private DatabaseReference reference;
//    private DatabaseReference dbRef;
//
//    // Declare the ProgressBar
//    private ProgressBar progressBar;
//
//    private ArrayAdapter<CharSequence> branchAdapter, yearAdapter, semesterAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        progressBar = findViewById(R.id.progressBar);
//        auth = FirebaseAuth.getInstance();
//        reference = FirebaseDatabase.getInstance().getReference();
//
//        regName = findViewById(R.id.regName);
//        regEmail = findViewById(R.id.regEmail);
//        regPassword = findViewById(R.id.regPass);
//
//        regBranchSpinner = findViewById(R.id.regBranchSpinner);
//        regYearSpinner = findViewById(R.id.regYearSpinner);
//        regSemesterSpinner = findViewById(R.id.regSemesterSpinner);
//
//        register = findViewById(R.id.register);
//        openLog = findViewById(R.id.openLog);
//
//        // Setting up adapters for spinners
//        branchAdapter = ArrayAdapter.createFromResource(this, R.array.branch_options, android.R.layout.simple_spinner_item);
//        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        regBranchSpinner.setAdapter(branchAdapter);
//
//        yearAdapter = ArrayAdapter.createFromResource(this, R.array.year_options, android.R.layout.simple_spinner_item);
//        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        regYearSpinner.setAdapter(yearAdapter);
//
//        semesterAdapter = ArrayAdapter.createFromResource(this, R.array.semester_options, android.R.layout.simple_spinner_item);
//        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        regSemesterSpinner.setAdapter(semesterAdapter);
//
//        register.setOnClickListener((view) -> {
//            validateData();
//        });
//
//        openLog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openLogin();
//            }
//        });
//    }
//
//    private void openLogin() {
//        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//        finish();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (auth.getCurrentUser() != null) {
//            openMain();
//        }
//    }
//
//    private void openMain() {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//    }
//
//    private void validateData() {
//        name = regName.getText().toString();
//        email = regEmail.getText().toString();
//        pass = regPassword.getText().toString();
//        branch = regBranchSpinner.getSelectedItem().toString();
//        year = regYearSpinner.getSelectedItem().toString();
//        sem = regSemesterSpinner.getSelectedItem().toString();
//
//        if (name.isEmpty()) {
//            regName.setError("Required");
//            regName.requestFocus();
//        } else if (email.isEmpty()) {
//            regEmail.setError("Required");
//            regEmail.requestFocus();
//        } else if (!isValidEmail(email)) { //////valid
//            Toast.makeText(RegisterActivity.this, "Enter college Email id", Toast.LENGTH_SHORT).show();
//            regEmail.setError("Invalid Email");
//            regEmail.requestFocus(); //////////revoe
//        } else if (!isStrongPassword(pass)) {
//            Toast.makeText(RegisterActivity.this, "Enter a strong password", Toast.LENGTH_SHORT).show();
//            regPassword.setError("Weak Password");
//            regPassword.requestFocus();
//        } else if (branch.isEmpty()) {
//            Toast.makeText(RegisterActivity.this, "Select Branch", Toast.LENGTH_SHORT).show();
//        } else if (year.isEmpty()) {
//            Toast.makeText(RegisterActivity.this, "Select Year", Toast.LENGTH_SHORT).show();
//        } else if (sem.isEmpty()) {
//            Toast.makeText(RegisterActivity.this, "Select Semester", Toast.LENGTH_SHORT).show();
//        } else {
//            createUser();
//        }
//    }
//
//    private boolean isValidEmail(String email) {
//        // Use a regex pattern to check if the email matches the allowed domains
//        String emailPattern = "^(.+)@(gmail\\.com|ghrce\\.raisoni\\.net)$";
//        return email.matches(emailPattern);
//    }
//
//    private boolean isStrongPassword(String password) {
//        // Check if the password contains a mix of characters (letters, numbers, and symbols)
////        return password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$");
//        return password.length() >= 8 &&                 // Minimum eight characters
//                password.matches(".*[A-Z].*") &&         // At least one uppercase letter
//                password.matches(".*[a-z].*") &&         // At least one lowercase letter
//                password.matches(".*\\d.*") &&           // At least one number
//                password.matches(".*[!@#$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?].*");  // At least one special character
//
//    }
//
//    private void createUser() {
//        // Show the progress bar
//        progressBar.setVisibility(View.VISIBLE);
//
//        auth.createUserWithEmailAndPassword(email, pass)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        // Hide the progress bar when task completes
//                        progressBar.setVisibility(View.GONE);
//
//                        if (task.isSuccessful()) {
//                            sendEmailVerification();
//                            uploadUserData();
//                        } else {
//                            Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Hide the progress bar if there's a failure
//                        progressBar.setVisibility(View.GONE);
//
//                        Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void sendEmailVerification() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        // Check if user is not null
//        if (user != null) {
//            user.sendEmailVerification()
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(RegisterActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(RegisterActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void uploadUserData() {
//        dbRef = reference.child("user");
//        String key = dbRef.push().getKey();
//
//        HashMap<String, String> userData = new HashMap<>();
//        userData.put("key", key);
//        userData.put("name", name);
//        userData.put("email", email);
//        userData.put("branch", branch);
//        userData.put("year", year);
//        userData.put("semester", sem);
//
//        dbRef.child(key).setValue(userData)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
//                            openMain();
//                        } else {
//                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//}


////////////Branch User chamges user catariged //

package bhushan.org.GHRCEUSER.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        register.setOnClickListener((view) -> {
            validateData();
        });

        openLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
    }

    private void openLogin() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            openMain();
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
            Toast.makeText(RegisterActivity.this, "Enter college Email id", Toast.LENGTH_SHORT).show();
            regEmail.setError("Invalid Email");
            regEmail.requestFocus();
        } else if (!isStrongPassword(pass)) {
            Toast.makeText(RegisterActivity.this, "Enter a strong password Eg Pass@123", Toast.LENGTH_SHORT).show();
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
            // Generate token
            token = UUID.randomUUID().toString();
            createUser();
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^(.+)@(gmail\\.com|ghrce\\.raisoni\\.net)$";
        return email.matches(emailPattern);
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?].*");
    }

    private void createUser() {
        progressBar.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            sendEmailVerification();
                            saveUserDataToDatabase(); // Save user data to Firebase Realtime Database
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void saveUserDataToDatabase() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            // Use the branch to categorize the user
            DatabaseReference branchRef = FirebaseDatabase.getInstance().getReference().child("users").child(branch).child(uid);
            HashMap<String, Object> userData = new HashMap<>();
            userData.put("name", name);
            userData.put("email", email);
            userData.put("branch", branch);
            userData.put("year", year);
            userData.put("semester", sem);
            userData.put("phone", phone);
            userData.put("gender", gender);
            userData.put("token", token); // Adding token to user data

            branchRef.setValue(userData)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                openMain();
                            } else {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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


