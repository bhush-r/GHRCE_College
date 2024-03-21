package bhushan.org.GHRCEUSER;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class Results extends AppCompatActivity {
    private WebView resultWebView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Results");

        resultWebView=(WebView) findViewById(R.id.resultsWebView);
        progressBar = findViewById(R.id.progressBar);

        resultWebView.setWebViewClient(new WebViewClient());
        resultWebView.loadUrl("https://ghrce.raisoni.net/results");
        WebSettings webSettings=resultWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
    public class mywebClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            super.onPageStarted(view,url,favicon);

            // Show the progress bar when the page starts loading
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // Hide the progress bar when the page finishes loading
            progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void onBackPressed(){
        if(resultWebView.canGoBack()) {
            resultWebView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }
}
