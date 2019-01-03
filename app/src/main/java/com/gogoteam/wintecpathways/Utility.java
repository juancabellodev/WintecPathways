package com.gogoteam.wintecpathways;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Utility {
    //convert from bitmap to byte array
    public static byte[] getBytes (Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    //convert from byte array to bitmap
    public static Bitmap getPhoto(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
