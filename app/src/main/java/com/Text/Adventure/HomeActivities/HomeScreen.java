package com.Text.Adventure.HomeActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.Text.Adventure.GameActivities.TextScreen;
import com.Text.Adventure.Google.GoogleActiveAccount;
import com.Text.Adventure.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class HomeScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "SingInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mFirebaseAuth;

    private SignInButton signInButton;
    private Button signOutButton;

    GoogleActiveAccount googleActiveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        mFirebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("370327462233-jp7d8gvqdpohpfll3goldf3lped35bqe.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = (SignInButton) findViewById(R.id.button_login);
        signInButton.setOnClickListener(this);

        signOutButton = (Button) findViewById(R.id.button_logout);
        signOutButton.setOnClickListener(this);

        signOutButton.setVisibility(View.INVISIBLE);
        signInButton.setVisibility(View.INVISIBLE);

        findViewById(R.id.button_login).setOnClickListener(this);

        updateUI();
    }

    private void updateUI() {
        if (GoogleActiveAccount.getAccount() != null) {
            signOutButton.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.INVISIBLE);
        } else {
            signInButton.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        GoogleActiveAccount.setAccount(account);
    }

    public void goTextScreen(View view){

        Intent textScreen = new Intent( this, TextScreen.class);
        startActivity(textScreen);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                signIn();
                break;
            case R.id.button_logout:
                signOut();
                break;
        }
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        System.out.println(result.getStatus());
        Log.d(TAG, "handleSignInResult:" +result.isSuccess());
        if (result.isSuccess()) {
            GoogleActiveAccount.setAccount(result.getSignInAccount());
            System.out.println(GoogleActiveAccount.getAccount().getEmail());
            //firebaseAuthWithGoogle(GoogleActiveAccount.getAccount());
        }
        else {
            System.out.println("Login Failed");
            Toast.makeText(this, "Login Fehlgeschlagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

        updateUI();
    }

    private void signOut() {
        //Auth.GoogleSignInApi.signOut(mGoogleSignInClient).setResultCallback(new ResultCallback<Status>() {
        //    @Override
        //    public void onResult(@NonNull Status status) {
        //        //ausgeloggt
        //    }
        //});
        updateUI();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(this, authResult -> {
                    startActivity(new Intent(HomeScreen.this, HomeScreen.class));
                    finish();
                })
                .addOnFailureListener(this, e -> Toast.makeText(HomeScreen.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}