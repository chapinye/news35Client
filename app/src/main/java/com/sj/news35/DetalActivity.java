package com.sj.news35;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class DetalActivity extends Activity implements View.OnClickListener {

    private String path;

    @ViewInject(R.id.news_detail_wv)
    private WebView webView;
    @ViewInject(R.id.loading_view)
    private View loading_view;
    @ViewInject(R.id.btn_left)
    private Button btn_left;

    @ViewInject(R.id.btn_right)
    private ImageButton btn_right;
    @ViewInject(R.id.imgbtn_left)
    private ImageButton imgbtn_left;
    @ViewInject(R.id.imgbtn_text)
    private ImageButton imgbtn_text;
    private WebSettings webViewSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news_detail);

        btn_left.setVisibility(View.GONE);
        btn_right.setVisibility(View.GONE);
        imgbtn_left.setVisibility(View.VISIBLE);
        imgbtn_left.setImageResource(R.drawable.back);
        imgbtn_left.setOnClickListener(this);
        imgbtn_text.setVisibility(View.VISIBLE);
        imgbtn_text.setImageResource(R.drawable.icon_textsize);
        imgbtn_text.setOnClickListener(this);

        ViewUtils.inject(this);

        webViewSettings = webView.getSettings();
        path = getIntent().getStringExtra("url");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loading_view.setVisibility(View.INVISIBLE);
            }
        });
        webView.loadUrl(path);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_text:
                switchTextSize(1);
                break;
            case R.id.imgbtn_left:
                finish();
                break;
        }
    }

    private void switchTextSize(int i) {
        switch (i) {
            case 1:
                webViewSettings.setTextSize(WebSettings.TextSize.LARGER);
                break;
            case 2:
                webViewSettings.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case 3:
                webViewSettings.setTextSize(WebSettings.TextSize.SMALLER);
                break;
        }
    }
}
