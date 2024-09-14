//package bhushan.org.GHRCEUSER.menuitems;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.Map;
//
//import bhushan.org.GHRCEUSER.R;
//import bhushan.org.GHRCEUSER.authentication.LoginActivity;
//import bhushan.org.GHRCEUSER.chating.ChatActivity;
//
//public class Profile extends AppCompatActivity {
//
//    private TextView pr_name, pr_email, pr_branch, pr_year, pr_sem, pr_gender, pr_phone;
//    private DatabaseReference dbRef;
//    private Button logout_button;
//
//    public static String currentUserName;
//    public static String currentUserEmail;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Profile");
//
//        // Initialize TextViews
//        pr_name = findViewById(R.id.pr_name);
//        pr_email = findViewById(R.id.pr_email);
//        pr_branch = findViewById(R.id.pr_branch);
//        pr_year = findViewById(R.id.pr_year);
//        pr_sem = findViewById(R.id.pr_sem);
//        pr_gender = findViewById(R.id.pr_gender);
//        pr_phone = findViewById(R.id.pr_phone);
//
//        // Initialize Log Out button
//        logout_button = findViewById(R.id.logout_button);
//        logout_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                openLogin();
//            }
//        });
//
//        // Get current user
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (user != null) {
//            // Get UID of the current user
//            String uid = user.getUid();
//
//            // Get reference to the user node in the database
//            dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
//
//            // Add ValueEventListener to retrieve user data
//            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        // Get user data as a Map
//                        Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();
//
//                        // Set user data to TextViews
//                        pr_name.setText(userData.get("name").toString());
//                        pr_email.setText(userData.get("email").toString());
//                        pr_branch.setText(userData.get("branch").toString());
//                        pr_year.setText(userData.get("year").toString());
//                        pr_sem.setText(userData.get("semester").toString());
//                        pr_gender.setText(userData.get("gender").toString());
//                        pr_phone.setText(userData.get("phone").toString());
//
//                        // Store the current user's name and email
//                        currentUserName = userData.get("name").toString();
//                        currentUserEmail = userData.get("email").toString();
//                    } else {
//                        // Log statement to indicate that dataSnapshot doesn't exist
//                        Log.d("ProfileActivity", "Data doesn't exist");
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Log statement for any database error
//                    Log.d("ProfileActivity", "Database Error: " + databaseError.getMessage());
//                }
//            });
//        }
//    }
//
//    private void openLogin() {
//        Intent intent = new Intent(Profile.this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    public void openChatActivity(View view) {
//        Intent intent = new Intent(this, ChatActivity.class);
//        intent.putExtra("userName", currentUserName); // Corrected the parameter name to "userName"
//        startActivity(intent);
//    }
//}


//package bhushan.org.GHRCEUSER.menuitems;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//import bhushan.org.GHRCEUSER.R;
//import bhushan.org.GHRCEUSER.authentication.LoginActivity;
//import bhushan.org.GHRCEUSER.chating.ChatActivity;
//
//public class Profile extends AppCompatActivity {
//
//    private TextView pr_name, pr_email, pr_branch, pr_year, pr_sem, pr_gender, pr_phone;
//    private DatabaseReference dbRef;
//    private Button logout_button;
//
//    public static String currentUserName;
//    public static String currentUserEmail;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Profile");
//
//        // Initialize TextViews
//        pr_name = findViewById(R.id.pr_name);
//        pr_email = findViewById(R.id.pr_email);
//        pr_branch = findViewById(R.id.pr_branch);
//        pr_year = findViewById(R.id.pr_year);
//        pr_sem = findViewById(R.id.pr_sem);
//        pr_gender = findViewById(R.id.pr_gender);
//        pr_phone = findViewById(R.id.pr_phone);
//
//        // Initialize Log Out button
//        logout_button = findViewById(R.id.logout_button);
//        Button update_button = findViewById(R.id.update_button);
//        logout_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Create an AlertDialog
//                new AlertDialog.Builder(Profile.this)
//                        .setTitle("Log Out")
//                        .setMessage("Are you sure you want to log out?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // User clicked Yes, perform logout
//                                FirebaseAuth.getInstance().signOut();
//                                openLogin();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // User clicked No, dismiss the dialog
//                                dialog.dismiss();
//                            }
//                        })
//                        .show();
//            }
//        });
//
//
//        update_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(Profile.this, UpdateProfileActivity.class);
////                startActivity(intent);
//                Toast.makeText(Profile.this, "coming soon....", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Get current user
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (user != null) {
//            // Get UID of the current user
//            String uid = user.getUid();
//
//            // Retrieve user data from Firebase Realtime Database
//            // To find the branch where the user data is stored, we need to query all branches
//            DatabaseReference branchesRef = FirebaseDatabase.getInstance().getReference().child("users");
//
//            // Use AtomicBoolean for thread-safe boolean flag
//            AtomicBoolean userFound = new AtomicBoolean(false);
//
//            branchesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot branchSnapshot : dataSnapshot.getChildren()) {
//                        DatabaseReference userRef = branchSnapshot.getRef().child(uid);
//
//                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()) {
//                                    // Get user data as a Map
//                                    Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();
//
//                                    // Set user data to TextViews
//                                    pr_name.setText(userData.get("name").toString());
//                                    pr_email.setText(userData.get("email").toString());
//                                    pr_branch.setText(userData.get("branch").toString());
//                                    pr_year.setText(userData.get("year").toString());
//                                    pr_sem.setText(userData.get("semester").toString());
//                                    pr_gender.setText(userData.get("gender").toString());
//                                    pr_phone.setText(userData.get("phone").toString());
//
//                                    // Store the current user's name and email
//                                    currentUserName = userData.get("name").toString();
//                                    currentUserEmail = userData.get("email").toString();
//
//                                    userFound.set(true);
//                                }
//
//                                // If user is not found in any branch, log message
//                                if (!userFound.get()) {
//                                    Log.d("ProfileActivity", "User data not found");
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                // Log statement for any database error
//                                Log.d("ProfileActivity", "Database Error: " + databaseError.getMessage());
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Log statement for any database error
//                    Log.d("ProfileActivity", "Database Error: " + databaseError.getMessage());
//                }
//            });
//        }
//    }
//
//    private void openLogin() {
//        Intent intent = new Intent(Profile.this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    public void openChatActivity(View view) {
//        Intent intent = new Intent(this, ChatActivity.class);
//        intent.putExtra("userName", currentUserName); // Corrected the parameter name to "userName"
//        startActivity(intent);
//    }
//}

package bhushan.org.GHRCEUSER.menuitems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import bhushan.org.GHRCEUSER.R;
import bhushan.org.GHRCEUSER.authentication.LoginActivity;
//import bhushan.org.GHRCEUSER.chating.chat;

public class Profile extends AppCompatActivity {

    private TextView pr_name, pr_email, pr_branch, pr_year, pr_sem, pr_gender, pr_phone;
    private DatabaseReference dbRef;
    private Button logout_button;

    public static String currentUserName;
    public static String currentUserEmail;

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

        // Initialize Log Out button
        logout_button = findViewById(R.id.logout_button);
        Button update_button = findViewById(R.id.update_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog
                new AlertDialog.Builder(Profile.this)
                        .setTitle("Log Out")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // User clicked Yes, perform logout
                                FirebaseAuth.getInstance().signOut();
                                openLogin();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // User clicked No, dismiss the dialog
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile.this, "coming soon....", Toast.LENGTH_SHORT).show();
            }
        });

        // Get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Get UID of the current user
            String uid = user.getUid();

            // Retrieve user data from Firebase Realtime Database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean userFound = false;

                    // Loop through each branch
                    for (DataSnapshot branchSnapshot : dataSnapshot.getChildren()) {
                        // Look for the user in each branch
                        if (branchSnapshot.hasChild(uid)) {
                            userFound = true;
                            DataSnapshot userSnapshot = branchSnapshot.child(uid);
                            Map<String, Object> userData = (Map<String, Object>) userSnapshot.getValue();

                            if (userData != null) {
                                // Set user data to TextViews
                                pr_name.setText(userData.get("name").toString());
                                pr_email.setText(userData.get("email").toString());
                                pr_branch.setText(userData.get("branch").toString());
                                pr_year.setText(userData.get("year").toString());
                                pr_sem.setText(userData.get("semester").toString());
                                pr_gender.setText(userData.get("gender").toString());
                                pr_phone.setText(userData.get("phone").toString());

                                // Store the current user's name and email
                                currentUserName = userData.get("name").toString();
                                currentUserEmail = userData.get("email").toString();
                            }
                            break;  // Exit loop once the user is found
                        }
                    }

                    if (!userFound) {
                        // User data not found in any branch
                        Log.d("ProfileActivity", "User data not found");
                        Toast.makeText(Profile.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Log statement for any database error
                    Log.d("ProfileActivity", "Database Error: " + databaseError.getMessage());
                    Toast.makeText(Profile.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
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

//    public void openChatActivity(View view) {
//        Intent intent = new Intent(this, chat.class);
//        intent.putExtra("userName", currentUserName); // Send the user's name to the chat activity
//        startActivity(intent);
//    }

}


