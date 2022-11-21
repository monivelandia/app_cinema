package com.example.aplicacion_cine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView mTextViewRegister;
    TextInputEditText mTextInputEditTextEmail ;
    TextInputEditText mTextInputEditTextPassword ;
    Button mButtonLogin;
    SignInButton mButtonLoginGoogle;
    FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private final int REQUEST_CODE_GOOGLE = 1; //NECESARO EL ENCAPSULADO
    FirebaseFirestore mFirestore;
    //Firestore almacena y firebase autentca


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewRegister= findViewById(R.id.TextViewRegister);
        mTextInputEditTextEmail= findViewById(R.id.TextInputEditTextEmail);
        mTextInputEditTextPassword= findViewById(R.id.TextInputEditTextPassword);
        mButtonLogin=findViewById(R.id.btnlogin);
        mButtonLoginGoogle =findViewById(R.id.btnloginSignInGoogle);


        mAuth=FirebaseAuth.getInstance();
        mFirestore= FirebaseFirestore.getInstance();


        mButtonLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
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

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_CODE_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Error", "Google sign in failed", e);
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String id = mAuth.getCurrentUser().getUid();
                            checkUserExist(id);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Error", "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    private void checkUserExist( final String id) {
        mFirestore.collection("Users").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //Elsnapshot trabaja con documentos
                if(documentSnapshot.exists()){ //si el documento existe
                    Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }else{
                    String email = mAuth.getCurrentUser().getEmail();
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", email);
                    mFirestore.collection("Users").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(MainActivity.this,CompleteProfileActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "No se pudo almacenar el usuario", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
            }
        });
    }

    private void login() {
        String email = mTextInputEditTextEmail.getText().toString();
        String password = mTextInputEditTextPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   Intent intent = new Intent( MainActivity.this, HomeActivity.class);
                   startActivity(intent);

               } else{
                   Toast.makeText(MainActivity.this, "El email y contraseña no son correctos", Toast.LENGTH_SHORT).show();
               }
            }
        });




        Log.d("campo", "email" + email);
        Log.d("campo", "password" + password);

    }
}