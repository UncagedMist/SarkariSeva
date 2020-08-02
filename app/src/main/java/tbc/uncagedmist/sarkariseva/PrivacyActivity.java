package tbc.uncagedmist.sarkariseva;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;

import tbc.uncagedmist.sarkariseva.Common.Common;

public class PrivacyActivity extends AppCompatActivity  {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        WebView webView = findViewById(R.id.webPrivacy);

        AppBarLayout toolbar = findViewById(R.id.app_bar);
        TextView txtTitle = toolbar.findViewById(R.id.tool_title);

        txtTitle.setText("Privacy Policy");

        webView.setWebViewClient(new MyWebViewClient());

        String url = Common.PRIVACY_APP.trim();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.loadUrl(url);
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
            progressDialog = new ProgressDialog(PrivacyActivity.this);
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