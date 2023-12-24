package bhushan.org.GHRCEUSER.paper;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import bhushan.org.GHRCEUSER.R;

public class PaperViewerActivity extends AppCompatActivity {

    private String url;
    private PDFView paperView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_viewer);

        url = getIntent().getStringExtra("paperUri");

        paperView = findViewById(R.id.paperView);

        new PaperDownload().execute(url);
    }
    private class PaperDownload extends AsyncTask<String,Void, InputStream>{


        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                if (urlConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            paperView.fromStream(inputStream)
//                    .onPageChange(new OnPageChangeListener() {
//                        @Override
//                        public void onPageChanged(int page, int pageCount) {
//                            // Update UI with the current page number
//                            setTitle("Page " + (page + 1) + " of " + pageCount);
//                        }
//                    })
                    .load();
        }
    }
}