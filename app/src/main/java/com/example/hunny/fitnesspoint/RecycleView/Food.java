package com.example.hunny.fitnesspoint.RecycleView;

/**
 * Created by Lincoln on 15/01/16.
 */
public class Food {
    public String name,serving;
    public float calorie,protein,crabs, fats;

    public Food() {
    }

    public Food(String name,String serving,float calorie,float protein, float crabs, float fats) {
        this.name = name;
        this.calorie = calorie;
        this.protein = protein;
        this.crabs = crabs;
        this.fats = fats;
        this.serving = serving;
    }



}
