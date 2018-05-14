package com.example.hunny.fitnesspoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hunny.fitnesspoint.dataModel.SignUpData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.client.android.CaptureActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import me.itangqi.waveloadingview.WaveLoadingView;

public class Main_layout extends AppCompatActivity {

    LinearLayout home, food_list, water, body_mass, scan_barcode;
    String TAG,u_activity,u_goal,u_gender;

    int u_weight, u_age ,cal;

    RelativeLayout lunch_parent, evening_parent, dinner_parent;
    ImageView visible;

    Animation show_animation;
    Animation hide_animation;
    Animation show_transtion;
    Animation hide_transtion;
    Animation show_weight;
    Animation hide_weight;
    Animation show_item;
    Animation hide_item;
    View dark_view;

    CircleImageView profile;

    FloatingActionButton button;
    FloatingActionButton food;
    FloatingActionButton item;
    FloatingActionButton weight;

    DrawerLayout dl;

    WaveLoadingView mWaveLoadingView;

    public String food_name, serving, calorie, protein, crabs, fats;

    CircleImageView profile_draw;

    int count = 1;

    float f_kcal;
    String kcal = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        final ProgressDialog pd = new ProgressDialog(Main_layout.this);

        pd.setTitle("Fetching..");
        pd.setMessage("Please wait ..");
        pd.show();



        profile_draw = findViewById(R.id.profile);

