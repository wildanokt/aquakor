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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wildanokt.aquakor.R;
import com.wildanokt.aquakor.model.User;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtFName,edtUName,edtId,edtAddress,edtPhone,edtEmail,edtPass,edtRePass;

    ProgressBar pgRegis;

    //firebase instance
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");

        findViewById(R.id.tv_login).setOnClickListener(this);

        edtFName = findViewById(R.id.edt_name);
        edtUName = findViewById(R.id.edt_uname);
        edtId = findViewById(R.id.edt_id);
        edtAddress = findViewById(R.id.edt_address);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);
        edtRePass = findViewById(R.id.edt_repassword);

        pgRegis = findViewById(R.id.pg_regis);
        pgRegis.setVisibility(View.GONE);

        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    private void registerUser(){
        final String fname = edtFName.getText().toString().trim();
        final String uname = edtUName.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String civId  = edtId.getText().toString().trim();
        final String address = edtAddress.getText().toString().trim();
        String password = edtPass.getText().toString().trim();
        final String phone = edtPhone.getText().toString().trim();

        if (fname.isEmpty()){
            edtFName.setError("Field Required");
            edtFName.requestFocus();
            return;
        }
        if (uname.isEmpty()){
            edtUName.setError("Field Required");
            edtUName.requestFocus();
            return;
        }
        if (civId.isEmpty()){
            edtId.setError("Field Required");
            edtId.requestFocus();
            return;
        }
        if (civId.length()!=16){
            edtId.setError("Use valid identity Id");
            edtId.requestFocus();
            return;
        }
        if (address.isEmpty()){
            edtAddress.setError("Field Required");
            edtAddress.requestFocus();
            return;
        }
        if (phone.isEmpty()){
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
        pgRegis.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //save user info to database
                    String id = task.getResult().getUser().getUid();
                    User user = new User(fname, uname, email, civId, address, phone);
                    userDatabase.child(id).setValue(user);

                    Intent intent = new Intent(RegistrationActivity.this, ProfileActivity.class);
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
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.btn_register:
                registerUser();
                break;
        }
    }

}
