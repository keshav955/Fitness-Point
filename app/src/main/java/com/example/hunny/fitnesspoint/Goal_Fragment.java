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

                goal = goal_id.getText().toString();

                Toast.makeText(getContext(),goal,Toast.LENGTH_SHORT).show();

            }
        });
        return v;
    }

}
