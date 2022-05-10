package com.example.grocery.activits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.MainActivity;
import com.example.grocery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button signIn;
    EditText memail,mpass;
    TextView signUp;
    ProgressBar progressBar;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        initial();
    }

    private void initial() {
        signIn=findViewById(R.id.login_btn);
        memail=findViewById(R.id.email_login);
        mpass=findViewById(R.id.pass_login);
        signUp=findViewById(R.id.sign_up);
        progressBar=findViewById(R.id.progressbar_log);
        progressBar.setVisibility(View.GONE);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                loginUser();
                progressBar.setVisibility(View.VISIBLE);
                break;
            case R.id.sign_up:
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                break;
        }
    }

    private void loginUser() {
        String email=memail.getText().toString();
        String password=mpass.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this, "email is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "password is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length()<6){
            Toast.makeText(LoginActivity.this, "password length must be greater than 6 letters", Toast.LENGTH_SHORT).show();
            return;
        }
        //login user
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "error"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}