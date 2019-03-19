package com.wildanokt.aquakor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.wildanokt.aquakor.R;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtFName,edtUName,edtId,edtPhone,edtEmail,edtPass,edtRePass;

    ProgressBar pgRegis;

    //firebase instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViewById(R.id.tv_login).setOnClickListener(this);

        edtFName = findViewById(R.id.edt_name);
        edtUName = findViewById(R.id.edt_uname);
        edtId = findViewById(R.id.edt_id);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);
        edtRePass = findViewById(R.id.edt_repassword);
        pgRegis = findViewById(R.id.pg_regis);
        pgRegis.setVisibility(View.GONE);

        findViewById(R.id.btn_register).setOnClickListener(this);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser(){

        String email = edtEmail.getText().toString().trim();
        String password = edtPass.getText().toString().trim();

        if ((edtFName.getText().toString().trim()).isEmpty()){
            edtFName.setError("Field Required");
            edtFName.requestFocus();
            return;
        }
        if ((edtUName.getText().toString().trim()).isEmpty()){
            edtUName.setError("Field Required");
            edtUName.requestFocus();
            return;
        }
        if ((edtId.getText().toString().trim()).isEmpty()){
            edtId.setError("Field Required");
            edtId.requestFocus();
            return;
        }
        if ((edtId.getText().toString().trim()).length()<16){
            edtId.setError("Use valid identity Id");
            edtId.requestFocus();
            return;
        }
        if ((edtPhone.getText().toString().trim()).isEmpty()){
            edtPhone.setError("Field Required");
            edtPhone.requestFocus();
            return;
        }
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
        if ((edtRePass.getText().toString().trim()).isEmpty()){
            edtRePass.setError("Field Required");
            edtRePass.requestFocus();
            return;
        }
        if (!(edtRePass.getText().toString().trim())
                .equals(password)){
            edtRePass.setError("Password not match");
            edtRePass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        edtEmail.setError("Email already registered");
                        edtEmail.requestFocus();
                    }else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                pgRegis.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btn_register:
                pgRegis.setVisibility(View.VISIBLE);
                registerUser();
                break;
        }
    }

}
