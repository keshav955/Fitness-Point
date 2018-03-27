package com.example.hunny.fitnesspoint;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.*;
import static java.security.AccessController.getContext;

public class Login extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       final   EditText editText = findViewById(R.id.email);
       final   EditText password_et = findViewById(R.id.password);
        final Button login = findViewById(R.id.login_button);




           password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
                public void afterTextChanged(Editable editable) {

                 String email = editText.getText().toString();
                 String password = password_et.getText().toString();
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches() &&password.length()>8)
                {
                    login.setBackgroundResource(R.color.green);
                }

            }
        });

    }

    public void Back(View v)
    {
        Intent i = new Intent(Login.this, Main2Activity.class);
        startActivity(i);
    }

    public void forgot_password(View view)
    {
        TextView txt = findViewById(R.id.forgot);
        ImageView img = (ImageView)findViewById(R.id.key);
        txt.setTextColor(argb(255,63,81,181));


        img.setColorFilter(Color.argb(255, 63, 81, 181));

    }

    public void login(View view) {

        EditText email_et = findViewById(R.id.email);

        EditText password_et = findViewById(R.id.password);

        String email = email_et.getText().toString();

        String password = password_et.getText().toString();

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {

        }
        else {
            Toast.makeText(Login.this , "invalid email syntax" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 7)
        {
            Toast.makeText(Login.this , "password too short" , Toast.LENGTH_SHORT).show();

            return;
        }


    }

    public void sign_up(View view)
    {
        TextView txt = findViewById(R.id.sign_up);

        txt.setTextColor(Color.argb(255,63,81,181));

        Intent i = new Intent(Login.this ,Sign_up.class);

        startActivity(i);
    }
}
