package com.kishierik.jptranslit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class SelectionTranslit extends AppCompatActivity {

    final String YAHOO_API_URL = "https://jlp.yahooapis.jp/FuriganaService/V1/furigana?appid=";

    // YahooDevelopersから取得したAPIキー
    final String YAHOO_API_KEY = "";
    String url;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_translit);
        initialize();
    }

    private void initialize() {
        resultTextView = findViewById(R.id.resultTextView);
        generateApiUrl(getSelectedText());
        new AsyncHttpTask(this).execute(url);
    }

    private void generateApiUrl(CharSequence furigana) {
        url = YAHOO_API_URL + YAHOO_API_KEY + "&sentence=" + furigana;
    }

    private CharSequence getSelectedText() {
        CharSequence selection = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        return selection;
    }

    public void displayText(CharSequence text) {
        resultTextView.setText(text);
    }


}
