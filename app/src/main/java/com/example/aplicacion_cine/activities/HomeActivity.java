package com.example.aplicacion_cine.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.aplicacion_cine.R;
import com.example.aplicacion_cine.fragment.ChatsFragment;
import com.example.aplicacion_cine.fragment.FiltersFragment;
import com.example.aplicacion_cine.fragment.HomeFragment;
import com.example.aplicacion_cine.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new HomeFragment());
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId()==R.id.itemHome){
                        //aqui se abre un fragment home
                        openFragment(new HomeFragment());
                    }else if (item.getItemId()==R.id.itemFilter){
                        //aqui se abre un fragment filtros
                        openFragment(new FiltersFragment());
                    }else if (item.getItemId()==R.id.itemChats){
                        //aqui se abre un fragment chats
                        openFragment(new ChatsFragment());
                    }else if (item.getItemId()==R.id.itemPerfil){
                        //aqui se abre un fragment perfil
                        openFragment(new ProfileFragment());
                    }
                    return true;
                }
            };
}