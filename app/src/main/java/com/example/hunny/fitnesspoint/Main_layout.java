package com.example.hunny.fitnesspoint;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.client.android.CaptureActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import me.itangqi.waveloadingview.WaveLoadingView;

public class Main_layout extends AppCompatActivity {

    LinearLayout home,food_list,water,body_mass,scan_barcode;
    String TAG;

    RelativeLayout lunch_parent , evening_parent , dinner_parent;
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

    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        profile = findViewById(R.id.open_profile);


        final FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace(".","");

        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference();
        storageRef.child("images/"+email+".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.with(Main_layout.this)
                                .load(uri)
                                .into(profile);

                        // Got the download URL for 'users/me/profile.png'
                    }}).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
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
                Intent i = new Intent(Main_layout.this,ProfileActivity.class);
                startActivity(i);
            }
        });

        home.setBackgroundColor(Color.argb(255,175,173,173));

        body_mass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i =  new Intent(Main_layout.this,BMI.class);
                startActivity(i);
      //          home.setBackgroundColor(Color.argb(0,0,0,0));
                dl.closeDrawer(Gravity.LEFT);
            }
        });

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i =  new Intent(Main_layout.this,Water.class);
                startActivity(i);
        //        home.setBackgroundColor(Color.argb(0,0,0,0));
                dl.closeDrawer(Gravity.LEFT);
            }
        });

        food_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i =  new Intent(Main_layout.this,Search_Food_Activity.class);
                startActivity(i);
          //      home.setBackgroundColor(Color.argb(0,0,0,0));
                dl.closeDrawer(Gravity.LEFT);
            }
        });

        scan_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),CaptureActivity.class);
                intent.setAction("com.google.zxing.client.android.SCAN");
                intent.putExtra("SAVE_HISTORY", true);
                startActivityForResult(intent, 0);
                home.setBackgroundColor(Color.argb(0,0,0,0));
                dl.closeDrawer(Gravity.LEFT);
            }
        });

        show_animation = AnimationUtils.loadAnimation(Main_layout.this, R.anim.rotate);
        hide_animation = AnimationUtils.loadAnimation(Main_layout.this,R.anim.hide_rotate);

        show_transtion = AnimationUtils.loadAnimation(Main_layout.this, R.anim.translate);
        hide_transtion = AnimationUtils.loadAnimation(Main_layout.this,R.anim.hide_transition);

        show_weight = AnimationUtils.loadAnimation(Main_layout.this,R.anim.show_weight);
        hide_weight = AnimationUtils.loadAnimation(Main_layout.this,R.anim.hide_weight);

        show_item = AnimationUtils.loadAnimation(Main_layout.this,R.anim.show_item);
        hide_item = AnimationUtils.loadAnimation(Main_layout.this,R.anim.hide_item);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(food.getVisibility() == View.VISIBLE )
                {
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
                }
                else
                {
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

    public void search_food(View view)
    {
        Intent i = new Intent(Main_layout.this,Search_Food_Activity.class);
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

    public void Wave()
    {
        String progress = "50";
        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        //mWaveLoadingView.setTopTitle("Top Title");
       // mWaveLoadingView.setCenterTitleColor(Color.GRAY);
        mWaveLoadingView.setTopTitle(progress + " %");
        mWaveLoadingView.setTopTitleColor(-1);
        mWaveLoadingView.setBottomTitleSize(18);
        mWaveLoadingView.setProgressValue(Integer.valueOf(progress));
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

    public void open_drawer(View view)
    {
        dl.openDrawer(Gravity.LEFT);
    }

    public void home(View view)
    {
        home.setBackgroundColor(Color.argb(255,175,173,173));
        dl.closeDrawer(Gravity.LEFT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        home.setBackgroundColor(Color.argb(255,175,173,173));
        if (requestCode == 0) {
            if (resultCode == RESULT_OK)
            {
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.d(TAG, "contents: " + contents);
                Toast.makeText(Main_layout.this, "contents: " + contents, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_CANCELED)
            {
                Log.d(TAG, "RESULT_CANCELED");
                Toast.makeText(Main_layout.this, " RESULT_CANCELED ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void open_water(View view) {

        Intent i = new Intent(Main_layout.this,Water.class);
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

        Intent i = new Intent(Main_layout.this,BMI.class);
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
}