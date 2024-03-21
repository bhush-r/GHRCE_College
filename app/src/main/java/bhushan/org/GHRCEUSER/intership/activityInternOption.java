package bhushan.org.GHRCEUSER.intership;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import bhushan.org.GHRCEUSER.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Objects;

public class activityInternOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intern_option);
        CardView cvMath = findViewById(R.id.cvMath);
        CardView cvGeography = findViewById(R.id.cvGeography);
        CardView cvLiterature = findViewById(R.id.cvLiterature);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Company Option");

//        findViewById(R.id.imageViewQuizOption).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        cvMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activityInternOption.this, Cognifyz.class);
//                intent.putExtra(Constants.SUBJECT,getString(R.string.math));
                startActivity(intent);
            }
        });

        cvGeography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activityInternOption.this, Codsoft.class);
//                intent.putExtra(Constants.SUBJECT,getString(R.string.geography));
                startActivity(intent);
            }
        });

        cvLiterature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activityInternOption.this, LearnFlow.class);
//                intent.putExtra(Constants.SUBJECT,getString(R.string.literature));
                startActivity(intent);
            }
        });
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