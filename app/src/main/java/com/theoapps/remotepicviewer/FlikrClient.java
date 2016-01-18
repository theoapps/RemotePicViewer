package com.theoapps.remotepicviewer;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by theo on 1/17/16.
 */
public class FlikrClient extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] params) {
        List<FlikrPhoto> flikrPhotos = new ArrayList<FlikrPhoto>();
        try {
            URL url = new URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=f1aabefff34953a00979a416dd232bbb&tags=vaporwave&per_page=5&page=1&format=json&nojsoncallback=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder lines = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.append(line + "\n");
                    Log.d("flikrClient", line);
                }
                JSONObject photos = new JSONObject(lines.toString()).getJSONObject("photos");
                JSONArray photoArray = photos.getJSONArray("photo");
                for (int i = 0; i < photoArray.length(); i++) {
                    FlikrPhoto flikrPhoto = new FlikrPhoto();
                    JSONObject photo = photoArray.getJSONObject(i);
                    flikrPhoto.setSecret(photo.getString("secret"));
                    flikrPhoto.setFarm(photo.getString("farm"));
                    flikrPhoto.setServer(photo.getString("server"));
                    flikrPhoto.setId(photo.getString("id"));
                    flikrPhotos.add(flikrPhoto);
                }
            }
            else {
                Log.d("flikrClient", "http error");
            }
        } catch (Exception e) {
            Log.d("flikrClient", "exception " + e);
            e.printStackTrace();
        }
        return flikrPhotos;
    }
}
