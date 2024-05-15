package bhushan.org.GHRCEUSER.intership;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import bhushan.org.GHRCEUSER.R;
public class Cognifyz extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GET = 1;
    private Uri selectedImageUri;
    private TextView textViewFile;
    private DatabaseReference databaseReference;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognifyz);

        //        Notification
        FirebaseMessaging.getInstance().subscribeToTopic("Cognifyz");


        databaseReference = FirebaseDatabase.getInstance().getReference("CognifyzTechnology");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // Initialize Views
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextFullName = findViewById(R.id.editTextFullName);
        EditText editTextContactNo = findViewById(R.id.editTextContactNo);
        EditText editTextCollege = findViewById(R.id.editTextCollege);
        Spinner spinnerGender = findViewById(R.id.spinnerGender);
        Spinner spinnerAcademicDegree = findViewById(R.id.spinnerAcademicDegree);
        Spinner spinnerDegreeStatus = findViewById(R.id.spinnerDegreeStatus);
        Spinner spinnerInternshipDomain = findViewById(R.id.spinnerInternshipDomain);
        Spinner spinnerLearnAbout = findViewById(R.id.spinnerLearnAbout);
        RadioGroup radioGroupRegistrationOptions = findViewById(R.id.radioGroupRegistrationOptions);
        textViewFile = findViewById(R.id.textViewFile);
        Button buttonApply = findViewById(R.id.buttonApply);
        CheckBox checkBoxTerms = findViewById(R.id.checkBoxTerms);
        TextView textViewLinkedIn = findViewById(R.id.textViewLinkedIn);
        TextView textViewTelegram = findViewById(R.id.textViewTelegram);
        CheckBox checkBoxJoinGroup = findViewById(R.id.checkBoxJoinGroup);
        RadioGroup radioGroupCountry = findViewById(R.id.radioGroupCountry);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cognifyz");


