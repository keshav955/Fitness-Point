package com.example.hunny.fitnesspoint.RecycleViewFood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hunny.fitnesspoint.Food_Cell;
import com.example.hunny.fitnesspoint.Main_layout;
import com.example.hunny.fitnesspoint.R;
import com.example.hunny.fitnesspoint.RecycleView.FoodsAdapter;
import com.example.hunny.fitnesspoint.Show_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FoodsAdapter_new extends RecyclerView.Adapter<FoodsAdapter_new.MyViewHolder> {


    private List<Food_new> foodsList;

    private Context context ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, serving, calorie;
        ImageView delete;
      //  public float protein,crabs,fats;

        public RelativeLayout food_cell ;

        public MyViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name);
            serving = (TextView) view.findViewById(R.id.serving);
            calorie = (TextView) view.findViewById(R.id.calorie);
            food_cell = view.findViewById(R.id.food_cell);
            delete = view.findViewById(R.id.delete);

        }
    }


    public FoodsAdapter_new(List<Food_new> foodsList , Context context) {
        this.foodsList = foodsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_row_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Food_new food = foodsList.get(position);

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


            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal = Calendar.getInstance();

                final int  year_x = cal.get(Calendar.YEAR);
                int  month_x = cal.get(Calendar.MONTH);
                int  day_x = cal.get(Calendar.DAY_OF_MONTH);

                final String curent_date = day_x+"_"+(month_x+1)+"_"+year_x;


                FirebaseDatabase database = FirebaseDatabase.getInstance();

                FirebaseAuth authenticate = FirebaseAuth.getInstance();

                database.getReference().child("consumed_food").child(authenticate.getCurrentUser().getEmail().replace(".",""))
                        .child(curent_date).child(Show_data.s).child(food.food_id).setValue(null);

                SharedPreferences shared_preference_read = context.getSharedPreferences("app_data" , MODE_PRIVATE);

                SharedPreferences.Editor shared_preference = context.getSharedPreferences("app_data" , MODE_PRIVATE).edit();

                if(Show_data.s.equalsIgnoreCase("breakfast")) {

                    String new_kcal = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("breakfat_consumed", "").replace("Kcal","").trim()) - food.calorie));
                    String new_protien = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("breakfast_protein", "")) - food.protein));
                    String new_crabs = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("breakfast_crabs", "")) - food.carbs));
                    String new_fats = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("breakfast_fats", "")) - food.fats));

                    shared_preference.putString("breakfat_consumed" , new_kcal + " Kcal" );
                    shared_preference.putString("breakfast_protein" , new_protien );
                    shared_preference.putString("breakfast_crabs" , new_crabs);
                    shared_preference.putString("breakfast_fats" , new_fats);

                }
                else if(Show_data.s.equalsIgnoreCase("morning")) {

                    String new_kcal = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("morning_consumed", "").replace("Kcal","").trim()) - food.calorie));
                    String new_protien = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("morning_protein", "")) - food.protein));
                    String new_crabs = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("morning_crabs", "")) - food.carbs));
                    String new_fats = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("morning_fats", "")) - food.fats));

                    shared_preference.putString("morning_consumed" , new_kcal+ " Kcal");
                    shared_preference.putString("morning_protein" , new_protien );
                    shared_preference.putString("morning_crabs" , new_crabs);
                    shared_preference.putString("morning_fats" , new_fats );

                }
                else if(Show_data.s.equalsIgnoreCase("lunch")) {

                    String new_kcal = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("lunch_consumed", "").replace("Kcal","").trim()) - food.calorie));
                    String new_protien = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("lunch_protein", "")) - food.protein));
                    String new_crabs = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("lunch_crabs", "")) - food.carbs));
                    String new_fats = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("lunch_fats", "")) - food.fats));

                    shared_preference.putString("lunch_consumed" , new_kcal + " Kcal");
                    shared_preference.putString("lunch_protein" , new_protien );
                    shared_preference.putString("lunch_crabs" , new_crabs );
                    shared_preference.putString("lunch_fats" ,new_fats);

                }
                else if(Show_data.s.equalsIgnoreCase("evening")) {

                    String new_kcal = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("evening_consumed", "").replace("Kcal","").trim()) - food.calorie));
                    String new_protien = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("evening_protein", "")) - food.protein));
                    String new_crabs = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("evening_crabs", "")) - food.carbs));
                    String new_fats = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("evening_fats", "")) - food.fats));

                    shared_preference.putString("evening_consumed" , new_kcal+ " Kcal" );
                    shared_preference.putString("evening_protein" , new_protien );
                    shared_preference.putString("evening_crabs" , new_crabs );
                    shared_preference.putString("evening_fats" , new_fats );

                }
                else {
                    String new_kcal = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("dinner_consumed", "").replace("Kcal","").trim()) - food.calorie));
                    String new_protien = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("dinner_protein", "")) - food.protein));
                    String new_crabs = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("dinner_crabs", "")) - food.carbs));
                    String new_fats = String.valueOf((int)(Float.parseFloat(shared_preference_read.getString("dinner_fats", "")) - food.fats));

                    shared_preference.putString("dinner_consumed" , new_kcal + " Kcal" );
                    shared_preference.putString("dinner_protein" , new_protien );
                    shared_preference.putString("dinner_crabs" ,  new_crabs);
                    shared_preference.putString("dinner_fats" , new_fats );
                }

                shared_preference.putInt("Caloric_intake" , (shared_preference_read.getInt("Caloric_intake", 0) - (int)(food.calorie)));
                shared_preference.putInt("protein_bar", Main_layout.protein_bar.getProgress()- (int)(food.protein));
                shared_preference.putInt("crabs_bar",Main_layout.crabs_bar.getProgress()- (int)(food.carbs));
                shared_preference.putInt("fats_bar",Main_layout.fats_bar.getProgress()- (int)(food.fats));
                shared_preference.putInt("rem_pro",(int)(shared_preference_read.getInt("rem_pro", -1) + food.protein));
                shared_preference.putInt("rem_car",(int)(shared_preference_read.getInt("rem_car", -1) + food.carbs));
                shared_preference.putInt("rem_fat",(int)(shared_preference_read.getInt("rem_fat", -1) + food.fats));

                shared_preference.apply();

                foodsList.remove(position);

                FoodsAdapter_new.this.notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodsList.size();
    }
}
