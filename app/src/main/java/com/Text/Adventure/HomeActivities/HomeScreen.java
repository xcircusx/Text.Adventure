package com.Text.Adventure.HomeActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.Text.Adventure.R;



public class HomeScreen extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
    }

    public void goMainScreen(View view){
        Intent mainScreen = new Intent( this, MainScreen.class);
        startActivity(mainScreen);
    }
}