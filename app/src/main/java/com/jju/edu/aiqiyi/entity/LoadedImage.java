package com.jju.edu.aiqiyi.entity;

import android.graphics.Bitmap;

public class LoadedImage {
    Bitmap mBitmap;

public LoadedImage(Bitmap bitmap) {
    mBitmap = bitmap;
}

public Bitmap getBitmap() {
    return mBitmap;
}
}
