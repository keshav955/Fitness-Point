package com.example.hunny.fitnesspoint;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anastr.speedviewlib.Speedometer;

public class BMI extends AppCompatActivity {

    Speedometer speedometer;

    EditText weight_et;
    EditText height_et;
    TextView caption_txt,measure_height,measure_weight;

    float BMI;
    float height;
    int weight;

    String height_st;
    String weight_st;

    String ht,wt;

    NumberPicker feet,inch,cm,select,kg,lbs,select_measure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        speedometer =findViewById(R.id.speedView);
        weight_et  = findViewById(R.id.weight);
        height_et  = findViewById(R.id.height);
        measure_height = findViewById(R.id.measure_height);
        measure_weight = findViewById(R.id.measure_weight);

        caption_txt= findViewById(R.id.caption);

        weight_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(weight_et.getText().toString().equals("")  || height_et.getText().toString().equals(""))
                {
                }
                else
                {
                    Calculate_BMI();
                }
            }
        });

        weight_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                View view1 = LayoutInflater.from(BMI.this).inflate(R.layout.weight_picker,null);

                kg = view1.findViewById(R.id.kg);
                lbs = view1.findViewById(R.id.lbs);
                select_measure = view1.findViewById(R.id.select_measure);

                AlertDialog.Builder builder = new AlertDialog.Builder(BMI.this);
                builder.setMessage("INPUT WEIGHT");
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if(lbs.getVisibility() == View.VISIBLE ) {
                            wt = String.valueOf(lbs.getValue());
                            weight_et.setText(wt);
                            measure_weight.setText("LBS");
                        }
                        else
                        {
                            wt = String.valueOf(kg.getValue());
                            weight_et.setText(wt);
                            measure_weight.setText("KG");
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

                builder.setView(view1);

                final AlertDialog dialog = builder.create();
                dialog.show();

                kg.setMaxValue(150);
                kg.setMinValue(40);
                lbs.setMinValue(80);
                lbs.setMaxValue(300);
                select_measure.setMaxValue(2);
                select_measure.setMinValue(1);
                select_measure.setDisplayedValues(new String[]{"KG","LBS"});

                select_measure.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1)
                    {
                        if(i==1)
                        {
                            lbs.setVisibility(View.VISIBLE);
                            kg.setVisibility(View.GONE);
                        }
                        else
                        {
                            lbs.setVisibility(View.GONE);
                            kg.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });

        height_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                View view1 = LayoutInflater.from(BMI.this).inflate(R.layout.number_picker,null);

                feet = view1.findViewById(R.id.feet);
                inch = view1.findViewById(R.id.inch);
                cm  = view1.findViewById(R.id.cm);
                select = view1.findViewById(R.id.select);

                AlertDialog.Builder builder = new AlertDialog.Builder(BMI.this);
                builder.setMessage("INPUT HEIGHT");
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if(feet.getVisibility() == View.VISIBLE ) {
                            ht = String.valueOf(feet.getValue()) + "." + String.valueOf(inch.getValue());
                            height_et.setText(ht);
                            measure_height.setText("Feet");
                        }
                        else
                        {
                            ht = String.valueOf(cm.getValue());
                            height_et.setText(ht);
                            measure_height.setText("Cm");
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

                builder.setView(view1);

                final AlertDialog dialog = builder.create();
                dialog.show();

                feet.setMaxValue(10);
                feet.setMinValue(1);
                inch.setMinValue(1);
                inch.setMaxValue(12);
                cm.setMaxValue(250);
                cm.setMinValue(100);
                select.setMaxValue(2);
                select.setMinValue(1);
                select.setDisplayedValues(new String[]{"Centimeter","Feet & Inches"});

                select.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1)
                    {
                        if(i==1)
                        {
                            feet.setVisibility(View.VISIBLE);
                            inch.setVisibility(View.VISIBLE);
                            cm.setVisibility(View.GONE);
                        }
                        else
                        {
                            feet.setVisibility(View.GONE);
                            inch.setVisibility(View.GONE);
                            cm.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });

        height_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if( height_et.getText().toString().length() <= 1 || weight_et.getText().toString().length() <=1 )
                {
                }
                else
                {
                    Calculate_BMI();
                }
            }
        });

    }

    public void Calculate_BMI()
    {
        height_st = height_et.getText().toString();
        weight_st = weight_et.getText().toString();

        height = Integer.parseInt(height_st);
        weight = Integer.parseInt(weight_st);

        float height_metre = height/100;
        float height_square = height_metre * height_metre;
        BMI = weight/height_square;

        speedometer.setSpeedAt(BMI);

        if(BMI <= 18.5)
        {
            caption_txt.setText(" Underweight" );
            return;
        }
        if(BMI <= 20)
        {
            caption_txt.setText(" Slightly Underweight");
            return;
        }
        if(BMI <=24.9)
        {
            caption_txt.setText(" Normal");
            return;
        }
        if(BMI <=25.9)
        {
            caption_txt.setText(" Slightly Overweight");
            return;
        }
        if(BMI <=29.9)
        {
            caption_txt.setText(" Overweight");
            return;
        }
        if(BMI >= 30 && BMI <= 34.9)
        {
            caption_txt.setText(" Obesed");
            return;
        }
        if(BMI >35 && BMI <39.9)
        {
            caption_txt.setText(" Highly Obesed");
            return;
        }
        else if(BMI >40)
        {
            caption_txt.setText(" Extremly Obesed");
        }
    }

}
