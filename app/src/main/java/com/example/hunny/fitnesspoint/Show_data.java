package com.example.hunny.fitnesspoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hunny.fitnesspoint.RecycleView.Food;
import com.example.hunny.fitnesspoint.RecycleView.FoodsAdapter;
import com.example.hunny.fitnesspoint.RecycleViewFood.Food_new;
import com.example.hunny.fitnesspoint.RecycleViewFood.FoodsAdapter_new;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Show_data extends AppCompatActivity {

    private List<Food_new> foodList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FoodsAdapter_new mAdapter;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        s = getIntent().getStringExtra("Indetifier");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new FoodsAdapter_new(foodList , Show_data.this);

        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration_new(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);


        prepareFoodData();
    }

    private void prepareFoodData() {

        final Calendar cal = Calendar.getInstance();

        final int  year_x = cal.get(Calendar.YEAR);
        int  month_x = cal.get(Calendar.MONTH);
        int  day_x = cal.get(Calendar.DAY_OF_MONTH);

        final String curent_date = day_x+"_"+(month_x+1)+"_"+year_x;

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth authenticate = FirebaseAuth.getInstance();

        database.getReference().child("consumed_food").child(authenticate.getCurrentUser().getEmail().replace(".",""))
                .child(curent_date).child(s).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for ( DataSnapshot snap : dataSnapshot.getChildren())
                {
                    Food_new data = snap.getValue(Food_new.class);
                    data.food_id = snap.getKey();
                    foodList.add(data);
                }

                mAdapter.notifyDataSetChanged();

               if (foodList.size() == 0)
               {
                   TextView caps ,caps2;
                   ImageView add;

                   caps = findViewById(R.id.caps);
                   caps2 = findViewById(R.id.caps_2);
                   add = findViewById(R.id.add);

                   caps.setVisibility(View.VISIBLE);
                   caps2.setVisibility(View.VISIBLE);
                   add.setVisibility(View.VISIBLE);
               }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void Back(View view) {
        finish();
    }

    public void search_food(View view)
    {
        Intent i =  new Intent(Show_data.this, Search_Food_Activity.class);
        startActivity(i);
    }
}
