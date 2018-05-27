package com.example.hunny.fitnesspoint;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunny.fitnesspoint.dataModel.SignUpData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.fanrunqi.waveprogress.WaveProgressView;
import me.itangqi.waveloadingview.WaveLoadingView;

public class Water extends AppCompatActivity {

    WaveProgressView waveProgressbar;

    ImageView add,cup,juice,glass,shake,bottle,mug,jug;

    int weight ;

    int age ,water;

    String gender ;

    String activity ;

    TextView consumed;

    public String mWaveColor = "#FF07ADFA";
    public String mTextColor = "#4b4949";

    int current = 0;

    String litre ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        consumed = findViewById(R.id.consumed);

        final ProgressDialog pd = new ProgressDialog(Water.this);

        pd.setTitle("Fetching..");
        pd.setMessage("Please wait ..");
        pd.show();

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace(".","");

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("users").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pd.hide();

                SignUpData data = dataSnapshot.getValue(SignUpData.class);

                gender = data.gender;

                age = Integer.parseInt(data.dob);

                weight = Integer.parseInt(data.weight);

                activity= data.activity;

                 water = calculate_intake();

                litre = String.valueOf(water);

                FirebaseDatabase databases = FirebaseDatabase.getInstance();

                databases.getReference().child("Goals").child(auth.getCurrentUser().getEmail().replace(".",""))
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                SignUpData data = dataSnapshot.getValue(SignUpData.class);

                                activity= data.activity;
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                waveProgressbar.setCurrent( current,litre + "ml"); // 77, "788M/1024M"
                waveProgressbar.setMaxProgress(water);
                waveProgressbar.setText(mTextColor,50);//"#FFFF00", 41
                waveProgressbar.setWaveColor(mWaveColor); //"#5b9ef4"

                waveProgressbar.setWave(30,200);
                waveProgressbar.setmWaveSpeed(10);//The larger the value, the slower the vibration

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        SharedPreferences sp = getSharedPreferences("app_data" , MODE_PRIVATE);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,0);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String curentDate = df.format(c.getTime());

        if(!sp.getString("date" , "").equals(curentDate))
        {
            SharedPreferences.Editor shared_preference = getSharedPreferences("app_data" , MODE_PRIVATE).edit();

            shared_preference.putInt("water_consumption" , 0);
            shared_preference.putString("date" , curentDate );

            shared_preference.commit();
        }
        current = sp.getInt("water_consumption" , 0);
        consumed.setText(String.valueOf(sp.getInt("water_consumption",0)));

        waveProgressbar = findViewById(R.id.waveProgressbar);
        add = findViewById(R.id.add);
        cup = findViewById(R.id.cup);
        juice = findViewById(R.id.juice);
        glass = findViewById(R.id.glass);
        shake = findViewById(R.id.shake);
        bottle = findViewById(R.id.bottle);
        mug = findViewById(R.id.mug);
        jug = findViewById(R.id.jug);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View view1 = LayoutInflater.from(Water.this).inflate(R.layout.input_water,null);

                final EditText add = view1.findViewById(R.id.add_water);

                AlertDialog.Builder builder = new AlertDialog.Builder(Water.this);
                builder.setMessage("Input Water Intake");
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                          int text = Integer.parseInt(add.getText().toString());
                          add_to_current(text);
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

                add_to_current(100);
            }
        });

        juice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_to_current(200);
            }
        });
        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_to_current(300);
            }
        });
        shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_to_current(400);

            }
        });
        bottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_to_current(500);

            }
        });
        mug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_to_current(700);

            }
        });
        jug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_to_current(1000);
            }
        });



    }


    private void add_to_current(int quantity)
    {
        if (current>= Integer.parseInt(litre))
        {
            Toast.makeText(Water.this,"You have reached your todays threshold",Toast.LENGTH_SHORT).show();
            return;
        }
        current = current + quantity;

        consumed.setText(String.valueOf(current));

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,0);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String curentDate = df.format(c.getTime());

        SharedPreferences.Editor shared_preference = getSharedPreferences("app_data" , MODE_PRIVATE).edit();

        shared_preference.putInt("water_consumption" , current);
        shared_preference.putString("date" , curentDate );

        shared_preference.commit();

        waveProgressbar.setCurrent(current,litre +" ml");

        Intent activate = new Intent(this, AlarmReceiver.class);

        AlarmManager alarms ;
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, activate, 0);
        alarms = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarms.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()+7200000, alarmIntent);
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

        if(gender.equals("Female"))
        {
            water = water-500;
        }

        if(activity.equals("Beast Mode"))
        {
            water = water + 700;
        }
        if(activity.equals("Desk job"))
        {
            water = water + 200;
        }
        if(activity.equals("Active"))
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
