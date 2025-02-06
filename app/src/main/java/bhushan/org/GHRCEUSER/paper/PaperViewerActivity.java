//package bhushan.org.GHRCEUSER.paper;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.WindowManager;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.github.pdfviewer.PDFView;
//import com.github.pdfviewer.scroll.DefaultScrollHandle;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.FullScreenContentCallback;
//import com.google.android.gms.ads.interstitial.InterstitialAd;
//import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
//import com.google.android.gms.ads.LoadAdError;
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
//public class PaperViewerActivity extends AppCompatActivity {
//
//    private String url;
//    private PDFView paperView;
//    private ProgressBar progressBar;
//    private AdView bannerAdView;
//    private InterstitialAd interstitialAd;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Prevent screenshots and screen recordings
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
//        bannerAdView = findViewById(R.id.bannerAdView);
//
//        // Load Banner Ad
//        loadBannerAd();
//
//        // Load Interstitial Ad
//        loadInterstitialAd();
//    }
//
//    private void loadBannerAd() {
//        AdRequest adRequest = new AdRequest.Builder().build();
//        bannerAdView.loadAd(adRequest);
//    }
//
//    private void loadInterstitialAd() {
//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this, "ca-app-pub-2270115251422656/9983951208", adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd ad) {
//                interstitialAd = ad;
//
//                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                    @Override
//                    public void onAdDismissedFullScreenContent() {
//                        interstitialAd = null;
//                        loadPdf(); // Load PDF after ad is closed
//                    }
//
//                    @Override
//                    public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
//                        interstitialAd = null;
//                        loadPdf(); // Load PDF if ad fails to show
//                    }
//                });
//
//                // Show the ad before loading the PDF
//                interstitialAd.show(PaperViewerActivity.this);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError error) {
//                interstitialAd = null;
//                loadPdf(); // Load PDF if ad fails to load
//            }
//        });
//    }
//
//    private void loadPdf() {
//        new PaperDownloadTask().execute(url);
//    }
//
//    private class PaperDownloadTask extends AsyncTask<String, Void, InputStream> {
//        @Override
//        protected void onPreExecute() {
//            progressBar.setVisibility(ProgressBar.VISIBLE);
//            paperView.setVisibility(PDFView.INVISIBLE);
//        }
//
//        @Override
//        protected InputStream doInBackground(String... strings) {
//            InputStream inputStream = null;
//            try {
//                URL url = new URL(strings[0]);
//                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.setConnectTimeout(10000);
//                urlConnection.setReadTimeout(15000);
//
//                if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
//                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return inputStream;
//        }
//
//        @Override
//        protected void onPostExecute(InputStream inputStream) {
//            if (inputStream != null) {
//                paperView.fromStream(inputStream)
//                        .defaultPage(0)
//                        .scrollHandle(new DefaultScrollHandle(PaperViewerActivity.this))
//                        .load();
//                progressBar.setVisibility(ProgressBar.GONE);
//                paperView.setVisibility(PDFView.VISIBLE);
//            } else {
//                progressBar.setVisibility(ProgressBar.GONE);
//                Toast.makeText(PaperViewerActivity.this, "Failed to download PDF", Toast.LENGTH_SHORT).show();
//            }
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
