package com.example.aplicacion_cine.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplicacion_cine.R;
import com.example.aplicacion_cine.models.User;
import com.example.aplicacion_cine.providers.AuthProviders;
import com.example.aplicacion_cine.providers.UsersProviders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    //instanciar, es decir asociarlo con el id y que interactue
    CircleImageView mCircleImageViewBack;
    TextInputEditText mTextInputEditTextUsernameR ;
    TextInputEditText mTextInputEditTextEmailR ;
    TextInputEditText mTextInputEditTextPasswordR ;
    TextInputEditText mTextInputEditTextConfirmPassword ;
    Button mButtonRegister;

    //FirebaseAuth mAuth;
    //FirebaseFirestore mFirestore;

    AuthProviders mAuthProvider;
    UsersProviders mUsersProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mCircleImageViewBack=findViewById(R.id.circleimageback);
        mTextInputEditTextUsernameR =findViewById(R.id.textInputEditTextUserName);
        mTextInputEditTextEmailR=findViewById(R.id.textInputEditTextEmailRegister);
        mTextInputEditTextPasswordR=findViewById(R.id.textInputEditTextPasswordRegister);
        mTextInputEditTextConfirmPassword=findViewById(R.id.textInputEditTextConfirmPassword);
        mButtonRegister=findViewById(R.id.btnregister);

        //mAuth = FirebaseAuth.getInstance();
        //mFirestore = FirebaseFirestore.getInstance();

        mAuthProvider= new AuthProviders();
        mUsersProvider = new UsersProviders();
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
        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmpassword.isEmpty()){
            // si usuario es diferente de vacio, el email, contraseña
            //USO EQUALS PORQE SON OBJETOS
            if (isEmailValid(email)){

                if(password.equals(confirmpassword)){
                    if(password.length() >=6){
                        createUser(username,email,password);
                    }else {
                        Toast.makeText(this, "Las contraseñas debe tener 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "inserto todos los campos pero el correo no es valido", Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(this, "para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser(final String username, final String email, String password) {
        mAuthProvider.register(email, password)
         .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id= mAuthProvider.getUid();
                    User user = new User();
                    user.setId(id);
                    user.setEmail(email);
                    user.setUsername(username);
                    user.setPassword(password);
                    mUsersProvider.create(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "El usuario se almaceno con éxito", Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(RegisterActivity.this, "El usuario no se pudo almacenar en la base de datos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    Toast.makeText(RegisterActivity.this, "El usuario se ha registrado correctamente", Toast.LENGTH_LONG).show();
                }else{Toast.makeText(RegisterActivity.this,"No se pudo registrar el usuario",Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}