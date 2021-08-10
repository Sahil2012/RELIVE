package com.example.relive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements log,mainRe{


    BottomNavigationView bottomNavigationView;
    Fragment frg = null;
    int past;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null){
            startActivity(new Intent(HomeActivity.this,LogInActivity.class));
            finish();
        }

        bottomNavigationView = findViewById(R.id.bnb);
        frg = new HomeFragment(HomeActivity.this,HomeActivity.this);
        past = R.id.homebtn;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fargHold,frg).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(past == item.getItemId()) {
                    return true;
                }

                switch (item.getItemId()) {

                    case R.id.homebtn:
                        frg = new HomeFragment(HomeActivity.this,HomeActivity.this);
                        past = R.id.homebtn;
                        break;

                    case R.id.request:
                        frg = new RequestFragment(HomeActivity.this);
                        past = R.id.request;
                        break;

                    case R.id.track:
                        frg = new TrackerFragment();
                        past = R.id.track;
                        break;

                    case R.id.profile:
                        frg = new ProfileFragment(HomeActivity.this);
                        past = R.id.profile;
                        break;
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fargHold,frg).commit();

                return true;
            }
        });
    }

    @Override
    public void logoutMet() {
        auth.signOut();
        startActivity(new Intent(HomeActivity.this,LogInActivity.class));
        finish();
    }

    @Override
    public void callMe(String s) {
        Uri uri = Uri.parse("tel:" + s);
        Intent it = new Intent(Intent.ACTION_DIAL,uri);

        try {
            startActivity(it);
        }catch (SecurityException e){
            Toast.makeText(HomeActivity.this,"Unable to open dailer",Toast.LENGTH_SHORT).show();
        }
    }
}