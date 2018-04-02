package com.example.thebestteam.cs495capstonecomputing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class URLLinker extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urllinker);


        //gets the info that is passed with the intent
        String title = this.getIntent().getExtras().getString("title");
        String url = this.getIntent().getExtras().getString("url");

        //sets the title of the action bar
        setTitle(title);

        //initialize this to the webview that is in the xml file
        mWebView = (WebView) findViewById(R.id.detail_web_view);

        //actually load the URL
        //for now just my question on stack overflow
        mWebView.loadUrl("https://stackoverflow.com/questions/38286418/incompatible-type-on-assignment-from-bstnode-to-bstnode");
        //mWebView.loadUrl(url);
    }
}
