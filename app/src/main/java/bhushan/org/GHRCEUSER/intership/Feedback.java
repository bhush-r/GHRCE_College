package bhushan.org.GHRCEUSER.intership;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import bhushan.org.GHRCEUSER.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class Feedback extends AppCompatActivity {

    private TextView termsAndConditionsContentTextView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //        Notification
        FirebaseMessaging.getInstance().subscribeToTopic("Terms");


        termsAndConditionsContentTextView = findViewById(R.id.termsAndConditionsContentTextView);
        db = FirebaseFirestore.getInstance();

        fetchTermsAndConditions();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Terms & Conditions");
    }

    private void fetchTermsAndConditions() {
        db.document("/terms_and_conditions/BIKxOA6yA33DrQp0aioV")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String termsAndConditions = document.getString("content");
                                termsAndConditionsContentTextView.setText(Html.fromHtml(termsAndConditions));
                            } else {
                                termsAndConditionsContentTextView.setText("No terms and conditions found.");
                            }
                        } else {
                            termsAndConditionsContentTextView.setText("Error fetching terms and conditions.");
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
