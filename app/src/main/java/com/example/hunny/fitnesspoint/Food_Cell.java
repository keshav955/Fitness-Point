package com.example.hunny.fitnesspoint;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunny.fitnesspoint.RecycleView.Food;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import at.grabner.circleprogress.CircleProgressView;

public class Food_Cell extends AppCompatActivity {

    CircleProgress protein_progress, crabs_progress, fats_progress;

    CircleProgressView cal_progress;

    public String food_name, serving, calorie, protein, crabs, fats;

    TextView name_txt, calorie_txt, protein_txt, crabs_txt, fats_txt;

    int ser_pro, ser_kcal, ser_fat, ser_carb;

    Spinner serving_size;
    FloatingActionButton add_food;
    Button breakfast, morning_snack, lunch, evening_snack, dinner, late_night;

    EditText serving_et;

    float protein_fl, crabs_fl, fats_fl;

    int kcal_per, cal_intake = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodcell);

        serving_et = findViewById(R.id.serving);

        cal_intake = Main_layout.main_layout.calculate_caloric_intake();

        cal_progress = findViewById(R.id.circleView);

        cal_progress.setMaxValue(100);

        food_name = getIntent().getStringExtra("nameKey");
        serving = getIntent().getStringExtra("servingKey");
        calorie = getIntent().getStringExtra("calorieKey");
        protein = getIntent().getStringExtra("proteinKey");
        crabs = getIntent().getStringExtra("crabKey");
        fats = getIntent().getStringExtra("fatsKey");

        serving_size = findViewById(R.id.serving_size);
        add_food = findViewById(R.id.add_food);

        name_txt = findViewById(R.id.name);
        protein_txt = findViewById(R.id.food_protein);
        crabs_txt = findViewById(R.id.food_crabs);
        fats_txt = findViewById(R.id.food_fats);
        calorie_txt = findViewById(R.id.food_calorie);

        protein_progress = findViewById(R.id.protein);
        crabs_progress = findViewById(R.id.crabs);
        fats_progress = findViewById(R.id.fats);

        protein_fl = Float.parseFloat(protein);
        crabs_fl = Float.parseFloat(crabs);
        fats_fl = Float.parseFloat(fats);

        ser_pro = (int) protein_fl;
        ser_carb = (int) crabs_fl;
        ser_fat = (int) fats_fl;

        float cal_protein, cal_crabs, cal_fats;

        cal_protein = 4 * protein_fl;
        cal_crabs = 4 * crabs_fl;
        cal_fats = 9 * fats_fl;

        // float kcal = cal_protein + cal_fats + cal_crabs;

        final float kcal = Float.parseFloat(calorie);

        ser_kcal = (int) kcal;

        int protein_per = Math.round((cal_protein / kcal) * 100);
        protein_progress.setProgress(protein_per);

        int crabs_per = Math.round((cal_crabs / kcal) * 100);
        crabs_progress.setProgress(crabs_per);

        int fats_per = Math.round((cal_fats / kcal) * 100);
        fats_progress.setProgress(fats_per);

        name_txt.setText(food_name);
        protein_txt.setText(protein);
        crabs_txt.setText(crabs);
        fats_txt.setText(fats);
        calorie_txt.setText(calorie);

        kcal_per = (int) ((kcal * 100) / cal_intake);

        cal_progress.setValueAnimated(kcal_per);

        serving_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (serving_et.getText().toString().equals("")) {
                } else {
                    int ser = Integer.parseInt(serving_et.getText().toString());
                    int cal = kcal_per * ser;
                    cal_progress.setValueAnimated(cal);


                    ser_pro = (int) (ser * protein_fl);
                    ser_carb = (int) (ser * crabs_fl);
                    ser_fat = (int) (ser * fats_fl);
                    ser_kcal = (int) (ser * kcal);

                    protein_txt.setText(String.valueOf(ser_pro));
                    crabs_txt.setText(String.valueOf(ser_carb));
                    fats_txt.setText(String.valueOf(ser_fat));
                    calorie_txt.setText(String.valueOf(ser_kcal));
                }
            }
        });


        add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Food_Cell.this);
                View view1 = LayoutInflater.from(Food_Cell.this).inflate(R.layout.select_meal, null);

                breakfast = view1.findViewById(R.id.add_breakfast);
                morning_snack = view1.findViewById(R.id.add_morning_snack);
                lunch = view1.findViewById(R.id.add_lunch);
                evening_snack = view1.findViewById(R.id.add_evening_snack);
                dinner = view1.findViewById(R.id.add_dinner);

                builder.setView(view1);

                final AlertDialog dialog = builder.create();
                dialog.show();

                breakfast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        send_data("breakfast");
                        dialog.dismiss();
                    }
                });

                morning_snack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        send_data("morning");
                        dialog.dismiss();
                    }
                });
                lunch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        send_data("lunch");
                        dialog.dismiss();
                    }
                });
                evening_snack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        send_data("evening");
                        dialog.dismiss();
                    }
                });
                dinner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        send_data("dinner");
                        dialog.dismiss();
                    }
                });


            }
        });

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(Food_Cell.this, R.array.serving_size, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serving_size.setAdapter(adapter);
        serving_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Food_Cell.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void Back(View view) {

        Intent i  = new Intent(Food_Cell.this, Main_layout.class);
        i.putExtra("result" , "cancel");
        startActivity(i);
        finish();
    }

    public void send_data(String s) {
        final Calendar cal = Calendar.getInstance();

        final int  year_x = cal.get(Calendar.YEAR);
        int  month_x = cal.get(Calendar.MONTH);
        int  day_x = cal.get(Calendar.DAY_OF_MONTH);


        final String curent_date = day_x+"_"+(month_x+1)+"_"+year_x;

        String time = String.valueOf(System.currentTimeMillis());

        Food food_consumed =new Food(food_name,serving,ser_kcal,ser_pro,ser_carb,ser_fat);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth authenticate = FirebaseAuth.getInstance();

        database.getReference().child("consumed_food").child(authenticate.getCurrentUser().getEmail().replace(".","")).child(curent_date)
                .child(s).child(time).setValue(food_consumed);

                Intent i = new Intent(Food_Cell.this, Main_layout.class);

        i.putExtra("calorieKey", String.valueOf(ser_kcal));
        i.putExtra("proteinKey", String.valueOf(ser_pro));
        i.putExtra("crabKey", String.valueOf(ser_carb));
        i.putExtra("fatsKey", String.valueOf(ser_fat));
        i.putExtra("nameKey", food_name);
        i.putExtra("servingKey", String.valueOf(serving));
        i.putExtra("indentify", s);
        i.putExtra("result", "Ok");

        startActivity(i);

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(Food_Cell.this, Main_layout.class);
        i.putExtra("result", "Ok");
        startActivity(i);

        finish();

    }
}