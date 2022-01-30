package com.Text.Adventure.Firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FirebaseActiveAccount {

    public static String map = "";
    public static String npcs = "";

    private static FirebaseUser account;
    private static FirebaseStorage mStorage;
    private static boolean registered;

    static final long ONE_MEGABYTE = 1024 * 1024;

    private static String temp = "";

    static {
        mStorage = FirebaseStorage.getInstance();
        //db = FirebaseFirestore.getInstance();
        getMap();
        getNPCs();
    }

    public static void setAccount(FirebaseUser account1) {
        account = account1;
    }

    public static FirebaseUser getAccount() {
        return account;
    }

    public static void getMap() {
        StorageReference gsReference = mStorage.getReferenceFromUrl("gs://text-adventure-b9681.appspot.com/map.json");

        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                map = new String(bytes, StandardCharsets.UTF_8);
            }
        });
    }

    public static void getNPCs() {
        StorageReference gsReference = mStorage.getReferenceFromUrl("gs://text-adventure-b9681.appspot.com/npcs.json");

        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                npcs = new String(bytes, StandardCharsets.UTF_8);
            }
        });
    }
}
