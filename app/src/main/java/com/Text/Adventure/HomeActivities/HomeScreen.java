package com.Text.Adventure.HomeActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Text.Adventure.Firebase.FirebaseFunctions;
import com.Text.Adventure.Game.LoadMap;
import com.Text.Adventure.Game.LoadNPC;
import com.Text.Adventure.Game.Player;
import com.Text.Adventure.Game.Savestate;
import com.Text.Adventure.GameActivities.MainScreen;
import com.Text.Adventure.R;


public class HomeScreen extends AppCompatActivity  {

    public static Player player;

    private static String playerSave;
    private static String npcSave;
    private static String mapSave;

    private Savestate savestate;

    private TextView saveButton;
    private TextView loadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        player = new Player("Du");

        saveButton = findViewById(R.id.textviewsave);
        loadButton = findViewById(R.id.textviewladen);
        saveButton.setOnClickListener(view -> saveGame());
        loadButton.setOnClickListener(view -> loadGame());
    }

    public static Player getPlayer() {
        return player;
    }

    public void goMainScreen(View view){
        Intent mainScreen = new Intent( this, MainScreen.class);
        startActivity(mainScreen);
    }

    private void loadGame() {
        String encodedSave = FirebaseFunctions.getCurrentUser().getSavestate();
        if (encodedSave.equals("")) {
            Toast.makeText(this, "Kein Speicherstand vorhanden!", Toast.LENGTH_SHORT).show();
        } else {
            savestate = Savestate.decodeSave(encodedSave);
            player = Player.fromJson(savestate.getPlayerSave());
            LoadNPC.fromJson(savestate.getNpcSave());
            LoadMap.fromJson(savestate.getMapSave());
        }
    }

    private void saveGame() {
        playerSave = player.toJson();
        npcSave = LoadNPC.toJson();
        mapSave = LoadMap.toJson();
        savestate = new Savestate(mapSave, playerSave, npcSave);
        FirebaseFunctions.setUserSaveState(savestate.getEncoded());
    }
}