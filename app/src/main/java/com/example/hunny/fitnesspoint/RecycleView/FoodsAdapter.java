package com.example.hunny.fitnesspoint.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunny.fitnesspoint.Food_Cell;
import com.example.hunny.fitnesspoint.R;
import com.example.hunny.fitnesspoint.Search_Food_Activity;

import java.util.List;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.MyViewHolder> {


    private List<Food> foodsList;

    private Context context ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, serving, calorie;
      //  public float protein,crabs,fats;

        public RelativeLayout food_cell ;

        public MyViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name);
            serving = (TextView) view.findViewById(R.id.serving);
            calorie = (TextView) view.findViewById(R.id.calorie);
            food_cell = view.findViewById(R.id.food_cell);
        }
    }


    public FoodsAdapter(List<Food> foodsList , Context context) {
        this.foodsList = foodsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Food food = foodsList.get(position);

        holder.name.setText(String.valueOf(food.name));
/*
        holder.protein = food.protein;
        holder.crabs = food.crabs;
        holder.fats = food.fats;*/

        if(String.valueOf(food.serving).equals("qty"))
        {
            holder.serving.setText(String.valueOf(food.serving+" - 1"));
        }
        if(String.valueOf(food.serving).equals("ml"))
        {
            holder.serving.setText(String.valueOf("100" +food.serving));
        }
        if(String.valueOf(food.serving).equals("gm"))
        {
            holder.serving.setText(String.valueOf("100" +food.serving));
        }

        holder.calorie.setText(String.valueOf("Calorie -"+food.calorie));


        holder.food_cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent i = new Intent(context,Food_Cell.class);

                i.putExtra("calorieKey",String.valueOf(food.calorie));
                i.putExtra("proteinKey",String.valueOf(food.protein));
                i.putExtra("crabKey",String.valueOf(food.carbs));
                i.putExtra("fatsKey",String.valueOf(food.fats));
                i.putExtra("nameKey",String.valueOf(food.name));
                i.putExtra("servingKey",String.valueOf(food.serving));

                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return foodsList.size();
    }
}
