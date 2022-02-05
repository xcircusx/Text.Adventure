package com.Text.Adventure.Firebase;

public class User {

    private String uid;
    private String mail;
    private String savestate;

    public User(String uid, String mail, String savestate) {
        this.mail = mail;
        this.savestate = savestate;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getMail() {
        return mail;
    }

    public String getSavestate() {
        return savestate;
    }

    public void setSavestate(String savestate) {
        this.savestate = savestate;
    }
}
