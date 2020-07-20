package tbc.uncagedmist.sarkariseva;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;

import tbc.uncagedmist.sarkariseva.Common.Common;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        WebView webView = findViewById(R.id.webView);

        AppBarLayout toolbar = findViewById(R.id.app_bar);
        TextView txtTitle = toolbar.findViewById(R.id.tool_title);

        txtTitle.setText(Common.CurrentDetail.getName());

        webView.setWebViewClient(new MyWebViewClient());

        String url = Common.CurrentDetail.getWeb().trim();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    private static class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}