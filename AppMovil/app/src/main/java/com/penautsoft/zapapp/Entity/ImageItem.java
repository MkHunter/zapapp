package com.penautsoft.zapapp.Entity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

@SuppressLint("ParcelCreator")

public class ImageItem implements AsymmetricItem {

    private int columnSpan, rowSpan, position;
    private Bitmap bitmap;

    public ImageItem(){
        this(1,1,1,null);
    }

    public ImageItem(int colSpa,int rowSpa,int pos,Bitmap btm){
        this.columnSpan = colSpa;
        this.rowSpan = rowSpa;
        this.position = pos;
        this.bitmap = btm;
    }

    public ImageItem(Parcel in){
        readFromParcel(in);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void changeSpan(int colSpan,int rowSpan){
        this.columnSpan = colSpan;
        this.rowSpan = rowSpan;
    }

    public void setPosition(int pos){
        this.position = pos;
    }

    @Override
    public int getColumnSpan() {
        return columnSpan;
    }

    @Override
    public int getRowSpan() {
        return rowSpan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in) {
        columnSpan = in.readInt();
        rowSpan = in.readInt();
        position = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(columnSpan);
        parcel.writeInt(rowSpan);
        parcel.writeInt(position);
    }

    public static final Parcelable.Creator<ImageItem> CREATOR = new Parcelable.Creator<ImageItem>() {

        @Override public ImageItem createFromParcel(@NonNull Parcel in) {
            return new ImageItem(in);
        }

        @Override @NonNull
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };


    @Override
    public String toString() {
        return "ImageItem{" +
                "columnSpan=" + columnSpan +
                ", rowSpan=" + rowSpan +
                ", position=" + position +
                ", bitmap=" + bitmap +
                '}';
    }
}
