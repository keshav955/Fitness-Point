package com.example.hunny.fitnesspoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;*/
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;

    //CallbackManager mCallbackManager;

    private GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient ;

    ProgressDialog pd;

    int count = 0;

    private TextView mStatusTextView;
    private TextView mDetailTextView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        pd = new ProgressDialog(Main2Activity.this);

        getWindow().setStatusBarColor(Color.argb(255,8,9,9));
/*

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.fb_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...

            }
        });
*/
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("676889804456-84noev5dnlpno9m944f1rkn0pbld52fk.apps.googleusercontent.com")
                .requestEmail()
                .build();


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

    }

    public void login(View v)
    {
        Intent i = new Intent(Main2Activity.this, Login.class);

        startActivity(i);
    }

    public void google_sign(View view)
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            Intent i = new Intent(Main2Activity.this, Main_layout.class);
            startActivity(i);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                pd.setTitle("Signing In ..");
                pd.setMessage("Please wait ..");
                pd.show();

                GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);

            }
            catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                pd.hide();
                Toast.makeText(Main2Activity.this,"Google sign in failed",Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        /*showProgressDialog();*/
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd.hide();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            if(task.getResult().getAdditionalUserInfo().isNewUser())
                            {
                                Toast.makeText(Main2Activity.this, user.getEmail().toString(),Toast.LENGTH_SHORT).show();
                                Toast.makeText(Main2Activity.this, user.getDisplayName().toString(),Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Main2Activity.this,Sign_up.class);
                               // i.putExtra("name",user.getDisplayName().toString());
                                //i.putExtra("email",user.getEmail().toString());
                                /*count = 1;
                                i.putExtra("count",count);
                                *///i.putExtra("age",user.getPhotoUrl());
                                startActivity(i);
                            }

                            else {

                                Intent i = new Intent(Main2Activity.this,Main_layout.class);
                                //i.putExtra("user",user);
                                startActivity(i);
                            }


/*
                            updateUI(user);
*/
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            Toast.makeText(Main2Activity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
/*
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
*/
/*
                            updateUI(null);
*/
                        }

                        // [START_EXCLUDE]
/*
                        hideProgressDialog();
*/
                        // [END_EXCLUDE]
                    }
                });
    }
/*
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Main2Activity.this, user.getEmail(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(Main2Activity.this, user.getDisplayName(),Toast.LENGTH_SHORT).show();
 Intent i = new Intent(Main2Activity.this,Sign_up.class);
                            i.putExtra("user",user);
                            startActivity(i);;

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Main2Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
*/


/*
    private void updateUI(FirebaseUser user) {
        */
/*hideProgressDialog();*//*

        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText("sign out");
            mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }
*/
         @Override
             protected void onStop() {

              super.onStop();


         }
    @Override
    protected void onDestroy() {

        super.onDestroy();
        mAuth.signOut();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }


    @Override
    protected void onRestart() {
        super.onRestart();

        mAuth.signOut();

    }
}

