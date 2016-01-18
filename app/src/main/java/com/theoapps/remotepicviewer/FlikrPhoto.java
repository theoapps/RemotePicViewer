package com.theoapps.remotepicviewer;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by theo on 1/16/16.
 */
public class FlikrPhoto {
    String id;
    String server;
    String farm;
    String secret;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getURI() {
        //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        String urlString = "https://farm" + farm + ".staticflickr.com/"+ server +"/"+ id +"_"+ secret +".jpg";
        return urlString;
    }

    static void getData() {

    }
}
