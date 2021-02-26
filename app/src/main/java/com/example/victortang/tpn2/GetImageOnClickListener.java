package com.example.victortang.tpn2;

import android.view.View;

public class GetImageOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        try{
            AsyncFlickrJSONData task = new AsyncFlickrJSONData("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}