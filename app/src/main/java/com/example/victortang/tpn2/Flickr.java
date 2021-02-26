package com.example.victortang.tpn2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Flickr extends AppCompatActivity {

    private Button Getimage;
    private static ImageView img;
    public static Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr);

        img = (ImageView)findViewById(R.id.image);
        Getimage = (Button)findViewById(R.id.getImageButton);

        Getimage.setOnClickListener(new GetImageOnClickListener(){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                setRes(bm);
            }
        });


    }

    public static void setRes(Bitmap bm){
        img.setImageBitmap(bm);
    }
}