package com.example.hunny.fitnesspoint;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sign_Fragment extends Fragment {

    public static  Sign_Fragment sign_fragment_instance ;

    public Sign_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        sign_fragment_instance = this;
        return inflater.inflate(R.layout.fragment_sign, container, false);
    }

}
