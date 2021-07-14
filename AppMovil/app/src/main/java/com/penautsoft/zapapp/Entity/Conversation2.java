package com.penautsoft.zapapp.Entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.penautsoft.zapapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Conversation2 implements Parcelable {

    private String mainMessage;
    private String photos;
    private Date date,currentDate;
    private Object user;
    private ArrayList<Comment> comments;

    public Conversation2(Context ctx){
        this.mainMessage = ctx.getString(R.string.lorem_ipsum);
    }

    public Conversation2(Parcel in){
        readFromParcel(in);
    }

    public Conversation2(String msg){
        this.mainMessage = msg;
    }

    public Conversation2(String msg, String images, Date date){
        this.mainMessage = msg;
        this.photos = images;
        this.date = date;
    }

    public Conversation2(String msg, String images){
        this.mainMessage = msg;
        this.photos = images;
    }

    public String getMainMessage() {
        return mainMessage;
    }

    public void setMainMessage(String mainMessage) {
        this.mainMessage = mainMessage;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Conversation2 createFromParcel(Parcel in) {
            return new Conversation2(in);
        }

        public Conversation2[] newArray(int size) {
            return new Conversation2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mainMessage);
        parcel.writeString(photos);
        parcel.writeValue(date);
        parcel.writeValue(currentDate);
        parcel.writeValue(user);
        parcel.writeValue(comments);
    }



    private void readFromParcel(Parcel in){
        mainMessage = in.readString();
        photos = in.readString();
        /*photos = in.readValue();
        date = in.;
        currentDate = in.;
        user = in.;
        comments = in.;*/
    }


}
