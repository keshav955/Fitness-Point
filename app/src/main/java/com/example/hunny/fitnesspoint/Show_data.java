package com.example.hunny.fitnesspoint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hunny.fitnesspoint.RecycleView.Food;
import com.example.hunny.fitnesspoint.RecycleView.FoodsAdapter;
import com.example.hunny.fitnesspoint.RecycleViewFood.Food_new;
import com.example.hunny.fitnesspoint.RecycleViewFood.FoodsAdapter_new;

import java.util.ArrayList;
import java.util.List;

public class Show_data extends AppCompatActivity {

    private List<Food_new> foodList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FoodsAdapter_new mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

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

        Food_new food = new Food_new("Mad Max: Fury Road", "Action & Adventure", 2015,1,2,3);
        foodList.add(food);
    }

}
