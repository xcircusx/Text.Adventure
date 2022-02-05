package com.Text.Adventure.Firebase;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.Text.Adventure.Google.GoogleActiveAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firestore.v1.Document;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class FirebaseFunctions {

    public static String map = "";
    public static String npcs = "";

    private static FirebaseUser account;
    private static FirebaseStorage mStorage;
    private static FirebaseFirestore mFirestore;
    private static boolean registered = false;

    private static User currentUser;

    static final long ONE_MEGABYTE = 1024 * 1024;

    static {
        mStorage = FirebaseStorage.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        getMap();
        getNPCs();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void getAppUser() {
        mFirestore.collection("user").document(account.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.getId().equals(account.getUid()) && documentSnapshot.getData() != null) {
                registered = true;
                String mail = documentSnapshot.get("mail").toString();
                String savestate = documentSnapshot.get("savestate").toString();
                currentUser = new User(account.getUid(), mail, savestate);
            }
            if (!registered) {
                registerUser();
            }
        });
    }

    public static void registerUser() {
        String mail = GoogleActiveAccount.getAccount().getEmail();
        User userObject = new User(account.getUid(), mail, "");
        currentUser = userObject;
        assert mail != null;
        mFirestore.collection("user").document(account.getUid()).set(userObject);
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

    public static void setUserSaveState(String state) {
        HashMap<String, String> value = new HashMap<>();
        value.put("savestate", state);
        mFirestore.collection("user").document(account.getUid()).set(value, SetOptions.merge());
        currentUser.setSavestate(state);
    }

    public static Task<DocumentSnapshot> getUserSaveState() {
        return mFirestore.collection("user").document(currentUser.getMail()).get();
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
