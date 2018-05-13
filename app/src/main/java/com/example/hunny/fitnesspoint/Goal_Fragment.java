package com.example.hunny.fitnesspoint;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Goal_Fragment extends Fragment {

    View v;

    public static Goal_Fragment goal_fragment_instance;

    RadioGroup selected_goal;

    String goal;

    public Goal_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        goal_fragment_instance = this;

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_goal, container, false);

        selected_goal = v.findViewById(R.id.goal);

        selected_goal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int id = selected_goal.getCheckedRadioButtonId();

                RadioButton goal_id = v.findViewById(id);

                goal = goal_id.getText().toString().replace(" ","");

            }
        });
        return v;
    }

    @Override
    public void onResume() {
        RadioButton lose = v.findViewById(R.id.lose_weight);
        RadioButton maintain = v.findViewById(R.id.maintain_weight);
        RadioButton gain = v.findViewById(R.id.gain_weight);
        RadioButton lean = v.findViewById(R.id.lean_muscle_mass);

        super.onResume();

        if(Sign_up.goal != null)
        {
            if(Sign_up.gender.equals("LoseWeight"))
            {
                lose.setChecked(true);
            }
            if(Sign_up.gender.equals("GainWeight"))
            {
                gain.setChecked(true);
            }
            if(Sign_up.gender.equals("MaintainWeight"))
            {
                maintain.setChecked(true);
            }
            else
            {
                lean.setChecked(true);
            }
        }

    }


}
