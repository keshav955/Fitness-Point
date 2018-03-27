package com.example.hunny.fitnesspoint;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;


public class Sign_up extends AppCompatActivity {
    Button btn;
    EditText editText_et;

    int year_x, month_x, day_x;
    static final int DAILOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        Sign_Fragment sign1 = new Sign_Fragment();

        ft.replace(R.id.main_frame, sign1);

        ft.commit();
    }

    public void Back(View v) {
        Intent i = new Intent(Sign_up.this, Login.class);
        startActivity(i);
    }


    public void Date_of_birth(View view) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DAILOG_ID);
            }
        };
        btn = (Button) findViewById(R.id.DOB);
        btn.setOnClickListener(listener);

        final Calendar cal = Calendar.getInstance();

        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DAILOG_ID) {
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            year_x = i;
            month_x = i1+1;
            day_x = i2;

            editText_et = findViewById(R.id.date_of_birth);
            editText_et.setText(String.valueOf(day_x + "/" + month_x + "/" + year_x ));

        }
    };
    public void next(View view)
    {
        if(Sign_Fragment.sign_fragment_instance.isVisible())
        {
            FragmentManager fm1 = getSupportFragmentManager();

            FragmentTransaction ft1 = fm1.beginTransaction();

            Goal_Fragment sign1 = new Goal_Fragment();

            ft1.replace(R.id.main_frame, sign1);

            ft1.commit();
        }

    }
}