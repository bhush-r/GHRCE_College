package bhushan.org.GHRCEUSER;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class Results extends AppCompatActivity {
    private WebView resultWebView;
    private ProgressBar progressBar;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Results");

        FirebaseMessaging.getInstance().subscribeToTopic("Result");

        resultWebView = findViewById(R.id.resultsWebView);
        progressBar = findViewById(R.id.progressBar);
        errorMessage = findViewById(R.id.errorMessage);

        resultWebView.setWebViewClient(new CustomWebViewClient());
        resultWebView.loadUrl("https://ghrce.raisoni.net/results");
        WebSettings webSettings = resultWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            errorMessage.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            progressBar.setVisibility(View.GONE);
            errorMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (resultWebView.canGoBack()) {
            resultWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
