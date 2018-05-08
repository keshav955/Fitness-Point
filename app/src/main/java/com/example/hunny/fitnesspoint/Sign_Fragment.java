package com.example.hunny.fitnesspoint;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sign_Fragment extends Fragment {

    EditText dob;

    View v;

    RadioGroup selected_gender;

    String goal;

    public static Sign_Fragment sign_fragment_instance ;

    public Sign_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sign_fragment_instance = this;

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_sign, container, false);

        dob = v.findViewById(R.id.date_of_birth);

        selected_gender = v.findViewById(R.id.gender);

        selected_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int id = selected_gender.getCheckedRadioButtonId();

                RadioButton goal_id = v.findViewById(id);

                goal = goal_id.getText().toString();

                Toast.makeText(getContext(),goal,Toast.LENGTH_SHORT).show();

            }
        });


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar cal = Calendar.getInstance();

               int  year_x = cal.get(Calendar.YEAR);
               int  month_x = cal.get(Calendar.MONTH);
               int  day_x = cal.get(Calendar.DAY_OF_MONTH);

               final String curent_date = day_x+"/"+(month_x+1)+"/"+year_x+" 00:00:00";

               DatePickerDialog dp = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {



                       String selected_date = i2+"/"+(i1+1)+"/"+i+" 00:00:00";

                      if( ( compare_date(curent_date , selected_date) / 365 ) >= -16 )
                      {
                          Toast.makeText(getContext(), "age must be greater than 16" , Toast.LENGTH_SHORT).show();
                      }
                      else {
                          dob.setText(String.valueOf(i2 + "/" + (i1 + 1) + "/" + i));
                      }


                   }
               } , year_x , month_x , day_x);

               dp.show();

            }
        });
        return v;
    }

    public long compare_date(String date1 , String date2 )

    {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        try {
            Date date1_ = simpleDateFormat.parse(date1);
            Date date2_ = simpleDateFormat.parse(date2);

           return printDifference(date1_, date2_);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

//1 minute = 60 seconds
//1 hour = 60 x 60 = 3600
//1 day = 3600 x 24 = 86400
        public long printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        return elapsedDays;
    }

    @Override
    public void onResume() {

        EditText email_et = v.findViewById(R.id.email);

        EditText password_et = v.findViewById(R.id.password);

        EditText name_et = v.findViewById(R.id.name);

        EditText dob_et = v.findViewById(R.id.date_of_birth);

        RadioButton male_button = v.findViewById(R.id.male);
        RadioButton female_button = v.findViewById(R.id.female);


        super.onResume();

        if(Sign_up.email != null)
        {
            email_et.setText(Sign_up.email);
        }

        if(Sign_up.password != null)
        {
            password_et.setText(Sign_up.password);
        }
        if(Sign_up.name != null)
        {
            name_et.setText(Sign_up.name);
        }
        if(Sign_up.dob != null)
        {
            dob_et.setText(Sign_up.dob);
        }
        if(Sign_up.gender != null)
        {
            if(Sign_up.gender.equals("Male"))
            {
                male_button.setChecked(true);
            }
            else
            {
                female_button.setChecked(true);
            }
        }

    }
}
