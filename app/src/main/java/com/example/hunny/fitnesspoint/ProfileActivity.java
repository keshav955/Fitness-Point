package com.example.hunny.fitnesspoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hunny.fitnesspoint.dataModel.SignUpData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    TextView name ,email_id , goal , gender, dob, weight, height,sign_out,activity;
    CircleImageView profile_pic;
    ImageView profile_pic1;

     FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final  ProgressDialog pd = new ProgressDialog(ProfileActivity.this);

        pd.setTitle("Fetching..");
        pd.setMessage("Please wait ..");
        pd.show();

        auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace(".","");

        name = findViewById(R.id.u_name);
        email_id = findViewById(R.id.u_email);
        goal = findViewById(R.id.u_current_plan);
        gender = findViewById(R.id.u_gender);
        dob = findViewById(R.id.u_dob);
        weight = findViewById(R.id.u_weight);
        height = findViewById(R.id.u_height);
        sign_out = findViewById(R.id.sign_out);
        activity = findViewById(R.id.u_activity);
        profile_pic = findViewById(R.id.profile_pic);
        profile_pic1 = findViewById(R.id.profile_pic1);


        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference();
        storageRef.child("images/"+email+".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.with(ProfileActivity.this)
                                .load(uri)
                                .into(profile_pic);
                        Picasso.with(ProfileActivity.this)
                                .load(uri)
                                .into(profile_pic1);


                        // Got the download URL for 'users/me/profile.png'
                    }}).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        //   if(auth.getCurrentUser() != null)

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("users").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pd.hide();

                SignUpData data = dataSnapshot.getValue(SignUpData.class);

                name.setText(data.name);
                email_id.setText(data.email);
                goal.setText(data.goal);
                gender.setText(data.gender);
                dob.setText(data.dob);
                weight.setText(data.weight);
                height.setText(data.height);
                activity.setText(data.activity);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void Back(View view)
    {
        finish();
    }

    public void sign_out(View view)
    {
                auth.signOut();
                finish();
        SharedPreferences.Editor shared_preference = getSharedPreferences("app_data" , MODE_PRIVATE).edit();
        shared_preference.clear().commit();

        Intent i = new Intent(ProfileActivity.this,Main2Activity.class);
                startActivity(i);


    }
}
