package com.example.hunny.fitnesspoint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Food_Cell extends AppCompatActivity {

    Spinner serving_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodcell);

        serving_size = findViewById(R.id.serving_size);

        ArrayAdapter<CharSequence> adapter;
        adapter= ArrayAdapter.createFromResource(Food_Cell.this,R.array.serving_size,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serving_size.setAdapter(adapter);
        serving_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
               String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Food_Cell.this,text,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void Back(View view)
    {
        finish();
    }

}
