package com.example.aplicacion_cine.providers;

import com.example.aplicacion_cine.models.Post;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Collection;

public class PostProvider {
    CollectionReference mCollection;

    public PostProvider() {
        mCollection = FirebaseFirestore.getInstance().collection("Post");
    }

    public Task<Void> save(Post post){
     return  mCollection.document().set(post);
    }
}
