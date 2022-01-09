package com.Text.Adventure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
    }
    public void goTextScreen(View view){

        Intent textScreen = new Intent( this, TextScreen.class);
        startActivity(textScreen);

    }
}