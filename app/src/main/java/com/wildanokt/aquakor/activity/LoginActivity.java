package com.wildanokt.aquakor.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wildanokt.aquakor.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmail,edtPass;
    ProgressBar pgLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);

        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        pgLogin = findViewById(R.id.pg_login);
        pgLogin.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
    }

    private void userLogin(){

        String email = edtEmail.getText().toString().trim();
        String password = edtPass.getText().toString().trim();

        if (email.isEmpty()){
            edtEmail.setError("Field Required");
            edtEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Please use a valid email");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            edtPass.setError("Field Required");
            edtPass.requestFocus();
            return;
        }
        if (password.length()<6){
            edtPass.setError("Password must contain 6 character or more");
            edtPass.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        pgLogin.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_register:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
            case R.id.btn_login:
                pgLogin.setVisibility(View.VISIBLE);
                userLogin();
                break;
        }
    }
}
