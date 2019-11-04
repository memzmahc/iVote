package com.android.online.voteapp;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class success extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
    }
    public void sendhome(View view){
        Intent startnew1 = new Intent(this,list.class);
        startActivity(startnew1);
    }
    public void exitbtn(View view){
        finish();
        moveTaskToBack(true);
    }
}
