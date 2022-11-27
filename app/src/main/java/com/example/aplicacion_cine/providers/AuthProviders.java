package com.example.aplicacion_cine.providers;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthProviders {


    private FirebaseAuth mAuth;

    public AuthProviders() {
        mAuth=FirebaseAuth.getInstance();
    }

    public Task<AuthResult> register(String email, String password){
        return  mAuth.createUserWithEmailAndPassword(email,password);

    }
    //Autenticacion a traves del login
    public Task<AuthResult> login(String email, String password){
        return mAuth.signInWithEmailAndPassword(email,password);
    }


    public Task<AuthResult> googleLogin(GoogleSignInAccount googleSignInAccount){
        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        return mAuth.signInWithCredential(credential); //metodo que se encarga de autenticar con google
    }

    public  String getEmail(){
        if (mAuth.getCurrentUser() != null){ //si  la autenticacion es diferente de vacio{
            return mAuth.getCurrentUser().getEmail(); // me retorna la autentcacion a traves de getcurrentuser
        } else{
            return null;
        }
    }

    public String  getUid(){
        if (mAuth.getCurrentUser() != null){ //si  la autenticacion es diferente de vacio{
            return mAuth.getCurrentUser().getUid(); // me retorna la autentcacion a traves de getcurrentuser
        } else{
            return null;
        }
    }

}
