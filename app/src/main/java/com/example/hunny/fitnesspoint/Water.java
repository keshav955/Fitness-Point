package com.example.hunny.fitnesspoint;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import cn.fanrunqi.waveprogress.WaveProgressView;
import me.itangqi.waveloadingview.WaveLoadingView;

public class Water extends AppCompatActivity {

    WaveProgressView waveProgressbar;
    WaveLoadingView mWaveLoadingView;

    ImageView add,cup,juice,glass,shake,bottle,mug,jug;

    int weight = 76;

    int age = 20;

    String gender = "male";

    String activity = "beast_mode";

    public String mWaveColor = "#FF07ADFA";
    public String mTextColor = "#4b4949";

    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        waveProgressbar = findViewById(R.id.waveProgressbar);
        add = findViewById(R.id.add);
        cup = findViewById(R.id.cup);
        juice = findViewById(R.id.juice);
        glass = findViewById(R.id.glass);
        shake = findViewById(R.id.shake);
        bottle = findViewById(R.id.bottle);
        mug = findViewById(R.id.mug);
        jug = findViewById(R.id.jug);

        int water = calculate_intake();

        final String litre = String.valueOf(water);

        waveProgressbar.setCurrent( current,litre + "ml"); // 77, "788M/1024M"
        waveProgressbar.setMaxProgress(water);
        waveProgressbar.setText(mTextColor,50);//"#FFFF00", 41
        waveProgressbar.setWaveColor(mWaveColor); //"#5b9ef4"

        waveProgressbar.setWave(30,200);
        waveProgressbar.setmWaveSpeed(10);//The larger the value, the slower the vibration

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View view1 = LayoutInflater.from(Water.this).inflate(R.layout.input_water,null);

                EditText add = view1.findViewById(R.id.add_water);

                AlertDialog.Builder builder = new AlertDialog.Builder(Water.this);
                builder.setMessage("Input Water Intake");
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

                builder.setView(view1);

                final AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        cup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + 100;
                waveProgressbar.setCurrent(current,litre +"ml");
            }
        });

        juice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + 200;
                waveProgressbar.setCurrent(current,litre +"ml");
            }
        });
        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + 300;
                waveProgressbar.setCurrent(current,litre +"ml");
            }
        });
        shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + 400;
                waveProgressbar.setCurrent(current,litre +"ml");
            }
        });
        bottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + 500;
                waveProgressbar.setCurrent(current,litre +"ml");
            }
        });
        mug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + 700;
                waveProgressbar.setCurrent(current,litre +"ml");
            }
        });
        jug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + 1000;
                waveProgressbar.setCurrent(current,litre +"ml");
            }
        });



    }

    public int calculate_intake()
    {
        int water =0;

        if(age <= 30)
        {
            water = weight * 40;

        }
        if(age>=31 && age <=55)
        {
             water = weight *35;
        }

        if (age >= 56)
        {
             water = weight*30;
        }

        if(gender.equals("female"))
        {
            water = water-500;
        }

        if(activity.equals("beast_mode"))
        {
            water = water + 700;
        }
        if(activity.equals("offen"))
        {
            water = water + 200;
        }
        if(activity.equals("active"))
        {
            water = water + 350;
        }
        return  water;
    }

    public void Back(View view)
    {
        finish();
    }
}
