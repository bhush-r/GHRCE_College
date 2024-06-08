package bhushan.org.GHRCEUSER.compile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import bhushan.org.GHRCEUSER.R;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class compiler extends AppCompatActivity {

    private EditText codeInput;
    private TextView outputText;
    private JDoodleApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compiler);
        codeInput = findViewById(R.id.codeInput);
        Button compileButton = findViewById(R.id.compileButton);
        outputText = findViewById(R.id.outputText);

        // Retrofit setup
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.jdoodle.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(JDoodleApi.class);

        compileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compileCode();
            }
        });
    }

    private void compileCode() {
        String code = codeInput.getText().toString();
        JDoodleRequest request = new JDoodleRequest(code, "python3", "3", "115800078edc85aac147c5d8cfbb3dd4", "a95cf05ee7554204d6946ea1c9b5dea7657f2253607f0873e96265a98f357964");

        api.executeCode(request).enqueue(new Callback<JDoodleResponse>() {
            @Override
            public void onResponse(Call<JDoodleResponse> call, Response<JDoodleResponse> response) {
                if (response.isSuccessful()) {
                    outputText.setText(response.body().getOutput());
                } else {
                    outputText.setText("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JDoodleResponse> call, Throwable t) {
                outputText.setText("Failure: " + t.getMessage());
            }
        });
    }
}