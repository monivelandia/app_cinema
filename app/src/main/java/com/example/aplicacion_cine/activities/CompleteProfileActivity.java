package com.example.aplicacion_cine.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacion_cine.R;
import com.example.aplicacion_cine.models.User;
import com.example.aplicacion_cine.providers.AuthProviders;
import com.example.aplicacion_cine.providers.UsersProviders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import dmax.dialog.SpotsDialog;

public class CompleteProfileActivity extends AppCompatActivity {
    TextInputEditText mTextInputUsername;
    Button mButtonRegister;
    AuthProviders mAuthProviders;
    UsersProviders mUsersproviders;
    AlertDialog mDialog;
    //FirebaseAuth mAuth;
    //FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        mTextInputUsername = findViewById(R.id.textInputEditTextUserNameC);
        mButtonRegister = findViewById(R.id.btnConfirmar);

        mAuthProviders = new AuthProviders(); //se instancia distinto debido a que es una clase (metodo)
        mUsersproviders = new UsersProviders();


        mDialog= new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });
    }

    private void register() {
        String username = mTextInputUsername.getText().toString();
        if(!username.isEmpty()){
            updateUser(username);
        }else{
            Toast.makeText(this, "Para continuar inserta el nombre de usuario", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUser(String username) {
        String id =mAuthProviders.getUid();
        User user = new User();
        user.setUsername(username);
        user.setId(id);
        mDialog.show();
        mUsersproviders.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mDialog.dismiss();
                if (task.isSuccessful()){
                    Intent intent = new Intent(CompleteProfileActivity.this,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(CompleteProfileActivity.this, "No se almacenó el usuario en la base datos", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}