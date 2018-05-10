package com.example.hunny.fitnesspoint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class Forgot_password extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void forgot_password_pass(View view) {
        EditText email_et = findViewById(R.id.edit_forgot);

        String email = email_et.getText().toString();
        if(email_et.length() == 0 )
        {
            email_et.setError("please enter email first");
            return;
        }



        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Forgot_password.this,"Password Reset Link Has Been sent to Your Email",Toast.LENGTH_SHORT);
                        }
                    }
                });


    }
}
