package com.example.aplicacion_cine.providers;

import android.content.Context;

import com.example.aplicacion_cine.utils.CompressorBitmapImage;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.DataInput;
import java.io.File;
import java.util.Date;

public class ImageProviders {
    StorageReference mStorage;

    public ImageProviders() {
        mStorage= FirebaseStorage.getInstance().getReference();
    }

    public UploadTask save(Context context, File file){
        byte[] imageByte= CompressorBitmapImage.getImage(context, file.getPath(), 500, 500);
        StorageReference storage = mStorage.child(new Date() + "jpg");
        UploadTask task = storage.putBytes(imageByte);
        return  task;


    }
}
