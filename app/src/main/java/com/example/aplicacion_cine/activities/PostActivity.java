package com.example.aplicacion_cine.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacion_cine.R;
import com.example.aplicacion_cine.models.Post;
import com.example.aplicacion_cine.providers.AuthProviders;
import com.example.aplicacion_cine.providers.ImageProviders;
import com.example.aplicacion_cine.providers.PostProvider;
import com.example.aplicacion_cine.utils.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class PostActivity extends AppCompatActivity {

    ImageView mImageViewPost1;
    File mImageFile;
    private final int Gallery_REQUEST_CODE=1;
    Button mButtonPost;
    ImageProviders mImageProvider;
    TextInputEditText  mTextInputTittle;
    TextInputEditText  mTextInputDescription;
    ImageView mImageViewArte;
    ImageView mImageViewColors;
    ImageView mImageViewBooks;
    TextView mTextViewCategory;
    String mCategory="";
    PostProvider mPostProvider;
    String  mTittle= "";
    String mDescription = "";
    AuthProviders mAuthProviders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mImageViewPost1=findViewById(R.id.imageViewPost1);
        mTextInputTittle= findViewById(R.id.textInputTittle);
        mTextInputDescription= findViewById(R.id.textInputDescription);
        mImageViewArte= findViewById(R.id.imageViewArts);
        mImageViewColors= findViewById(R.id.imageViewColors);
        mImageViewBooks= findViewById(R.id.imageViewBooks);
        mTextViewCategory=findViewById(R.id.textViewCardCategory);

        mButtonPost=findViewById((R.id.btnpost));


        mImageProvider = new ImageProviders();
        mPostProvider= new PostProvider();
        mAuthProviders = new AuthProviders();

        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage(); //antes
                clickPost();
            }
        });



        mImageViewPost1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        mImageViewArte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory="Arte";
                mTextViewCategory.setText(mCategory); //Hace que cambie el titulo
            }
        });

        mImageViewColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory="Colores, marcadores";
                mTextViewCategory.setText(mCategory);
            }
        });
        mImageViewBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "Cuadernos, agendas";
                mTextViewCategory.setText(mCategory);
            }
        });
    }

    private void clickPost() {
        mTittle= mTextInputTittle.getText().toString();
         mDescription= mTextInputDescription.getText().toString();
        if(!mTittle.isEmpty() && !mDescription.isEmpty() && !mCategory.isEmpty()){
            if(mImageFile !=null){
                saveImage();

            }else{
                Toast.makeText(this, "Selecciona una imagen", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Completa los campos para continuar", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage() {
        mImageProvider.save(PostActivity.this, mImageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            Post post = new Post();
                            post.setImage1(url);
                            post.setTittle(mTittle);
                            post.setDescription(mDescription);
                            post.setCategory(mCategory);
                            post.setIdUser(mAuthProviders.getUid());

                            mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> tasksave) {
                                    if(tasksave.isSuccessful()){
                                        Toast.makeText(PostActivity.this, "La información de almaceno correctamente", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(PostActivity.this, "No se pudo almacenar la información", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                    Toast.makeText(PostActivity.this, "La imagen se almaceno correctamente", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(PostActivity.this, "Error al almacenar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT); //permite abrir la galeria
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* VALIDACION DE IMAGEN CON GALERIA */
        if (requestCode == Gallery_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                mImageFile = FileUtil.from(this, data.getData());
                mImageViewPost1.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
            } catch (Exception e) {
                Log.d("ERROR", "Se produjo un error " + e.getMessage());
                Toast.makeText(this, "Se produjo un error " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}