package com.example.hunny.fitnesspoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hunny.fitnesspoint.RecycleView.Food;
import com.example.hunny.fitnesspoint.RecycleView.FoodsAdapter;
import com.example.hunny.fitnesspoint.RecycleView.RecyclerTouchListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.client.android.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

public class Search_Food_Activity extends AppCompatActivity {

    String TAG;

    private List<Food> foodList = new ArrayList<>();

    private List<Food> searched_foodlist = new ArrayList<>();

    private RecyclerView recyclerView;
    private FoodsAdapter mAdapter;

    private SearchView search_bar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);

        searched_foodlist = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new FoodsAdapter(searched_foodlist);

        recyclerView.setHasFixedSize(true);

        search_bar = findViewById(R.id.search_bar);

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                filter_data(s);

                return false;
            }
        });

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
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Food food = foodList.get(position);
                Toast.makeText(getApplicationContext(), food.name + " is selected!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Search_Food_Activity.this,Food_Cell.class);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareFoodData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.d(TAG, "contents: " + contents);
                Toast.makeText(Search_Food_Activity.this, "contents: " + contents, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "RESULT_CANCELED");
                Toast.makeText(Search_Food_Activity.this, " RESULT_CANCELED ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void prepareFoodData() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for ( DataSnapshot snap : dataSnapshot.getChildren())
                {
                    Food data = snap.getValue(Food.class);
                    foodList.add(data);
                }

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    private void filter_data(String s)
    {
        searched_foodlist.clear();

        for(Food food : foodList)
        {
            if(food.name.toLowerCase().contains(s.toLowerCase()))
            {
                searched_foodlist.add(food);
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    public void Back(View view)
    {
        finish();
    }

    public void barcode_scanner(View view)
    {
        Intent intent = new Intent(getApplicationContext(),CaptureActivity.class);
        intent.setAction("com.google.zxing.client.android.SCAN");
        intent.putExtra("SAVE_HISTORY", true);
        startActivityForResult(intent, 0);
    }
}
