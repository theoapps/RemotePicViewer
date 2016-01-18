package com.theoapps.remotepicviewer;

import android.os.Parcel;
import android.os.Parcelable;
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
public class FlikrPhoto implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.server);
        dest.writeString(this.farm);
        dest.writeString(this.secret);
    }

    public FlikrPhoto() {
    }

    protected FlikrPhoto(Parcel in) {
        this.id = in.readString();
        this.server = in.readString();
        this.farm = in.readString();
        this.secret = in.readString();
    }

    public static final Creator<FlikrPhoto> CREATOR = new Creator<FlikrPhoto>() {
        public FlikrPhoto createFromParcel(Parcel source) {
            return new FlikrPhoto(source);
        }

        public FlikrPhoto[] newArray(int size) {
            return new FlikrPhoto[size];
        }
    };
}
