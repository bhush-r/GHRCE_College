package bhushan.org.GHRCEUSER.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import bhushan.org.GHRCEUSER.MainActivity;
import bhushan.org.GHRCEUSER.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText regName, regEmail, regPassword, regBranch, regYear, regSemester;
    private Button register;

    private String name, email, pass, branch, year, sem;

    private FirebaseAuth auth;

    private DatabaseReference reference;
    private DatabaseReference dbRef;
    private TextView openLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();


        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPass);
        regBranch = findViewById(R.id.regBranch); // Add this line
        regYear = findViewById(R.id.regYear);     // Add this line
        regSemester = findViewById(R.id.regSemester); // Add this line
        register = findViewById(R.id.register);
        openLog = findViewById(R.id.openLog);


        register.setOnClickListener((view) -> { validateData();});

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
        if (auth.getCurrentUser() != null){
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
        pass = regPassword.getText().toString();
        ///////
        branch = regBranch.getText().toString();
        year = regYear.getText().toString();
        sem = regSemester.getText().toString();

        if (name.isEmpty()) {
            regName.setError("Required");
            regName.requestFocus();
        } else if (email.isEmpty()) {
            regEmail.setError("Required");
            regEmail.requestFocus();
        } else if (!isValidEmail(email)) { //////valid
            Toast.makeText(RegisterActivity.this, "Enter college Email id", Toast.LENGTH_SHORT).show();
            regEmail.setError("Invalid Email");
            regEmail.requestFocus(); //////////revoe
        }
        else if (!isStrongPassword(pass)) {
            Toast.makeText(RegisterActivity.this, "Enter a strong password", Toast.LENGTH_SHORT).show();
            regPassword.setError("Weak Password");
            regPassword.requestFocus();
//        } else if (pass.isEmpty() || !isStrongPassword(pass)) {
//            Toast.makeText(RegisterActivity.this, "Use min 6 character)", Toast.LENGTH_SHORT).show();
//            regPassword.setError("Invalid Password");
//            regPassword.requestFocus();
        } else if (branch.isEmpty()) {        ////////////////
            regBranch.setError("Required");
            regBranch.requestFocus();
        } else if (year.isEmpty()) {
            regYear.setError("Required");
            regYear.requestFocus();
        } else if (sem.isEmpty()) {
            regSemester.setError("Required");
            regSemester.requestFocus();
        }else {
            createUser();
        }

    }

    private boolean isValidEmail(String email) {
        // Use a regex pattern to check if the email matches the allowed domains
        String emailPattern = "^(.+)@(gmail\\.com|ghrce\\.raisoni\\.net)$";
        return email.matches(emailPattern);
    }

//    private boolean isStrongPassword(String password) {
//        // Check if the password contains at least one special character and has a minimum length of 6
//        String passwordPattern = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?|\\\\])\\S{6,}$";
//        return password.matches(passwordPattern);
//    }
private boolean isStrongPassword(String password) {
    // Check if the password contains a mix of characters (letters, numbers, and symbols)
    return password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$");
}

    private void createUser() {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            uploadData();
                        }else {
                            Toast.makeText(RegisterActivity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadData() {
        dbRef = reference.child("user");
        String key = dbRef.push().getKey();

        HashMap<String, String> user = new HashMap<>();
        user.put("key", key);
        user.put("name", name);
        user.put("email", email);
        user.put("branch", regBranch.getText().toString());
        user.put("year", regYear.getText().toString());
        user.put("semester", regSemester.getText().toString());

        dbRef.child(key).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            openMain();
                        }else {
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