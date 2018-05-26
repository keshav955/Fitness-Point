package com.example.hunny.fitnesspoint;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import me.itangqi.waveloadingview.WaveLoadingView;

public class Main_layout extends AppCompatActivity {

    public static Main_layout main_layout;

    SharedPreferences sp;

    LinearLayout home, food_list, water, body_mass, scan_barcode;
    String TAG,u_activity,u_goal,u_gender;

    int u_weight, u_age ,protein_intake = 0,crabs_intake = 0,fats_intake = 0;

    TextView protein_rem , crabs_rem , fats_rem;

    TextView breakfat_consumed,breakfast_protein,breakfast_crabs,breakfast_fats,
             morning_consumed,morning_protein,morning_crabs,morning_fats,
             lunch_consumed,lunch_protein,lunch_crabs,lunch_fats,
             evening_consumed,evening_protein,evening_crabs,evening_fats,
             dinner_consumed , dinner_protein,dinner_crabs,dinner_fats ;

    int rem_pro = 0,rem_car = 0,rem_fat =0;

    RelativeLayout lunch_parent, evening_parent, dinner_parent;
    ImageView visible;

    Animation show_animation, hide_animation, show_transtion, hide_transtion,
              show_weight,hide_weight,show_item,hide_item;

    View dark_view;

    public static ProgressBar protein_bar , crabs_bar,fats_bar;

    CircleImageView profile;

    FloatingActionButton button, food, item, weight;

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

        breakfat_consumed = findViewById(R.id.consumed);
        breakfast_protein = findViewById(R.id.breakfast_protein);
        breakfast_crabs   = findViewById(R.id.breakfast_crabs);
        breakfast_fats    = findViewById(R.id.breakfast_fats);
        morning_consumed  = findViewById(R.id.morning_consumed);
        morning_protein   = findViewById(R.id.morning_protein);
        morning_crabs = findViewById(R.id.morning_crabs);
        morning_fats = findViewById(R.id.morning_fats);
        lunch_consumed = findViewById(R.id.lunch_consumed);
        lunch_protein = findViewById(R.id.lunch_protein);
        lunch_crabs = findViewById(R.id.lunch_crabs);
        lunch_fats = findViewById(R.id.lunch_fats);
        evening_consumed = findViewById(R.id.evening_consumed);
        evening_protein = findViewById(R.id.evening_protein);
        evening_crabs = findViewById(R.id.evening_crabs);
        evening_fats = findViewById(R.id.evening_fats);
        dinner_consumed = findViewById(R.id.dinner_consumed);
        dinner_protein = findViewById(R.id.dinner_protein);
        dinner_crabs = findViewById(R.id.dinner_crabs);
        dinner_fats = findViewById(R.id.dinner_fats);

        main_layout = this;

        final ProgressDialog pd = new ProgressDialog(Main_layout.this);

        pd.setTitle("Fetching..");
        pd.setMessage("Please wait ..");
        pd.show();


        protein_bar = findViewById(R.id.protein_bar);
        crabs_bar = findViewById(R.id.crabs_bar);
        fats_bar = findViewById(R.id.fats_bar);

