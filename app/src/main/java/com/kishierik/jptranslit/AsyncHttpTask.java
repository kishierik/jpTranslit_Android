package com.kishierik.jptranslit;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by kishierik on 2018/01/21.
 */

public class AsyncHttpTask extends AsyncTask<String, Integer, Document>{

    final String FURIGANA_TAG = "Furigana";
    final String SUBWORDLIST_TAG = "SubWordList";
    URL url;
    HttpsURLConnection urlConnection;
    SelectionTranslit selectionTranslitActivity;
    Document document;

    public AsyncHttpTask(SelectionTranslit activity) {
        selectionTranslitActivity = activity;
    }

    protected Document doInBackground(String... urls) {
        try {
            url = new URL(urls[0]);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/xml");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
            return document;
        }
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(Document result) {
        getFurigana(result);
    }

    private void getFurigana(Document document) {
        StringBuilder furiganaSentence = new StringBuilder();
        try {
            NodeList furiganaWords = document.getElementsByTagName(FURIGANA_TAG);
            NodeList subWordList = document.getElementsByTagName(SUBWORDLIST_TAG);
            for (int i = 0; i < furiganaWords.getLength(); i++)
            {
                if (subWordList.getLength() > 0 && i == 0) {
                    i = 1;
                }
                furiganaSentence.append(furiganaWords.item(i).getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            selectionTranslitActivity.displayText(furiganaSentence.toString());
        }
    }
}
