//package bhushan.org.GHRCEUSER.ebook;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
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
//
//public class PdfViewerActivity extends AppCompatActivity {
//
//    private String url;
//    private PDFView pdfView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pdf_viewer);
//
//        url = getIntent().getStringExtra("pdfUri");
//
//        pdfView = findViewById(R.id.pdfView);
//
//        new PdfDownload().execute(url);
//    }
//    private class PdfDownload extends AsyncTask<String,Void, InputStream>{
//
//
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
//            pdfView.fromStream(inputStream)
////                    .onPageChange(new OnPageChangeListener() {
////                        @Override
////                        public void onPageChanged(int page, int pageCount) {
////                            // Update UI with the current page number
////                            setTitle("Page " + (page + 1) + " of " + pageCount);
////                        }
////                    })
//                    .load();
//        }
//    }
//}


package bhushan.org.GHRCEUSER.ebook;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import  com.github.pdfviewer.PDFView;
//import com.github.barteksc.pdfviewer.PDFView;
//import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.pdfviewer.scroll.DefaultScrollHandle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import bhushan.org.GHRCEUSER.R;

public class PdfViewerActivity extends AppCompatActivity {

    private String url;
    private PDFView pdfView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add the line to prevent screenshots and screen recordings
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );

        setContentView(R.layout.activity_pdf_viewer);

        url = getIntent().getStringExtra("pdfUri");

        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.progressBar);

        new PdfDownload().execute(url);
    }

    private class PdfDownload extends AsyncTask<String, Void, InputStream> {

        @Override
        protected void onPreExecute() {
            // Show the progress bar before starting the download
            progressBar.setVisibility(ProgressBar.VISIBLE);
            pdfView.setVisibility(PDFView.INVISIBLE);
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
            // Introduce a delay of 2 seconds before hiding the progress bar
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Hide the progress bar after the delay
                    progressBar.setVisibility(ProgressBar.GONE);

                    // Show the PDFView
                    pdfView.setVisibility(PDFView.VISIBLE);

                    // Load the PDF into the PDFView
                    pdfView.fromStream(inputStream)
                            .defaultPage(0) // Set default page to 0
                            .scrollHandle(new DefaultScrollHandle(PdfViewerActivity.this)) // Add scroll handle
                            .load();
                }
            }, 8000); // Delay in milliseconds (adjust as needed)
        }
    }
}