         sp = getSharedPreferences("app_data" , MODE_PRIVATE);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,0);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String curentDate = df.format(c.getTime());

        if(!sp.getString("date" , "").equals(curentDate))
        {
            SharedPreferences.Editor shared_preference = getSharedPreferences("app_data" , MODE_PRIVATE).edit();

            shared_preference.putString("date" , curentDate );
            shared_preference.putInt("Caloric_intake" ,0);
            shared_preference.putString("calorie" , "0" );
            shared_preference.putString("breakfat_consumed" ,"0 Kcal");
            shared_preference.putString("breakfast_protein" , "0" );
            shared_preference.putString("breakfast_crabs" , "0" );
            shared_preference.putString("breakfast_fats" , "0" );
            shared_preference.putString("morning_consumed" , "0 Kcal");
            shared_preference.putString("morning_protein" , "0");
            shared_preference.putString("morning_crabs" , "0" );
            shared_preference.putString("morning_fats" , "0");
            shared_preference.putString("lunch_consumed" , "0 Kcal");
            shared_preference.putString("lunch_protein" , "0" );
            shared_preference.putString("lunch_crabs" , "0" );
            shared_preference.putString("lunch_fats" , "0" );
            shared_preference.putString("evening_consumed" , "0 Kcal");
            shared_preference.putString("evening_protein" , "0" );
            shared_preference.putString("evening_crabs" , "0");
            shared_preference.putString("evening_fats" , "0" );
            shared_preference.putString("dinner_consumed" , "0 Kcal");
            shared_preference.putString("dinner_protein" , "0" );
            shared_preference.putString("dinner_crabs" , "0" );
            shared_preference.putString("dinner_fats" , "0" );
            shared_preference.putInt("protein_bar",0);
            shared_preference.putInt("crabs_bar",0);
            shared_preference.putInt("fats_bar",0);
            shared_preference.putInt("rem_pro",-1);
            shared_preference.putInt("rem_car",-1);
            shared_preference.putInt("rem_fat",-1);

            shared_preference.apply();
        }

         kcal = String.valueOf(sp.getInt("Caloric_intake" , 0));

             rem_pro = sp.getInt("rem_pro",-1);
             rem_car = sp.getInt("rem_car",-1);
             rem_fat = sp.getInt("rem_fat",-1);

        breakfat_consumed.setText( String.valueOf(sp.getString("breakfat_consumed", "0")));
        breakfast_protein.setText(String.valueOf(sp.getString("breakfast_protein","0")));
        breakfast_crabs.setText(String.valueOf(sp.getString("breakfast_crabs","0")));
        breakfast_fats.setText(String.valueOf(sp.getString("breakfast_fats","0")));

        morning_consumed.setText(String.valueOf(sp.getString("morning_consumed","0")));
        morning_protein.setText(String.valueOf(sp.getString("morning_protein","0")));
        morning_crabs.setText(String.valueOf(sp.getString("morning_crabs","0")));
        morning_fats.setText(String.valueOf(sp.getString("morning_fats","0")));

        lunch_consumed.setText(String.valueOf(sp.getString("lunch_consumed","0")));
        lunch_protein.setText(String.valueOf(sp.getString("lunch_protein","0")));
        lunch_crabs.setText(String.valueOf(sp.getString("lunch_crabs","0")));
        lunch_fats.setText(String.valueOf(sp.getString("lunch_fats","0")));

        evening_consumed.setText(String.valueOf(sp.getString("evening_consumed","0")));
        evening_protein.setText(String.valueOf(sp.getString("evening_protein","0")));
        evening_crabs.setText(String.valueOf(sp.getString("evening_crabs","0")));
        evening_fats.setText(String.valueOf(sp.getString("evening_fats","0")));

        dinner_consumed.setText(String.valueOf(sp.getString("dinner_consumed","0")));
        dinner_protein.setText(String.valueOf(sp.getString("dinner_protein","0")));
        dinner_crabs.setText(String.valueOf(sp.getString("dinner_crabs","0")));
        dinner_fats.setText(String.valueOf(sp.getString("dinner_fats","0"))) ;

        int pro_prog =  sp.getInt("protein_bar",0);
        int car_prog = sp.getInt("crabs_bar",0);
        int fat_prog = sp.getInt("fats_bar",0);

        protein_bar.setProgress(pro_prog);
        crabs_bar.setProgress(car_prog);
        fats_bar.setProgress(fat_prog);

        protein_rem = findViewById(R.id.protein_percent);
        crabs_rem = findViewById(R.id.crabs_percent);
        fats_rem = findViewById(R.id.fats_percent);

        protein_rem.setText(String.valueOf(rem_pro + "g left"));
        crabs_rem.setText(String.valueOf(rem_car +"g left"));
        fats_rem.setText(String.valueOf(rem_fat + "g left"));

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

                if (getIntent().getStringExtra("result") == null) {

                    AppConfig.caloric_intake  = calculate_caloric_intake();
                }
                Wave();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

                i.putExtra("calorie",calculate_caloric_intake());
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

        RelativeLayout breakfast_parent,morning_parent,lunch_parent,evening_parent,dinner_parent;

        breakfast_parent = findViewById(R.id.breakfast_Parent);
        morning_parent   = findViewById(R.id.morning_snack_Parent);
        lunch_parent = findViewById(R.id.lunch_Parent);
        evening_parent   = findViewById(R.id.evening_Parent);
        dinner_parent = findViewById(R.id.dinner_Parent);

        breakfast_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String consumed = "breakfast";
                Show_data(consumed);
            }
        });
        morning_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String consumed = "morning";
                Show_data(consumed);
            }
        });
        lunch_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String consumed = "lunch";
                Show_data(consumed);
            }
        });
        evening_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String consumed = "evening";
                Show_data(consumed);
            }
        });
        dinner_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String consumed = "dinner";
                Show_data(consumed);
            }
        });
    }

    public void search_food(View view) {
        Intent i = new Intent(Main_layout.this, Search_Food_Activity.class);
     //   i.putExtra("caloric_intake",calculate_caloric_intake());
        startActivity(i);
        finish();

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
        String progress;
        float f_kcal;
            progress  = kcal;
            f_kcal = Float.parseFloat(kcal);

        float kcal_consumed = 0;

        if (AppConfig.caloric_intake == 0)
        {
        }
        else {
             kcal_consumed = (f_kcal / AppConfig.caloric_intake) * 100;
        }
       // String remaining = String.valueOf((cal)-kcal_consumed);
        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        //mWaveLoadingView.setTopTitle("Top Title");
        // mWaveLoadingView.setCenterTitleColor(Color.GRAY);
        mWaveLoadingView.setTopTitle("Consumed - "+ progress);
        mWaveLoadingView.setTopTitleColor(-1);
        mWaveLoadingView.setTopTitleSize(15);
        mWaveLoadingView.setBottomTitleSize(18);
        mWaveLoadingView.setCenterTitle("Total - "+String.valueOf(AppConfig.caloric_intake));
        mWaveLoadingView.setBottomTitle("");
        mWaveLoadingView.setProgressValue((int) kcal_consumed);
        mWaveLoadingView.setBorderWidth(6);
        mWaveLoadingView.setAmplitudeRatio(40);
        mWaveLoadingView.setTopTitleStrokeColor(Color.argb(0,0,0,0));
        mWaveLoadingView.setTopTitleStrokeWidth(0);
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
        protein_intake = (int) (u_weight * 0.8);
        crabs_intake = (int) (u_weight * 1.4);
        fats_intake = (int) ((u_weight * 40)/100);

        if(u_age <= 30)
        {
            calorie_intake = u_weight * 30;
            protein_intake = protein_intake + 20;
            crabs_intake   = crabs_intake + 80;
            fats_intake    = fats_intake + 10;
        }
        if(u_age>=31 && u_age <=40)
        {
            calorie_intake = u_weight *28;
            protein_intake = protein_intake + 10;
            crabs_intake   = crabs_intake + 25;
        }

        if(u_age>=41 && u_age <=55)
        {
            calorie_intake = u_weight *25;
        }

        if (u_age >= 56)
        {
            calorie_intake = u_weight *20;
            protein_intake = protein_intake - 20;
            crabs_intake   = crabs_intake -20;
            fats_intake    = fats_intake -10;
        }

        if(u_gender.equals("Female"))
        {
            calorie_intake = calorie_intake-500;
        }

        if(u_gender.equals("Male"))
        {
            calorie_intake = calorie_intake + 200;
            protein_intake = protein_intake + 20;
            crabs_intake   = crabs_intake + 50;
            fats_intake    = fats_intake + 10;
        }

        if(u_activity.equals("Beast Mode" ))
        {
            calorie_intake = calorie_intake + 700;
            protein_intake = protein_intake + u_weight;
            crabs_intake   = (int)(crabs_intake + (u_weight * 1.5));
            fats_intake    = (int)(fats_intake + (u_weight/3));
        }

        if(u_activity.equals("Desk job"))
        {
            calorie_intake = calorie_intake + 200;
            protein_intake = protein_intake + 10;
            crabs_intake   = crabs_intake + 10;
            fats_intake    = fats_intake + 10;
        }
        if(u_activity.equals("Active"))
        {
            calorie_intake = calorie_intake + 350;
            protein_intake = (int)(protein_intake + (u_weight/2));
            crabs_intake   = (int)(crabs_intake + (u_weight/3));
            fats_intake    =(int) (fats_intake + (u_weight/5));
        }
        if(u_goal.equals("LoseWeight"))
        {
            calorie_intake = calorie_intake - 600;
            protein_intake = (int)(protein_intake - (u_weight/2));
            crabs_intake   = (int)(crabs_intake - (u_weight/3));
            fats_intake    =(int) (fats_intake - (u_weight/5));
        }
        if(u_goal.equals("MaintainWeight"))
        {
            calorie_intake = calorie_intake + 100;
        }
        if(u_goal.equals("GainWeight"))
        {
            calorie_intake = calorie_intake + 600;
            protein_intake = (int)(protein_intake + (u_weight/1.8));
            crabs_intake   = (int)(crabs_intake + (u_weight * 1.6));
            fats_intake    =(int) (fats_intake + (u_weight/3));
        }
        if(u_goal.equals("LeanMuscleMass"))
        {
            calorie_intake = calorie_intake + 300;
            protein_intake = (int)(protein_intake + (u_weight/2));
            fats_intake    =(int) (fats_intake  + 5);
        }

        protein_bar.setMax(protein_intake);
        crabs_bar.setMax(crabs_intake);
        fats_bar.setMax(fats_intake);

        if(rem_pro == -1)
        {
            rem_pro = protein_intake;
            rem_car = crabs_intake;
            rem_fat = fats_intake;
        }

        calorie_intake = (protein_intake * 4) + (crabs_intake * 4)+(fats_intake * 9);

        protein_rem.setText(String.valueOf(protein_intake) + "g left");
        crabs_rem.setText(String.valueOf(crabs_intake) + "g left");
        fats_rem.setText(String.valueOf(fats_intake) + "g left");

        return  calorie_intake;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().getStringExtra("nameKey") == null) {
            return;
        }

        if (getIntent().getStringExtra("result").equals("Ok")) {

            food_name = getIntent().getStringExtra("nameKey");
            serving = getIntent().getStringExtra("servingKey");
            calorie = getIntent().getStringExtra("calorieKey");
            protein = getIntent().getStringExtra("proteinKey");
            crabs = getIntent().getStringExtra("crabKey");
            fats = getIntent().getStringExtra("fatsKey");
            String identifer = getIntent().getStringExtra("indentify");

            float protein_fl, crabs_fl, fats_fl;

            protein_fl = Float.parseFloat(protein);
            crabs_fl = Float.parseFloat(crabs);
            fats_fl = Float.parseFloat(fats);

            int i_protein = (int) protein_fl;
            int i_crabs = (int) crabs_fl;
            int i_fats = (int) fats_fl;

            protein_bar.incrementProgressBy(i_protein);
            crabs_bar.incrementProgressBy(i_crabs);
            fats_bar.incrementProgressBy(i_fats);

            rem_pro = rem_pro - i_protein;
            rem_car = rem_car - i_crabs;
            rem_fat = rem_fat - i_fats;

            protein_rem.setText(String.format("%sg  left", String.valueOf(rem_pro)));
            crabs_rem.setText(String.format("%sg  left", String.valueOf(rem_car)));
            fats_rem.setText(String.format("%sg  left", String.valueOf(rem_fat)));

            float cal_protein, cal_crabs, cal_fats;

            cal_protein = 4 * protein_fl;
            cal_crabs = 4 * crabs_fl;
            cal_fats = 9 * fats_fl;

            f_kcal = Float.parseFloat(calorie);

            kcal = String.valueOf( Float.parseFloat(kcal) + f_kcal );
            Wave();

            if (identifer.equals("breakfast")) {
                int total_consumed = (int)(f_kcal) + Integer.parseInt(breakfat_consumed.getText().toString().replace("Kcal", "").trim());
                int total_protein = i_protein + Integer.parseInt(breakfast_protein.getText().toString());
                int total_crabs = i_crabs + Integer.parseInt( breakfast_crabs.getText().toString());
                int total_fats = i_fats + Integer.parseInt(breakfast_fats.getText().toString());

                breakfat_consumed.setText(String.valueOf(total_consumed) + " Kcal");
                breakfast_protein.setText(String.valueOf(total_protein));
                breakfast_crabs.setText(String.valueOf(total_crabs));
                breakfast_fats.setText(String.valueOf(total_fats));
            }
            if (identifer.equals("morning")) {
                int total_consumed = (int)(f_kcal) + Integer.parseInt(morning_consumed.getText().toString().replace("Kcal", "").trim());
                int total_protein = i_protein + Integer.parseInt(morning_protein.getText().toString());
                int total_crabs = i_crabs + Integer.parseInt( morning_crabs.getText().toString());
                int total_fats = i_fats + Integer.parseInt(morning_fats.getText().toString());

                morning_consumed.setText(String.valueOf(total_consumed) + " Kcal");
                morning_protein.setText(String.valueOf(total_protein));
                morning_crabs.setText(String.valueOf(total_crabs));
                morning_fats.setText(String.valueOf(total_fats));

            }
            if (identifer.equals("lunch")) {
                int total_consumed = (int)(f_kcal) + Integer.parseInt(lunch_consumed.getText().toString().replace("Kcal", "").trim());
                int total_protein = i_protein + Integer.parseInt(lunch_protein.getText().toString());
                int total_crabs = i_crabs + Integer.parseInt(lunch_crabs.getText().toString());
                int total_fats = i_fats + Integer.parseInt(lunch_fats.getText().toString());

                lunch_consumed.setText(String.valueOf(total_consumed) + " Kcal");
                lunch_protein.setText(String.valueOf(total_protein));
                lunch_crabs.setText(String.valueOf(total_crabs));
                lunch_fats.setText(String.valueOf(total_fats));
            }
            if (identifer.equals("evening")) {
                int total_consumed = (int)(f_kcal) + Integer.parseInt(lunch_consumed.getText().toString().replace("Kcal", "").trim());
                int total_protein = i_protein + Integer.parseInt(lunch_protein.getText().toString());
                int total_crabs = i_crabs + Integer.parseInt(lunch_crabs.getText().toString());
                int total_fats = i_fats + Integer.parseInt(lunch_fats.getText().toString());

                evening_consumed.setText(String.valueOf(total_consumed) + " Kcal");
                evening_protein.setText(String.valueOf(total_protein));
                evening_crabs.setText(String.valueOf(total_crabs));
                evening_fats.setText(String.valueOf(total_fats));

            }
            if (identifer.equals("dinner")) {

                int total_consumed = (int)(f_kcal) + Integer.parseInt(dinner_consumed.getText().toString().replace("Kcal", "").trim());
                int total_protein = i_protein + Integer.parseInt(dinner_protein.getText().toString());
                int total_crabs = i_crabs + Integer.parseInt(dinner_crabs.getText().toString());
                int total_fats = i_fats + Integer.parseInt(dinner_fats.getText().toString());

                dinner_consumed.setText(String.valueOf(total_consumed + " Kcal"));
                dinner_protein.setText(String.valueOf(total_protein));
                dinner_crabs.setText(String.valueOf(total_crabs));
                dinner_fats.setText(String.valueOf(total_fats));
            }

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE,0);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

            Intent activate = new Intent(this, AlarmReceiver1.class);

            AlarmManager alarms ;
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, activate, 0);
            alarms = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarms.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()+7200000, alarmIntent);
        }
    }

    public void open_profile(View view) {

        Intent i = new Intent(Main_layout.this, ProfileActivity.class);
        startActivity(i);
    }

    @Override
    protected void  onPause()
    {
        super.onPause();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,0);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String curentDate = df.format(c.getTime());

        SharedPreferences.Editor shared_preference = getSharedPreferences("app_data" , MODE_PRIVATE).edit();

        shared_preference.putString("date" , curentDate );
        shared_preference.putInt ("Caloric_intake" ,(int)(Float.parseFloat(kcal)));
        shared_preference.putString("breakfat_consumed" , breakfat_consumed.getText().toString() );
        shared_preference.putString("breakfast_protein" , breakfast_protein.getText().toString() );
        shared_preference.putString("breakfast_crabs" , breakfast_crabs.getText().toString() );
        shared_preference.putString("breakfast_fats" , breakfast_fats.getText().toString() );
        shared_preference.putString("morning_consumed" , morning_consumed.getText().toString() );
        shared_preference.putString("morning_protein" , morning_protein.getText().toString() );
        shared_preference.putString("morning_crabs" , morning_crabs.getText().toString() );
        shared_preference.putString("morning_fats" , morning_fats.getText().toString() );
        shared_preference.putString("lunch_consumed" , lunch_consumed.getText().toString() );
        shared_preference.putString("lunch_protein" , lunch_protein.getText().toString() );
        shared_preference.putString("lunch_crabs" , lunch_crabs.getText().toString() );
        shared_preference.putString("lunch_fats" , lunch_fats.getText().toString() );
        shared_preference.putString("evening_consumed" , evening_consumed.getText().toString() );
        shared_preference.putString("evening_protein" , evening_protein.getText().toString() );
        shared_preference.putString("evening_crabs" , evening_crabs.getText().toString() );
        shared_preference.putString("evening_fats" , evening_fats.getText().toString() );
        shared_preference.putString("dinner_consumed" , dinner_consumed.getText().toString() );
        shared_preference.putString("dinner_protein" , dinner_protein.getText().toString() );
        shared_preference.putString("dinner_crabs" , dinner_crabs.getText().toString() );
        shared_preference.putString("dinner_fats" , dinner_fats.getText().toString() );
        shared_preference.putInt("protein_bar", protein_bar.getProgress());
        shared_preference.putInt("crabs_bar",crabs_bar.getProgress());
        shared_preference.putInt("fats_bar",fats_bar.getProgress());
        shared_preference.putInt("rem_pro",Integer.parseInt(protein_rem.getText().toString().replace(" ","").replace("gleft","").trim()));
        shared_preference.putInt("rem_car",Integer.parseInt(crabs_rem.getText().toString().replace(" ","").replace("gleft","").trim()));
        shared_preference.putInt("rem_fat",Integer.parseInt(fats_rem.getText().toString().replace(" ","").replace("gleft","").trim()));

        shared_preference.apply();
    }

    public void Show_data(String s)
    {
        Intent i = new Intent(Main_layout.this,Show_data.class);
        i.putExtra("Indetifier",s);
        startActivity(i);
        finish();
    }
}