package com.example.hunny.fitnesspoint;


import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class Weight_Fragment extends Fragment {

    public static Weight_Fragment weight_fragment_instance;

    View v;

    EditText weight_et;
    EditText height_et;
    TextView BMI_txt ;
    TextView caption_txt;
    TextView height_unit,weight_unit;

    float BMI;
    float height;
    int weight;

    String height_st;
    String weight_st;
    String text;

    public Weight_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        weight_fragment_instance = this;
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_weight, container, false);

        weight_et  = v.findViewById(R.id.weight);
        height_et  = v.findViewById(R.id.height);
        BMI_txt    = v.findViewById(R.id.BMI);
        caption_txt= v.findViewById(R.id.caption);
        height_unit = v.findViewById(R.id.cm);
        weight_unit = v.findViewById(R.id.kg);

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

        height_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

               if( height_et.getText().toString().length() <= 0 || weight_et.getText().toString().length() <=0 )
               {
               }
               else
               {
                   Calculate_BMI();
               }
            }
        });


        height_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.height_spinner,null);
                Spinner spinner = (Spinner) view1.findViewById(R.id.spinner) ;
                final EditText input_height = (EditText) view1.findViewById(R.id.input_height);

                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String input = input_height.getText().toString();

                        height_et.setText(input );
                        height_unit.setText(text);

                    }
                });

                builder.setView(view1);

                final AlertDialog dialog = builder.create();
                dialog.show();

                ArrayAdapter<CharSequence> adapter;
                adapter= ArrayAdapter.createFromResource(getContext(),R.array.measurement,android.R.layout.simple_spinner_item);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                    {
                         text = adapterView.getItemAtPosition(i).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView)
                    {

                    }
                });
            }
        });



        return v;
    }

        public void Calculate_BMI()
        {   String h_unit = height_unit.getText().toString();
            String w_unit = weight_unit.getText().toString();

            height_st = height_et.getText().toString();
            weight_st = weight_et.getText().toString();

            height = Integer.parseInt(height_st);
            weight = Integer.parseInt(weight_st);


            float height_metre = height/100;
            float height_square = height_metre * height_metre;
            BMI = weight/height_square;

            BMI_txt.setText(String.valueOf(BMI));

            CharSequence caption = caption_txt.getText();

            caption_txt.setTextColor(Color.argb(255,244,131,18));

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

    @Override
    public void onResume() {


        super.onResume();

        if(Sign_up.weight != null)
        {
            weight_et.setText(Sign_up.weight);
        }

        if(Sign_up.height != null)
        {
            height_et.setText(Sign_up.height);
        }


    }


}
