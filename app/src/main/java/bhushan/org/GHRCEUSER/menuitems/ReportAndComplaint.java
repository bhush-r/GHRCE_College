package bhushan.org.GHRCEUSER.menuitems;

import bhushan.org.GHRCEUSER.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ReportAndComplaint extends AppCompatActivity {

    private Spinner subjectDropdown;
    private TextInputEditText complaintDescription;
    private Button attachmentButton, submitButton;
    private ProgressDialog progressDialog;

    private static final int PICK_FILE_REQUEST_CODE = 101;
    private Uri selectedFileUri = null;
    private String downloadUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_and_complaint);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        subjectDropdown = findViewById(R.id.subject_dropdown);
        complaintDescription = findViewById(R.id.complaint_description);
        attachmentButton = findViewById(R.id.attachment_button);
        submitButton = findViewById(R.id.submit_button);

        FirebaseMessaging.getInstance().subscribeToTopic("Complaint");
        // Populate spinner with subjects
        populateSubjectsSpinner();

        // Handle spinner item selection
        subjectDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubject = parent.getItemAtPosition(position).toString();
                // Do something with the selected subject
                Toast.makeText(ReportAndComplaint.this, "Selected subject: " + selectedSubject, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        attachmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSubmitReport();
            }
        });
    }

    private void populateSubjectsSpinner() {
        // Populate spinner with subjects
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.subjects_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectDropdown.setAdapter(adapter);
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedFileUri = data.getData();
            String fileName = getFileName(selectedFileUri);
            // Update UI to show selected file
            attachmentButton.setText("Selected File: \n" + fileName);
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (columnIndex != -1) {
                            result = cursor.getString(columnIndex);
                        }
                    }
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void validateAndSubmitReport() {
        String subject = subjectDropdown.getSelectedItem().toString();
        String description = complaintDescription.getText().toString();

        if (TextUtils.isEmpty(subject)) {
            showAlert("Subject is required.");
            return;
        }

        if (TextUtils.isEmpty(description)) {
            showAlert("Complaint description is required.");
            return;
        }

        if (selectedFileUri == null) {
            showAlert("Please select a file.");
            return;
        }

        uploadFileToStorage(subject, description);
    }

    private void uploadFileToStorage(final String subject, final String description) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Get a reference to the Firebase Storage location
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("uploads").child(selectedFileUri.getLastPathSegment());

        // Upload file to Firebase Storage
        storageRef.putFile(selectedFileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL from the uploaded file
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUrl = uri.toString();
                                uploadReportToDatabase(subject, description);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                showAlert("Failed to get download URL. Please try again.");
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        showAlert("Failed to upload file. Please try again.");
                    }
                });
    }

    private void uploadReportToDatabase(String subject, String description) {
        // Dismiss the progress dialog
        progressDialog.dismiss();

        // Get current date and time
        String currentDateAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Get a reference to the Firebase Realtime Database location
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Report a Complaint");

        // Create a Report object with the details
        Report report = new Report(subject, description, currentDateAndTime, downloadUrl);

        // Push the report object to the database
        databaseReference.push().setValue(report)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Show success message
                        Toast.makeText(ReportAndComplaint.this, "Report submitted successfully.", Toast.LENGTH_SHORT).show();

                        // Clear data after submission
                        clearData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Show failure message
                        showAlert("Failed to submit report to database. Please try again.");
                    }
                });
    }

    private void clearData() {
        complaintDescription.setText("");
        attachmentButton.setText("Select File");
        selectedFileUri = null;
        downloadUrl = null;
    }

    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
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
