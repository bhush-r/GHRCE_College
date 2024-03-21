package bhushan.org.GHRCEUSER.menuitems;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import bhushan.org.GHRCEUSER.R;

public class privacyPolicy extends AppCompatActivity {

    private TextView privacyPolicyContentTextView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        // Initialize FirebaseFirestore instance
        db = FirebaseFirestore.getInstance();

        // Initialize TextView to display Privacy Policy content
        privacyPolicyContentTextView = findViewById(R.id.privacyPolicyContentTextView);

        // Fetch and display Privacy Policy content
        fetchPrivacyPolicy();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Privacy Policy");
    }

    private void fetchPrivacyPolicy() {
        // Reference to the 'privacy_policy' document in Firestore
        db.document("/privacy_policy/nwoWLyQZUMWDl0PsLPg4")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                // Retrieve the Privacy Policy text and display it in TextView
                                String privacyPolicyText = document.getString("content");
                                if (privacyPolicyText != null) {
                                    privacyPolicyContentTextView.setText(Html.fromHtml(privacyPolicyText));
                                } else {
                                    // Privacy Policy content is null, display default message
                                    privacyPolicyContentTextView.setText("Privacy Policy content is not available.");
                                }
                            } else {
                                privacyPolicyContentTextView.setText("No privacy policy found.");
                            }
                        } else {
                            privacyPolicyContentTextView.setText("Error fetching privacy policy.");
                        }
                    }
                });
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