        profile = findViewById(R.id.open_profile);

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace(".", "");

        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference();
        storageRef.child("images/" + email + ".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.with(Main_layout.this)
                                .load(uri)
                                .into(profile);
                        Picasso.with(Main_layout.this)
                                .load(uri)
                                .into(profile_draw);


                        // Got the download URL for 'users/me/profile.png'
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("users").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pd.hide();

                SignUpData data = dataSnapshot.getValue(SignUpData.class);

                u_weight = Integer.parseInt(data.weight);
                u_age  = Integer.parseInt(data.dob);
                u_activity = data.activity;
                u_goal = data.goal;
                u_gender = data.gender;

                cal = calculate_caloric_intake();
                Wave();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Wave();
        dl = findViewById(R.id.draw);
        button = findViewById(R.id.add_button);
        item = findViewById(R.id.item);
        food = findViewById(R.id.food);
        weight = findViewById(R.id.weight);
        dark_view = findViewById(R.id.dark_view);
        scan_barcode = findViewById(R.id.scan_barcode);
        body_mass = findViewById(R.id.body_mass);
        home = findViewById(R.id.home);
        food_list = findViewById(R.id.food_list);
        water = findViewById(R.id.water);
        lunch_parent = findViewById(R.id.lunch_Parent);
        evening_parent = findViewById(R.id.evening_Parent);
        dinner_parent = findViewById(R.id.dinner_Parent);
        visible = findViewById(R.id.visible_view);


        visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lunch_parent.setVisibility(View.VISIBLE);
                evening_parent.setVisibility(View.VISIBLE);
                dinner_parent.setVisibility(View.VISIBLE);
                visible.setVisibility(View.GONE);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_layout.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        home.setBackgroundColor(Color.argb(255, 175, 173, 173));

        body_mass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_layout.this, BMI.class);
                startActivity(i);
                //          home.setBackgroundColor(Color.argb(0,0,0,0));
                dl.closeDrawer(Gravity.LEFT);
            }
        });

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_layout.this, Water.class);
                startActivity(i);
                //        home.setBackgroundColor(Color.argb(0,0,0,0));
                dl.closeDrawer(Gravity.LEFT);
            }
        });

        food_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_layout.this, Search_Food_Activity.class);
                startActivity(i);
                //      home.setBackgroundColor(Color.argb(0,0,0,0));
                dl.closeDrawer(Gravity.LEFT);
            }
        });

        scan_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
                intent.setAction("com.google.zxing.client.android.SCAN");
                intent.putExtra("SAVE_HISTORY", true);
                startActivityForResult(intent, 0);
                home.setBackgroundColor(Color.argb(0, 0, 0, 0));
                dl.closeDrawer(Gravity.LEFT);
            }
        });

        show_animation = AnimationUtils.loadAnimation(Main_layout.this, R.anim.rotate);
        hide_animation = AnimationUtils.loadAnimation(Main_layout.this, R.anim.hide_rotate);

        show_transtion = AnimationUtils.loadAnimation(Main_layout.this, R.anim.translate);
        hide_transtion = AnimationUtils.loadAnimation(Main_layout.this, R.anim.hide_transition);

        show_weight = AnimationUtils.loadAnimation(Main_layout.this, R.anim.show_weight);
        hide_weight = AnimationUtils.loadAnimation(Main_layout.this, R.anim.hide_weight);

        show_item = AnimationUtils.loadAnimation(Main_layout.this, R.anim.show_item);
        hide_item = AnimationUtils.loadAnimation(Main_layout.this, R.anim.hide_item);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (food.getVisibility() == View.VISIBLE) {
                    food.setVisibility(View.GONE);
                    item.setVisibility(View.GONE);
                    weight.setVisibility(View.GONE);
                    button.startAnimation(hide_animation);
                    item.startAnimation(hide_item);
                    food.startAnimation(hide_transtion);
                    weight.startAnimation(hide_weight);
                    dark_view.setVisibility(View.GONE);
                    dark_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            food.setVisibility(View.GONE);
                            item.setVisibility(View.GONE);
                            weight.setVisibility(View.GONE);
                            button.startAnimation(hide_animation);
                            item.startAnimation(hide_item);
                            food.startAnimation(hide_transtion);
                            weight.startAnimation(hide_weight);
                            dark_view.setVisibility(View.GONE);
                        }
                    });
                } else {
                    food.setVisibility(View.VISIBLE);
                    item.setVisibility(View.VISIBLE);
                    weight.setVisibility(View.VISIBLE);
                    button.startAnimation(show_animation);
                    food.startAnimation(show_transtion);
                    item.startAnimation(show_item);
                    weight.startAnimation(show_weight);
                    dark_view.setVisibility(View.VISIBLE);
                    dark_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            food.setVisibility(View.GONE);
                            item.setVisibility(View.GONE);
                            weight.setVisibility(View.GONE);
                            button.startAnimation(hide_animation);
                            item.startAnimation(hide_item);
                            food.startAnimation(hide_transtion);
                            weight.startAnimation(hide_weight);
                            dark_view.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void search_food(View view) {
        Intent i = new Intent(Main_layout.this, Search_Food_Activity.class);
        startActivity(i);

        food.setVisibility(View.GONE);
        item.setVisibility(View.GONE);
        weight.setVisibility(View.GONE);
        button.startAnimation(hide_animation);
        item.startAnimation(hide_item);
        food.startAnimation(hide_transtion);
        weight.startAnimation(hide_weight);
        dark_view.setVisibility(View.GONE);
    }

    public void Wave() {

        String progress = kcal;
        float f_kcal = Float.parseFloat(kcal);

        float kcal_consumed =  f_kcal/20;

       // String remaining = String.valueOf((cal)-kcal_consumed);

        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        //mWaveLoadingView.setTopTitle("Top Title");
        // mWaveLoadingView.setCenterTitleColor(Color.GRAY);
        mWaveLoadingView.setTopTitle("Consumed - "+progress);
        mWaveLoadingView.setTopTitleColor(-1);
        mWaveLoadingView.setBottomTitleSize(18);
        mWaveLoadingView.setCenterTitle("Total - "+String.valueOf(cal));
        mWaveLoadingView.setBottomTitle("Remaining - ");
        mWaveLoadingView.setProgressValue((int) kcal_consumed);
        mWaveLoadingView.setBorderWidth(6);
        mWaveLoadingView.setAmplitudeRatio(40);
        mWaveLoadingView.setTopTitleStrokeColor(Color.LTGRAY);
        mWaveLoadingView.setTopTitleStrokeWidth(1);
        mWaveLoadingView.setAnimDuration(2000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

    }

    public void open_drawer(View view) {
        dl.openDrawer(Gravity.LEFT);
    }

    public void home(View view) {
        home.setBackgroundColor(Color.argb(255, 175, 173, 173));
        dl.closeDrawer(Gravity.LEFT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        home.setBackgroundColor(Color.argb(255, 175, 173, 173));
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.d(TAG, "contents: " + contents);
                Toast.makeText(Main_layout.this, "contents: " + contents, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "RESULT_CANCELED");
                Toast.makeText(Main_layout.this, " RESULT_CANCELED ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void open_water(View view) {

        Intent i = new Intent(Main_layout.this, Water.class);
        startActivity(i);

        food.setVisibility(View.GONE);
        item.setVisibility(View.GONE);
        weight.setVisibility(View.GONE);
        button.startAnimation(hide_animation);
        item.startAnimation(hide_item);
        food.startAnimation(hide_transtion);
        weight.startAnimation(hide_weight);
        dark_view.setVisibility(View.GONE);

    }

    public void open_bmi(View view) {

        Intent i = new Intent(Main_layout.this, BMI.class);
        startActivity(i);

        food.setVisibility(View.GONE);
        item.setVisibility(View.GONE);
        weight.setVisibility(View.GONE);
        button.startAnimation(hide_animation);
        item.startAnimation(hide_item);
        food.startAnimation(hide_transtion);
        weight.startAnimation(hide_weight);
        dark_view.setVisibility(View.GONE);
    }

    public int calculate_caloric_intake()
    {
        int calorie_intake =0;

        if(u_age <= 30)
        {
            calorie_intake = u_weight * 30;
        }
        if(u_age>=31 && u_age <=40)
        {
            calorie_intake = u_weight *28;
        }

        if(u_age>=41 && u_age <=55)
        {
            calorie_intake = u_weight *25;
        }

        if (u_age >= 56)
        {
            calorie_intake = u_weight *20;
        }

        if(u_gender.equals("Female"))
        {
            calorie_intake = calorie_intake-500;
        }

        if(u_activity.equals("Beast Mode" ))
        {
            calorie_intake = calorie_intake + 700;
        }
        if(u_activity.equals("Desk job"))
        {
            calorie_intake = calorie_intake + 200;
        }
        if(u_activity.equals("Active"))
        {
            calorie_intake = calorie_intake + 350;
        }
        if(u_goal.equals("LoseWeight"))
        {
            calorie_intake = calorie_intake + 600;
        }
        if(u_goal.equals("MaintainWeight"))
        {
            calorie_intake = calorie_intake + 100;
        }
        if(u_goal.equals("GainWeight"))
        {
            calorie_intake = calorie_intake + 600;
        }
        if(u_goal.equals("LeanMuscleMass"))
        {
            calorie_intake = calorie_intake + 300;
        }

        return  calorie_intake;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent().getStringExtra("nameKey") == null)
        {
            return;
        }

        food_name = getIntent().getStringExtra("nameKey");
        serving = getIntent().getStringExtra("servingKey");
        calorie = getIntent().getStringExtra("calorieKey");
        protein = getIntent().getStringExtra("proteinKey");
        crabs = getIntent().getStringExtra("crabKey");
        fats = getIntent().getStringExtra("fatsKey");
        float protein_fl , crabs_fl , fats_fl;

        protein_fl = Float.parseFloat(protein);
        crabs_fl = Float.parseFloat(crabs);
        fats_fl = Float.parseFloat(fats);

        float cal_protein,cal_crabs,cal_fats;

        cal_protein= 4 * protein_fl ;
        cal_crabs = 4 * crabs_fl;
        cal_fats = 9 * fats_fl;

        f_kcal = cal_protein + cal_fats + cal_crabs;

        kcal = String.valueOf(f_kcal);

        Wave();

    }

}