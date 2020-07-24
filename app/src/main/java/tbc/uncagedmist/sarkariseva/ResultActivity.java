package tbc.uncagedmist.sarkariseva;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.AppBarLayout;

import tbc.uncagedmist.sarkariseva.Common.Common;

public class ResultActivity extends AppCompatActivity {

    AdView resultBanner;
    WebView webView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        webView = findViewById(R.id.webResult);
        resultBanner = findViewById(R.id.resultBanner);

        AppBarLayout toolbar = findViewById(R.id.app_bar);
        TextView txtTitle = toolbar.findViewById(R.id.tool_title);

        txtTitle.setText(Common.CurrentDetail.getName());

        AdRequest adRequest = new AdRequest.Builder().build();
        resultBanner.loadAd(adRequest);

        webView.setWebViewClient(new MyWebViewClient());

        String url = Common.CurrentDetail.getWeb().trim();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.loadUrl(url);

        resultBanner.setAdListener(new AdListener()   {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressDialog = new ProgressDialog(ResultActivity.this);
            progressDialog.setMessage("Please wait ...");
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }
    }
}