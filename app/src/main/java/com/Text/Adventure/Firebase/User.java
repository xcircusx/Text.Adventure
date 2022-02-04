package com.Text.Adventure.Firebase;

import com.google.firebase.firestore.PropertyName;

public class User {

    private String mail;
    private String savestate;

    public User(String mail, String savestate) {
        this.mail = mail;
        this.savestate = savestate;
    }

    @PropertyName("mail")
    public String getMail() {
        return mail;
    }

    @PropertyName("savestate")
    public String getSavestate() {
        return savestate;
    }
}
