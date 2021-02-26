package com.example.victortang.tpn2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {

    private final String httpUrl;
    private Bitmap bm;

    public AsyncBitmapDownloader(String str){
        this.httpUrl = str;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        URL url;

        try {
            url = new URL(this.httpUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            bm = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        MainActivity.setRes(bm);

    }
}