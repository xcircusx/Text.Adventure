package com.Text.Adventure.GameActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Text.Adventure.Game.LoadMap;
import com.Text.Adventure.Game.Player;
import com.Text.Adventure.Game.Room;
import com.Text.Adventure.HomeActivities.HomeScreen;
import com.Text.Adventure.R;

public class MainScreen extends AppCompatActivity {

    private Player player;

    private TextView textView;
    private Button buttonNPC;
    private Button buttonNorth;
    private Button buttonEast;
    private Button buttonSouth;
    private Button buttonWest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.player = HomeScreen.player;

        setContentView(R.layout.main_screen);

        buttonNPC = findViewById(R.id.buttonNPC);
        textView = findViewById(R.id.textView);

        buttonNorth = findViewById(R.id.buttonNorth);
        buttonEast = findViewById(R.id.buttonEast);
        buttonSouth = findViewById(R.id.buttonSouth);
        buttonWest = findViewById(R.id.buttonWest);

        textView.setText("");

        textView.append(LoadMap.getCurrentRoom().getDescription());

        buttonTextChanger();

        buttonNorth.setOnClickListener(view -> {
            changeRoom(1, player);
        });
        buttonEast.setOnClickListener(view -> {
            changeRoom(2, player);
        });
        buttonSouth.setOnClickListener(view -> {
            changeRoom(3, player);
        });
        buttonWest.setOnClickListener(view -> {
            changeRoom(4, player);
        });
        setButtonNPCText();
        changeButtonAvailability();
    }

    public void openNpcScreen(View view) {
        Intent npcScreen = new Intent(this, NPCScreen.class);
        startActivity(npcScreen);
    }

    private void buttonTextChanger(){
        Room northRoom = null;
        Room eastRoom = null;
        Room southRoom = null;
        Room westRoom =  null;

        northRoom = LoadMap.getNextRoomDirection(1);
        eastRoom = LoadMap.getNextRoomDirection(2);
        southRoom = LoadMap.getNextRoomDirection(3);
        westRoom = LoadMap.getNextRoomDirection(4);

        buttonNorth.setText("Norden");
        buttonEast.setText("Osten");
        buttonSouth.setText("Süden");
        buttonWest.setText("Westen");

        if (!(northRoom == null)){
            buttonNorth.setText(northRoom.getName());
        }
        if (!(eastRoom == null)){
            buttonEast.setText(eastRoom.getName());
        }
        if (!(southRoom == null)){
            buttonSouth.setText(southRoom.getName());
        }
        if (!(westRoom == null)){
            buttonWest.setText(westRoom.getName());
        }
    }

    private void setButtonNPCText() {
        buttonNPC.setText("NPC");
        buttonNPC.setClickable(false);
        buttonNPC.setVisibility(View.INVISIBLE);
        if (!(LoadMap.getCurrentRoom().getNpcs().size() == 0)) {
            buttonNPC.setText(LoadMap.getCurrentRoom().getNpcs().get(0).getName());
            buttonNPC.setClickable(true);
            buttonNPC.setVisibility(View.VISIBLE);
        }
    }

    private void changeRoom(int dir, Player player){
        String[] result = LoadMap.changeRoom(dir, player);
        if (result[0].equals("True")){
            buttonTextChanger();
            changeButtonAvailability();
            setButtonNPCText();
            textView.setText("");
            textView.append(LoadMap.getCurrentRoom().getDescription());
        } else {
            textView.append(result[1]);
        }
    }

    private void changeButtonAvailability(){
        buttonNorth.setClickable(LoadMap.northAvailable());
        buttonEast.setClickable(LoadMap.eastAvailable());
        buttonSouth.setClickable(LoadMap.southAvailable());
        buttonWest.setClickable(LoadMap.westAvailable());
    }
}