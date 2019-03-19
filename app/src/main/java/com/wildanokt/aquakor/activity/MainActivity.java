package com.wildanokt.aquakor.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wildanokt.aquakor.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    Button btnRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.login);
        btnRegis = findViewById(R.id.regis);

        btnLogin.setOnClickListener(this);
        btnRegis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.login:
                intent = new Intent(this, LoginActivity.class);
                break;
            case R.id.regis:
                intent = new Intent(this, RegistrationActivity.class);
                break;
        }
        startActivity(intent);
    }
}
