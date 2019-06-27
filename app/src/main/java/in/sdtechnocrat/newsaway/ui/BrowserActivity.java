package in.sdtechnocrat.newsaway.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import in.sdtechnocrat.newsaway.R;

public class BrowserActivity extends AppCompatActivity {

    Toolbar toolbar;
    WebView webView;
    TextView txtTitle, txtUrl;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.webView);
        txtTitle = findViewById(R.id.txtTitle);
        txtUrl = findViewById(R.id.txtUrl);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);

        Intent intent = getIntent();

        if (intent != null) {
            url = intent.getStringExtra("URL");

            txtUrl.setText(url);
            txtUrl.setVisibility(View.VISIBLE);
            txtTitle.setVisibility(View.GONE);

            webView.loadUrl(url);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    txtTitle.setText(view.getTitle());
                    txtUrl.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Or do you own task
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
