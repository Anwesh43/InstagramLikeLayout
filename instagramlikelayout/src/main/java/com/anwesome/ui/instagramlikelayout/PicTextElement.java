package com.anwesome.ui.instagramlikelayout;

import android.graphics.Bitmap;

/**
 * Created by anweshmishra on 21/12/16.
 */
public class PicTextElement {
    private String profileName,status="";
    private Bitmap mainBitmap,profileBitmap;
    private int numberOfLikes = 0;

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public PicTextElement(String profileName, Bitmap bitmap,Bitmap profileBitmap) {
        this.profileName = profileName;
        this.mainBitmap = bitmap;
        this.profileBitmap = profileBitmap;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bitmap getMainBitmap() {
        return mainBitmap;
    }

    public void setMainBitmap(Bitmap mainBitmap) {
        this.mainBitmap = mainBitmap;
    }

    public Bitmap getProfileBitmap() {
        return profileBitmap;
    }

    public void setProfileBitmap(Bitmap profileBitmap) {
        this.profileBitmap = profileBitmap;
    }

    public Bitmap getBitmap() {
        return mainBitmap;
    }
    public String getprofileName() {
        return profileName;
    }
    
    public int hashCode() {
        return profileName.hashCode()+mainBitmap.hashCode()+numberOfLikes;
    }
}
