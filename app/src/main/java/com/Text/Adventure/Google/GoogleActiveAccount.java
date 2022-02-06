package com.Text.Adventure.Google;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class GoogleActiveAccount {

    private static GoogleSignInAccount account;

    public static void setAccount(GoogleSignInAccount account1) {
        account = account1;
    }

    public static GoogleSignInAccount getAccount() {
        return account;
    }
}
