package com.example.testapp;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText mUsername,mEmail,mPassword,mPhone,mProfession;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUsername=findViewById(R.id.username);
        mEmail=findViewById(R.id.gmail_id);
        mPassword=findViewById(R.id.password);
        mPhone=findViewById(R.id.phno);
        mProfession=findViewById(R.id.profession);
        mRegisterBtn=findViewById(R.id.reg_but);
        mLoginBtn=findViewById(R.id.login_but);

        fAAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        if(fAAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login_activity.class));
                finish();
            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String user=mUsername.getText().toString().trim();
                if(TextUtils.isEmpty(user))
                {
                    mUsername.setError("Username is required");
                    return;
                }
                String regex = "^[A-Za-z]\\w{5,29}$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(user);
                if(!m.matches())
                {
                    mUsername.setError("Invalid Username");
                    return;
                }
                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Gmail-ID is required");
                    return;
                }
                if(password.length()<8)
                {
                    mPassword.setError("Password of length >=8 is required");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                // register the user in firebase
                fAAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(Register.this, "Error ! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}