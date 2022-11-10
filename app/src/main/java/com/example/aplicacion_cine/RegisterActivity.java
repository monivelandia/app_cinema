package com.example.aplicacion_cine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    CircleImageView mCircleImageViewBack;
    TextInputEditText mTextInputEditTextUsernameR;
    TextInputEditText mTextInputEditTextEmailR;
    TextInputEditText mTextInputEditTextPasswordR;
    TextInputEditText mTextInputEditTextConfirmPassword;
    Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //instanciar, es decir asociarlo con el id y que interactue
        mCircleImageViewBack = findViewById(R.id.circleimageback);
        mTextInputEditTextUsernameR=findViewById(R.id.textInputEditTextUserName);
        mTextInputEditTextEmailR = findViewById(R.id.textInputEditTextEmailRegister);
        mTextInputEditTextPasswordR= findViewById(R.id.textInputEditTextPasswordRegister);
        mTextInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword);
        mButtonRegister = findViewById(R.id.btnregister);
        //re de recursos
        //evento del regstro
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        mCircleImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
//creamos el metodo register (independiente)
    private void register() {
        String username = mTextInputEditTextUsernameR.getText().toString();
        String email = mTextInputEditTextEmailR.getText().toString();
        String password = mTextInputEditTextPasswordR.getText().toString();
        String confirmpassword = mTextInputEditTextConfirmPassword.getText().toString(); // lo que ingresa el usuario

        //para que lo queden campoas vacios: ! indica dieferente de vacio
        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() &&  confirmpassword.isEmpty()){
            // si usuario es diferente de vacio, el email, contraseña
            //USO EQUALS PORQE SON OBJETOS
             if(isEmailValid(email)){
                 if(password.equals(confirmpassword)){
                     if (password.length() >= 8) {
                         createUser(email, password);
                     }else {
                         Toast.makeText(this, "La contraseña debe tener 6 más caracteres", Toast.LENGTH_SHORT);
                     }
                 }else{
                     Toast.makeText(this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT);
                 }
             }else{
                 Toast.makeText(this,"Inserto todos los campos pero el correo no es válido",Toast.LENGTH_SHORT);
             } 
        } else  {
            Toast.makeText(this,"Para continuar inserta todo los campos",Toast.LENGTH_LONG).show();
        }
    }

    private void createUser(String email, String password) {
    }

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}