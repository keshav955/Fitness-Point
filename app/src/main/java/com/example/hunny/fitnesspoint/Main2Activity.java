package com.example.hunny.fitnesspoint;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setStatusBarColor(Color.argb(255,8,9,9));
    }

    public void login(View v)
    {
        Intent i = new Intent(Main2Activity.this, Login.class);

        startActivity(i);
    }
}
