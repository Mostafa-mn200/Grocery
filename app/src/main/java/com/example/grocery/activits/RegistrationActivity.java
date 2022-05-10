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
import com.example.grocery.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    Button signUp;
    EditText mname,memail,mpass;
    TextView signIn;
    ProgressBar progressBar;

    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        initial();
    }

    private void initial() {
        signUp=findViewById(R.id.login_reg_btn);
        mname=findViewById(R.id.name_reg);
        memail=findViewById(R.id.email_reg);
        mpass=findViewById(R.id.pass_reg);
        signIn=findViewById(R.id.sign_in);
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_reg_btn:
                creatUser();
                progressBar.setVisibility(View.VISIBLE);
                break;
            case R.id.sign_in:
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                break;
        }
    }

    private void creatUser() {
        String name=mname.getText().toString();
        String email=memail.getText().toString();
        String password=mpass.getText().toString();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(RegistrationActivity.this, "Name is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)){
            Toast.makeText(RegistrationActivity.this, "email is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(RegistrationActivity.this, "password is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length()<6){
            Toast.makeText(RegistrationActivity.this, "password length must be greater than 6 letters", Toast.LENGTH_SHORT).show();
            return;
        }

        //create user
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    UserModel userModel=new UserModel(name,email,password);
                    String id=task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(userModel);
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "error"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}