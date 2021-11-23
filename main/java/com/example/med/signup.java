package com.example.med;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    TextInputEditText mFullName,mEmail,mPassword, mPhone,Agemo;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    TextView log;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        log=(TextView)findViewById(R.id.in);
        mFullName=findViewById(R.id.name);
        mEmail=findViewById(R.id.em);
        mPassword=findViewById(R.id.pass);
        mPhone=findViewById(R.id.Num);
        Agemo=findViewById(R.id.Age);

        mRegisterBtn=findViewById(R.id.btnRegister);
        fAuth=FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.pb);

        if(fAuth.getCurrentUser () != null) {
            startActivity (new Intent (getApplicationContext(), signup.class));
            finish ();
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View a) {
                intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);


            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View z) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }
                if (password.length () < 6) {
                    mPassword.setError ("Password Must be >= 6 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                // register the user in firebase
                fAuth.createUserWithEmailAndPassword (email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()){
                            Toast.makeText (signup.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent (getApplicationContext (),Menu.class));
                        }else {
                            Toast.makeText (signup.this,"Error ! " + task.getException ().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                });
            }

        });
    }
}