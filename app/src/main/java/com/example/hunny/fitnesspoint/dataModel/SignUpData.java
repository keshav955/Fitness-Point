package com.example.hunny.fitnesspoint.dataModel;

/**
 * Created by Hunny on 28-03-2018.
 */

public class SignUpData {

    public  String name ,email , password , goal , gender, dob, weight, height ,activity;


    public SignUpData()
    {

    }

    public SignUpData(String name,String email , String password , String goal , String gender, String dob, String weight, String height , String activity )
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.goal = goal;
        this.gender = gender;
        this.dob = dob;
        this.weight = weight;
        this.height = height;
        this.activity = "Beast Mode";
    }
}
