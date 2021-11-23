package com.example.med;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class login extends AppCompatActivity {
    TextView reg2;
    TextInputEditText lemail,lpass;
    Button mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    Intent intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        reg2=(TextView)findViewById(R.id.magregister);
        lemail=findViewById(R.id.le);
        lpass=findViewById(R.id.lp);
        mLoginBtn=findViewById(R.id.btnLogin);
        fAuth=FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.pbb);

        reg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View b) {
                intent2 = new Intent(getApplicationContext(),Register.class);
                startActivity(intent2);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lemail.getText().toString().trim();
                String password = lpass.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    lemail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    lpass.setError("Password is Required.");
                    return;
                }
                if (password.length () < 6) {
                    lpass.setError ("Password Must be >= 6 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful ()){
                            Toast.makeText (login.this,"Logged in Success",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent (getApplicationContext (),Menu.class));
                        }else {
                            Toast.makeText (login.this,"Error ! " + task.getException ().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });
    }
}