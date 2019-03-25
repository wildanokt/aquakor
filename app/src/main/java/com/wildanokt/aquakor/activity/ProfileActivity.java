package com.wildanokt.aquakor.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.wildanokt.aquakor.R;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    TextView tvName;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Profile");
        setSupportActionBar(toolbar);

        //nav drawer
        drawerLayout = findViewById(R.id.drawer_profile);
        navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        //widget
        tvName = findViewById(R.id.tv_profile_name);
        tvName.setText(mAuth.getCurrentUser().getEmail());

        //button
        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_home:
                drawerLayout.closeDrawers();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.menu_profile:
                drawerLayout.closeDrawers();
//                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
