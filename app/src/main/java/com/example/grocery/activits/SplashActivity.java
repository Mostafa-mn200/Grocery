package com.example.grocery.activits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.grocery.MainActivity;
import com.example.grocery.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth=FirebaseAuth.getInstance();

        progressBar=findViewById(R.id.progressbar_splash);
        progressBar.setVisibility(View.GONE);
        if (auth.getCurrentUser()!=null){
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            Toast.makeText(this, "please waite you are already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void login(View view) {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
    }

    public void registration(View view) {
        startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
    }
}