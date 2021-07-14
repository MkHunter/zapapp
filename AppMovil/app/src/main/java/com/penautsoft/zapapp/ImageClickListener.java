package com.penautsoft.zapapp;

import android.graphics.Bitmap;

public interface ImageClickListener {

    void onImageClick(int idx, Bitmap image,int viewId);

    void onLogImageLick(int idx, Bitmap image);
}
