package com.example.hunny.fitnesspoint;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunny.fitnesspoint.dataModel.SignUpData;
import com.google.android.gms.common.oob.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class Sign_up extends AppCompatActivity {

    public static Sign_up sign_up_activity;
    Button btn;
    EditText editText_dob;
    static final int REQUEST_IMAGE_CAPTURE = 0;
    EditText editText_et;

    String ht,wt;

    Uri profile_image_uri ;

    Boolean image_selected = false;

    ImageView image_view ;
    NumberPicker kg,lbs,select_measure;


    StepView stepView;

    private int currentStep = 0;

    public static  String email , password , goal , gender , dob , name , weight , height ;


    private static int RESULT_LOAD_IMAGE = 1;

    private FirebaseAuth mAuth;
    int year_x, month_x, day_x;
    static final int DAILOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sign_up_activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        image_view = findViewById(R.id.profile_image);


        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        Sign_Fragment sign = new Sign_Fragment();

        ft.replace(R.id.main_frame, sign);

        ft.commit();

        stepView = findViewById(R.id.step_view);

        stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .steps(new ArrayList<String>() {{
                    add("Sign up");
                    add("Goal");
                    add("BMI");
                }})
                .stepsNumber(3)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                // other state methods are equal to the corresponding xml attributes
                .commit();


       // int Count = getIntent().getIntExtra("count",0);

        EditText email_et = findViewById(R.id.email);
        EditText password_et = findViewById(R.id.password);


          // String name = getIntent().getStringExtra("name");
          //String  email = getIntent().getStringExtra("email");

           // email_et.setText(name);
           // password_et.setText(email);

        // step_view();
    }


    public void Back(View v) {
        finish();
    }

    public void back (View v)
    {
       if(Goal_Fragment.goal_fragment_instance.isVisible())
       {
           FragmentManager fm = getSupportFragmentManager();

           FragmentTransaction ft = fm.beginTransaction();

           Sign_Fragment sign = new Sign_Fragment();

           ft.replace(R.id.main_frame, sign);

           ft.commit();

           if (currentStep > 0) {
               currentStep--;
           }
           stepView.done(false);
           stepView.go(currentStep, true);

           return;
       }
       if(Weight_Fragment.weight_fragment_instance.isVisible())
       {
           FragmentManager fm = getSupportFragmentManager();

           FragmentTransaction ft = fm.beginTransaction();

           Goal_Fragment sign = new Goal_Fragment();

           ft.replace(R.id.main_frame, sign);

           ft.commit();

           if (currentStep > 0) {
               currentStep--;
           }
           stepView.done(false);
           stepView.go(currentStep, true);

           return;
       }
    }



    public void next(View view)
    {
        if (Sign_Fragment.sign_fragment_instance.isVisible() == true) {

            EditText email_et = findViewById(R.id.email);

            EditText password_et = findViewById(R.id.password);

            EditText name_et = findViewById(R.id.name);

            EditText dob_et = findViewById(R.id.date_of_birth);

            final RadioGroup selected_gender = findViewById(R.id.gender);

            name = name_et.getText().toString();

            email = email_et.getText().toString();

            password = password_et.getText().toString();

            dob = dob_et.getText().toString();

            if (name.length()<= 3)
            {
                name_et.setError("Please enter a Name");
                return;
            }

            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            } else {
                 email_et.setError("Please Enter Email");
                return;
            }

            if (password.length() < 7) {
                password_et.setError("Password too short");
                return;
            }
            if (dob.equals(""))
            {
                dob_et.setError("Please enter Date Of Birth");
                return;
            }
            if (selected_gender.getCheckedRadioButtonId() == -1)
            {
                Toast.makeText(Sign_up.this,"Please select Gender",Toast.LENGTH_LONG).show();
                return;
            }

            if(! image_selected)
            {
                Toast.makeText(Sign_up.this,"Please select profile image",Toast.LENGTH_LONG).show();

                return;
            }

            RadioButton radioButton = findViewById( selected_gender.getCheckedRadioButtonId());

            gender = radioButton.getText().toString();

            FragmentManager fm1 = getSupportFragmentManager();

            FragmentTransaction ft1 = fm1.beginTransaction();

            Goal_Fragment sign1 = new Goal_Fragment();

            ft1.replace(R.id.main_frame, sign1);

            ft1.commit();

            if (currentStep < stepView.getStepCount() - 1) {
                currentStep++;
                stepView.go(currentStep, true);
            } else {
                stepView.done(true);
            }
            return;
        }
          if(Goal_Fragment.goal_fragment_instance.isVisible())
        {
            final RadioGroup selected_goal = findViewById(R.id.goal);

            if (selected_goal.getCheckedRadioButtonId() == -1)
            {
                Toast.makeText(Sign_up.this,"Please Select Goal",Toast.LENGTH_LONG).show();
                return;
            }

            RadioButton radioButton = findViewById( selected_goal.getCheckedRadioButtonId());

            goal = radioButton.getText().toString().replace(" ","");

            FragmentManager fm1 = getSupportFragmentManager();

            FragmentTransaction ft1 = fm1.beginTransaction();

            Weight_Fragment sign1 = new Weight_Fragment();

            ft1.replace(R.id.main_frame, sign1);

            ft1.commit();

            if (currentStep < stepView.getStepCount() - 1) {
                currentStep++;
                stepView.go(currentStep, true);
            } else {
                stepView.done(true);
            }

            return;

        }
            if (Weight_Fragment.weight_fragment_instance.isVisible())
            {
                 EditText height_et = findViewById(R.id.height);
                 EditText weight_et = findViewById(R.id.weight);

                height = height_et.getText().toString();
                weight = weight_et.getText().toString();

                if (height.equals("")) {
                    height_et.setError("Please Enter Height First");
                    return;
                }

                if (weight.equals("")) {
                    weight_et.setError("Please enter Weight First");
                    return;
                }

                if(mAuth.getCurrentUser() == null) {
                    save_authentication_data();
                }
                else
                {
                    save_profile_data();
                }
                if (currentStep < stepView.getStepCount() - 1) {
                    currentStep++;
                    stepView.go(currentStep, true);
                } else {
                    stepView.done(true);
                }
            }

    }

    public void input_weight (View v)
    {
        final EditText weight_et = findViewById(R.id.weight);
        final TextView weight_kg = findViewById(R.id.kg);

        View view1 = LayoutInflater.from(Sign_up.this).inflate(R.layout.weight_picker,null);

        kg = view1.findViewById(R.id.kg);
        lbs = view1.findViewById(R.id.lbs);
        select_measure = view1.findViewById(R.id.select_measure);

        AlertDialog.Builder builder = new AlertDialog.Builder(Sign_up.this);
        builder.setMessage("INPUT WEIGHT");
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(lbs.getVisibility() == View.VISIBLE ) {
                    wt = String.valueOf(lbs.getValue());
                    weight_et.setText(wt);
                    weight_kg.setText("Lbs");
                }
                else
                {
                    wt = String.valueOf(kg.getValue());
                    weight_et.setText(wt);
                    weight_kg.setText("Kg");
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

            }
        });

        builder.setView(view1);

        final AlertDialog dialog = builder.create();
        dialog.show();

        kg.setMaxValue(150);
        kg.setMinValue(40);
        lbs.setMinValue(80);
        lbs.setMaxValue(300);
        select_measure.setMaxValue(2);
        select_measure.setMinValue(1);
        select_measure.setDisplayedValues(new String[]{"KG","LBS"});

        select_measure.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1)
            {
                if(i==1)
                {
                    lbs.setVisibility(View.VISIBLE);
                    kg.setVisibility(View.GONE);
                }
                else
                {
                    lbs.setVisibility(View.GONE);
                    kg.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void profile_image(View view)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(Sign_up.this);
        View view1 = LayoutInflater.from(Sign_up.this).inflate(R.layout.input_picture,null);

        LinearLayout camera,gallary;

        camera = view1.findViewById(R.id.camera);
        gallary = view1.findViewById(R.id.gallary);

        builder.setView(view1);

        final AlertDialog dialog = builder.create();
        dialog.show();


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();

                dialog.dismiss();

            }
        });

        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);

                dialog.dismiss();
            }
        });


       /* Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
*/
    }

    private void dispatchTakePictureIntent() {

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);//zero can be replaced with any action code

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    image_view.setImageURI(selectedImage);

                    image_selected = true;

                    profile_image_uri = selectedImage;
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    image_view.setImageURI(selectedImage);

                    image_selected = true;

                    profile_image_uri = selectedImage;
                }
                break;
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();

            Bitmap imageBitmap = (Bitmap) extras.get("data");



            image_view.setImageBitmap(imageBitmap);
        }
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.profile_image);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }*/

    public void save_authentication_data()
    {
        final ProgressDialog progress = new ProgressDialog(Sign_up.this);
        progress.setTitle("Please Wait");
        progress.setMessage("Creating Account....");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        FirebaseAuth authenticate = FirebaseAuth.getInstance();

        OnCompleteListener listener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                progress.hide();
                if (task.isSuccessful())
                {
                    save_profile_data();
                }
                else
                    {
                    Toast.makeText(Sign_up.this, "error try again", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        };

        authenticate.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);

    }
    public void save_profile_data()
    {
        SignUpData data = new SignUpData(name ,email , password , goal , gender, dob, weight, height ,"" );

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        uploadFile(profile_image_uri);

        database.getReference().child("users").child(email.replace(".","")).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(Sign_up.this, "done", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Sign_up.this, Activity.class);
                startActivity(i);
            }
        });
    }

    public void uploadFile(Uri uri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference mountainImagesRef = storageRef.child("images/" + email.replace(".","") + ".jpg");


        UploadTask uploadTask = mountainImagesRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

            }
        });

    }
}