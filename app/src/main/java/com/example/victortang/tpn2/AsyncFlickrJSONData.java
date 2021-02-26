package com.example.victortang.tpn2;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {
    private String httpUrl;
    private JSONObject myJSONObject;

    //nous reprenons l'URL que nous avions mis dans la class GetImageOnClickListener
    public AsyncFlickrJSONData(String httpUrl){
        this.httpUrl = httpUrl;
    }
    //On refait la même chose pour l'exercice 1 lorsqu'il sagissait de se connecter
    @Override
    protected JSONObject doInBackground(String... strings) {
        URL url;
        try {
            url = new URL(this.httpUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                Log.i("JFL", s);
                //pareillement nous créeons un JSONObject pour récupérer les datas pour les stocker dans un var
                try {
                    myJSONObject = new JSONObject(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }finally {
                urlConnection.disconnect();
            }
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return myJSONObject;
    }

    @Override
    /*protected void onPostExecute(JSONObject myJSONObject) {
        try {
            String urlImage = myJSONObject.getJSONArray("items").getJSONObject(0).getJSONObject("media").getString("m");
            Log.i("TE", urlImage);
            AsyncBitmapDownloader bmdownloader = new AsyncBitmapDownloader(urlImage);
            bmdownloader.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
    protected void onPostExecute(JSONObject myJSONObject) {
        try {
            String urlImage = myJSONObject.getJSONArray("items").getJSONObject(0).getJSONObject("media").getString("m");
            Log.i("TE", urlImage);
            AsyncBitmapDownloader bmdownloader = new AsyncBitmapDownloader(urlImage);
            bmdownloader.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //même méthode readStream que dans MainActivity
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString().replace("jsonFlickrFeed(","");
        } catch (IOException e) {
            return "";
        }
    }
}

