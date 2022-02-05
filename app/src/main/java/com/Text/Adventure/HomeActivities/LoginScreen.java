package com.Text.Adventure.HomeActivities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.Text.Adventure.Firebase.FirebaseFunctions;
import com.Text.Adventure.GameActivities.MainScreen;
import com.Text.Adventure.Google.GoogleActiveAccount;
import com.Text.Adventure.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SingInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    private SignInButton signInButton;
    private Button signOutButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("188575906459-dvqjcrapcegqqqh85379dbgrakmnuh96.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = findViewById(R.id.button_login);
        signInButton.setOnClickListener(this);

        signOutButton = findViewById(R.id.button_logout);
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
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        GoogleActiveAccount.setAccount(account);
       if (GoogleActiveAccount.getAccount() != null && FirebaseFunctions.getAccount() == null) {
            firebaseAuthWithGoogle(GoogleActiveAccount.getAccount());
        }
        updateUI();
    }

    public void goMainScreen(View view) {
        Intent mainScreen = new Intent(this, MainScreen.class);
        startActivity(mainScreen);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleActiveAccount.setAccount(result.getSignInAccount());
            firebaseAuthWithGoogle(GoogleActiveAccount.getAccount());
            updateUI();
        } else {
            Toast.makeText(this, "Login fehlgeschlagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mAuth.signOut();
        FirebaseFunctions.setAccount(null);
        mGoogleSignInClient.signOut().addOnSuccessListener(result -> {
            GoogleActiveAccount.setAccount(null);
            updateUI();
        });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(this, authResult -> {
                    FirebaseFunctions.setAccount(authResult.getUser());
                    FirebaseFunctions.getAppUser();
                    startActivity(new Intent(LoginScreen.this, LoginScreen.class));
                    finish();
                })
                .addOnFailureListener(this, e -> {
                        Toast.makeText(LoginScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginScreen.this, LoginScreen.class));
                    }
                );
    }

    public void goHomeScreen(View view) {
        Intent homeScreen = new Intent(this, HomeScreen.class);
        startActivity(homeScreen);
    }
}