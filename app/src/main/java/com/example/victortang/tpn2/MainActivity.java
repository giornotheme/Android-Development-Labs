package com.example.victortang.tpn2;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String res ="false";
    private static ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startnewactivity = findViewById(R.id.flickr);

        startnewactivity.setOnClickListener(v -> {
            Intent newActivity = new Intent(MainActivity.this, Flickr.class);
            startActivity(newActivity);
        });


        final Button auth_button = findViewById(R.id.authentication_activity_play_authenticate);
        auth_button.setOnClickListener(v -> thread.start());

    }

    //le thread sera lancé uniquement lorsque l'utilisateur aura appuyé sur le bouton Authenticate
    @SuppressLint("SetTextI18n")
    Thread thread = new Thread(() -> {

        URL url;
        try {
            url = new URL("https://httpbin.org/basic-auth/bob/sympa");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //Pour pouvoir remplacer les credentials, on récupère les deux EditText via leur id
            String log = ((EditText) findViewById(R.id.logField)).getText().toString();
            String pass = ((EditText) findViewById(R.id.passwordField)).getText().toString();

            String basicAuth = "Basic " + Base64.encodeToString((log+":"+pass).getBytes(), Base64.NO_WRAP);
            urlConnection.setRequestProperty ("Authorization", basicAuth);
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                Log.i("JFL", s);

                //Création du JSONObject permettant de transmettre les données entre un serveur et une application WEB
                //Ici nous récupérons la var du fichier JSON authenticated et nous le stockons dans une var JSONObject
                try {
                    JSONObject resJson = new JSONObject(s);
                    res = resJson.getString("authenticated");
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                runOnUiThread(() -> {
                    //We refresh the result TextView
                    TextView result = findViewById(R.id.result);
                    result.setText("My result here : " + res);
                });
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    });






    //Méthode pour lire le steam lorsqu'on fait une requête
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    public static void setRes(Bitmap bm){
        img.setImageBitmap(bm);
    }
}