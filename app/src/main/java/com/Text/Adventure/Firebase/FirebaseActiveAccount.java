package com.Text.Adventure.Firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseActiveAccount {

    private static FirebaseUser account;


    public static void setAccount(FirebaseUser account1) {
        account = account1;
    }

    public static FirebaseUser getAccount() {
        return account;
    }
}
