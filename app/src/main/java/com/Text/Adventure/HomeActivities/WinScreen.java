package com.Text.Adventure.HomeActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.Text.Adventure.Game.LoadMap;
import com.Text.Adventure.Game.LoadNPC;
import com.Text.Adventure.GameActivities.MainScreen;
import com.Text.Adventure.R;

public class WinScreen extends AppCompatActivity {

    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_screen);

        continueButton = findViewById(R.id.button_continue_won);
        continueButton.setOnClickListener(view -> goHomeScreen());
    }

    private void goHomeScreen() {
        LoadMap.init();
        LoadNPC.init();
        Intent homeScreen = new Intent(this, HomeScreen.class);
        startActivity(homeScreen);
    }
}