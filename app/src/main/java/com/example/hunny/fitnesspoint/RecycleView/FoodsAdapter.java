package com.example.hunny.fitnesspoint.RecycleView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hunny.fitnesspoint.R;

import java.util.List;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.MyViewHolder> {

    private List<Food> foodsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, serving, calorie;

        public MyViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name);
            serving = (TextView) view.findViewById(R.id.serving);
            calorie = (TextView) view.findViewById(R.id.calorie);
        }
    }


    public FoodsAdapter(List<Food> foodsList) {
        this.foodsList = foodsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Food food = foodsList.get(position);

        holder.name.setText(String.valueOf(food.name));

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
    }

    @Override
    public int getItemCount() {
        return foodsList.size();
    }
}
