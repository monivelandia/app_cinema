package com.example.aplicacion_cine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    TextView mTextViewRegister = findViewById(R.id.TextViewRegister);
    TextInputEditText mTextInputEditTextEmail = findViewById(R.id.TextInputEditTextEmail);
    TextInputEditText mTextInputEditTextPassword = findViewById(R.id.TextInputEditTextPassword);
    Button mButtonlogin = findViewById(R.id.btnlogin);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mButtonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String email = mTextInputEditTextEmail.getText().toString();
        String password = mTextInputEditTextPassword.getText().toString();
        Log.d("campo", "email" + email);
        Log.d("campo", "password" + password);

    }
}