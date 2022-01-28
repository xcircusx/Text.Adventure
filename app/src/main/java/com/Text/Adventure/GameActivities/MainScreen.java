package com.Text.Adventure.GameActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Text.Adventure.Game.LoadMap;
import com.Text.Adventure.R;

public class MainScreen extends AppCompatActivity {

    private TextView textView;
    private Button buttonNPC;
    private Button buttonNorth;
    private Button buttonEast;
    private Button buttonSouth;
    private Button buttonWest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        buttonNPC = findViewById(R.id.buttonNPC);
        textView = findViewById(R.id.textView);

        buttonNorth = findViewById(R.id.buttonNorth);
        buttonEast = findViewById(R.id.buttonEast);
        buttonSouth = findViewById(R.id.buttonSouth);
        buttonWest = findViewById(R.id.buttonWest);

        System.out.println(LoadMap.getCurrentRoom());

        buttonNorth.setClickable(LoadMap.northAvailable());
        buttonEast.setClickable(LoadMap.eastAvailable());
        buttonSouth.setClickable(LoadMap.southAvailable());
        buttonWest.setClickable(LoadMap.westAvailable());

        textView.setText("");

        textView.append(LoadMap.getCurrentRoom().getDescription());
    }

    public void openNpcScreen(View view) {
        Intent npcScreen = new Intent(this, NPCScreen.class);
        startActivity(npcScreen);
    }
}