//        findViewById(R.id.imageViewStartQuiz).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        // Set up Gender Spinner
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        // Set up Academic Degree Spinner
        ArrayAdapter<CharSequence> academicDegreeAdapter = ArrayAdapter.createFromResource(this,
                R.array.academic_degree_options, android.R.layout.simple_spinner_item);
        academicDegreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAcademicDegree.setAdapter(academicDegreeAdapter);

        // Set up Degree Status Spinner
        ArrayAdapter<CharSequence> degreeStatusAdapter = ArrayAdapter.createFromResource(this,
                R.array.degree_status_options, android.R.layout.simple_spinner_item);
        degreeStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDegreeStatus.setAdapter(degreeStatusAdapter);

        // Set up Internship Domain Spinner
        ArrayAdapter<CharSequence> internshipDomainAdapter = ArrayAdapter.createFromResource(this,
                R.array.internship_domain_options, android.R.layout.simple_spinner_item);
        internshipDomainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInternshipDomain.setAdapter(internshipDomainAdapter);

        // Set up Learn About Cognifyz Technologies Spinner
        ArrayAdapter<CharSequence> learnAboutAdapter = ArrayAdapter.createFromResource(this,
                R.array.learn_about_options, android.R.layout.simple_spinner_item);
        learnAboutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLearnAbout.setAdapter(learnAboutAdapter);

        // Set up File Selection TextView
        textViewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        // Apply Button Click Listener
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    uploadDataToFirebase();
                }
            }
        });

        // Set up Read More / Read Less for textViewDescription
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        final String fullDescription = textViewDescription.getText().toString();
        final int maxLength = 100; // Set the maximum length for truncated text
        final String truncatedDescription = fullDescription.length() > maxLength ?
                fullDescription.substring(0, maxLength) + "..." : fullDescription;
        textViewDescription.setText(truncatedDescription);
        textViewDescription.setOnClickListener(new View.OnClickListener() {
            boolean isExpanded = false;

            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    textViewDescription.setText(truncatedDescription);
                } else {
                    textViewDescription.setText(fullDescription);
                }
                isExpanded = !isExpanded;
            }
        });

        // LinkedIn TextView Click Listener
        textViewLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open LinkedIn URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/cognifyz-techonologies"));
                startActivity(intent);
            }
        });

        // Telegram TextView Click Listener
        textViewTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Telegram URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/cognifyz_technologies"));
                startActivity(intent);
            }
        });
    }

    // Function to open image chooser
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_GET);
    }

    // Function to handle result of image chooser
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK) {
            if (data != null && textViewFile != null) { // Check if textViewFile is not null
                selectedImageUri = data.getData();
                // Display the selected file name in textViewFile
                textViewFile.setText(selectedImageUri.getLastPathSegment());
            }
        }
    }

    // Function to validate form fields
    private boolean validateForm() {
        // Add your validation logic here
        // For simplicity, let's assume all fields are required
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextFullName = findViewById(R.id.editTextFullName);
        EditText editTextContactNo = findViewById(R.id.editTextContactNo);
        EditText editTextCollege = findViewById(R.id.editTextCollege);
        TextView textViewFile = findViewById(R.id.textViewFile);
        CheckBox checkBoxTerms = findViewById(R.id.checkBoxTerms);
        RadioGroup radioGroupRegistrationOptions = findViewById(R.id.radioGroupRegistrationOptions);
        RadioGroup radioGroupCountry = findViewById(R.id.radioGroupCountry);

        CheckBox checkBoxJoinGroup = findViewById(R.id.checkBoxJoinGroup);

        String email = editTextEmail.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String contactNo = editTextContactNo.getText().toString().trim();
        String college = editTextCollege.getText().toString().trim();
        String selectedFileName = textViewFile.getText().toString().trim();

        if (email.isEmpty() || fullName.isEmpty() || contactNo.isEmpty() ||
                college.isEmpty() || selectedFileName.isEmpty() || !checkBoxTerms.isChecked()) {
            Toast.makeText(this, "Please fill in all required fields and accept terms and conditions.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if a payment receipt is selected
        if (radioGroupRegistrationOptions.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select the Fees Type", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if a country is selected
        if (radioGroupCountry.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select Country", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if the Join Group checkbox is checked
        if (!checkBoxJoinGroup.isChecked()) {
            Toast.makeText(this, "Join the Group", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Function to upload data to Firebase Realtime Database
    private void uploadDataToFirebase() {
        // Show progress bar
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait Apply...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextFullName = findViewById(R.id.editTextFullName);
        EditText editTextContactNo = findViewById(R.id.editTextContactNo);
        EditText editTextCollege = findViewById(R.id.editTextCollege);
        Spinner spinnerGender = findViewById(R.id.spinnerGender);
        Spinner spinnerAcademicDegree = findViewById(R.id.spinnerAcademicDegree);
        Spinner spinnerDegreeStatus = findViewById(R.id.spinnerDegreeStatus);
        Spinner spinnerInternshipDomain = findViewById(R.id.spinnerInternshipDomain);
        Spinner spinnerLearnAbout = findViewById(R.id.spinnerLearnAbout);
        RadioGroup radioGroupRegistrationOptions = findViewById(R.id.radioGroupRegistrationOptions);
        TextView textViewFile = findViewById(R.id.textViewFile);

        String email = editTextEmail.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String contactNo = editTextContactNo.getText().toString().trim();
        String college = editTextCollege.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();
        String academicDegree = spinnerAcademicDegree.getSelectedItem().toString();
        String degreeStatus = spinnerDegreeStatus.getSelectedItem().toString();
        String internshipDomain = spinnerInternshipDomain.getSelectedItem().toString();
        String learnAbout = spinnerLearnAbout.getSelectedItem().toString();

        int selectedRadioButtonId = radioGroupRegistrationOptions.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String registrationOption = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";

        String selectedFileName = textViewFile.getText().toString().trim();

        // Check if an image is selected
        if (selectedImageUri != null) {
            // Get a reference to the Firebase Storage location
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + selectedFileName);

            // Upload the image file to Firebase Storage
            storageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully
                        // Now get the download URL of the uploaded image
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Image download URL retrieved successfully
                            String imageUrl = uri.toString();

                            // Create a unique key for the data
                            String key = databaseReference.push().getKey();

                            // Create a UserData object to store the information
                            UserData userdata = new UserData(email, fullName, contactNo, college, gender,
                                    academicDegree, degreeStatus, internshipDomain, learnAbout, registrationOption,
                                    imageUrl); // Add imageUrl to the constructor

                            // Upload the data to Firebase Realtime Database
                            databaseReference.child(key).setValue(userdata)
                                    .addOnSuccessListener(aVoid -> {
                                        // Inform the user that the data has been uploaded
                                        Toast.makeText(Cognifyz.this, "Apply Successful", Toast.LENGTH_SHORT).show();
                                        // Hide progress bar
                                        progressDialog.dismiss();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle any errors that may occur during the upload
                                        Toast.makeText(Cognifyz.this, "Failed to upload data to Firebase: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        // Hide progress bar
                                        progressDialog.dismiss();
                                    });
                        }).addOnFailureListener(e -> {
                            // Handle any errors that may occur while retrieving image download URL
                            Toast.makeText(Cognifyz.this, "Failed to retrieve image download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors that may occur during the upload
                        Toast.makeText(Cognifyz.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
        } else {
            // If no image is selected, show the toast "Select Payment receipt"
            Toast.makeText(Cognifyz.this, "Select Payment receipt", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.option_menu, menu);
//        return true;
//    }

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
