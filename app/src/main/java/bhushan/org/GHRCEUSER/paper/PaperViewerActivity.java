//package bhushan.org.GHRCEUSER.paper;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.WindowManager;
//import android.widget.ProgressBar;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.github.barteksc.pdfviewer.PDFView;
//import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
//
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//
//import javax.net.ssl.HttpsURLConnection;
//
//import bhushan.org.GHRCEUSER.R;
//import bhushan.org.GHRCEUSER.ebook.PdfViewerActivity;
//
//public class PaperViewerActivity extends AppCompatActivity {
//
//    private String url;
//    private PDFView paperView;
//
//    private ProgressBar progressBar;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Add the line to prevent screenshots and screen recordings
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE
//        );
//
//        setContentView(R.layout.activity_paper_viewer);
//
//        url = getIntent().getStringExtra("paperUri");
//
//        paperView = findViewById(R.id.paperView);
//        progressBar = findViewById(R.id.progressBar);
//
//        new PaperDownload().execute(url);
//    }
//    private class PaperDownload extends AsyncTask<String,Void, InputStream>{
//
//        @Override
//        protected void onPreExecute() {
//            // Show the progress bar before starting the download
//            progressBar.setVisibility(ProgressBar.VISIBLE);
//            paperView.setVisibility(PDFView.INVISIBLE);
//        }
//        @Override
//        protected InputStream doInBackground(String... strings) {
//            InputStream inputStream = null;
//
//            try {
//                URL url = new URL(strings[0]);
//                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
//
//                if (urlConnection.getResponseCode() == 200){
//                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return inputStream;
//        }
//
//        @Override
//        protected void onPostExecute(InputStream inputStream) {
//            // Introduce a delay of 2 seconds before hiding the progress bar
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // Hide the progress bar after the delay
//                    progressBar.setVisibility(ProgressBar.GONE);
//
//                    // Show the PDFView
//                    paperView.setVisibility(PDFView.VISIBLE);
//
//                    // Load the PDF into the PDFView
//                    paperView.fromStream(inputStream)
//                            .defaultPage(0) // Set default page to 0
//                            .scrollHandle(new DefaultScrollHandle(PaperViewerActivity.this)) // Add scroll handle
//                            .load();
//                }
//            }, 5000); // Delay in milliseconds (adjust as needed)
//        }
//    }
//}




package bhushan.org.GHRCEUSER.paper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
//
//import com.github.barteksc.pdfviewer.PDFView;
//import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
//import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.pdfviewer.PDFView;
import com.github.pdfviewer.listener.OnLoadCompleteListener;
import com.github.pdfviewer.scroll.DefaultScrollHandle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import bhushan.org.GHRCEUSER.R;

public class PaperViewerActivity extends AppCompatActivity {

    private String url;
    private PDFView paperView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add the line to prevent screenshots and screen recordings
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );

        setContentView(R.layout.activity_paper_viewer);

        url = getIntent().getStringExtra("paperUri");

        paperView = findViewById(R.id.paperView);
        progressBar = findViewById(R.id.progressBar);

        new PaperDownloadTask().execute(url);
    }

    private class PaperDownloadTask extends AsyncTask<String, Void, InputStream> {

        @Override
        protected void onPreExecute() {
            // Show the progress bar before starting the download
            progressBar.setVisibility(ProgressBar.VISIBLE);
            paperView.setVisibility(PDFView.INVISIBLE);
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            if (inputStream != null) {
                // Load the PDF into the PDFView
                paperView.fromStream(inputStream)
                        .defaultPage(0) // Set default page to 0
                        .onLoad(new OnLoadCompleteListener() {
                            @Override
                            public void loadComplete(int nbPages) {
                                // Hide the progress bar after the PDF is loaded
                                progressBar.setVisibility(ProgressBar.GONE);
                                paperView.setVisibility(PDFView.VISIBLE);
                            }
                        })
                        .scrollHandle(new DefaultScrollHandle(PaperViewerActivity.this)) // Add scroll handle
                        .load();
            } else {
                // Handle case where inputStream is null (failed to download PDF)
                progressBar.setVisibility(ProgressBar.GONE);
                Toast.makeText(PaperViewerActivity.this, "Failed to download PDF", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
