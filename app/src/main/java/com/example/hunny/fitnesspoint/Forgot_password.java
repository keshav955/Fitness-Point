package com.example.hunny.fitnesspoint;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

        final ProgressDialog pd = new ProgressDialog(Forgot_password.this);

        String email = email_et.getText().toString();
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            pd.setTitle("Sending Link..");
            pd.setMessage("Please wait ..");
            pd.show();
        }
           else {
            email_et.setError("invalid email details");
            return;
        }


        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.hide();

                        if (task.isSuccessful()) {
                            Toast.makeText(Forgot_password.this,"Password Reset Link Has Been sent to Your Email",Toast.LENGTH_SHORT);
                        }
                        else
                        {
                            Toast.makeText(Forgot_password.this, "Email does not exits in database",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Back(View view) {
        finish();
    }
}
