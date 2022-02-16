package com.Text.Adventure.GameActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Text.Adventure.Game.Dialog.Condition;
import com.Text.Adventure.Game.Dialog.Option;
import com.Text.Adventure.Game.Dialog.State;
import com.Text.Adventure.Game.Dialog.Trigger;
import com.Text.Adventure.Game.LoadMap;
import com.Text.Adventure.Game.NPC;
import com.Text.Adventure.Game.Player;
import com.Text.Adventure.HomeActivities.HomeScreen;
import com.Text.Adventure.HomeActivities.LostScreen;

import com.Text.Adventure.HomeActivities.WinScreen;
import com.Text.Adventure.R;
import java.util.ArrayList;
import java.util.HashMap;

public class NPCScreen extends AppCompatActivity {

    private NPC npc;

    private Button buttonOption1;
    private Button buttonOption2;
    private Button buttonOption3;

    private Button buttonEnd;

    private TextView textView;
    private Player player;

    private Button[] optionButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.npc_screen);

        player = HomeScreen.getPlayer();

        optionButtons = new Button[3];

        buttonOption1 = findViewById(R.id.buttonNPC1);
        buttonOption2 = findViewById(R.id.buttonNPC2);
        buttonOption3 = findViewById(R.id.buttonNPC3);
        buttonEnd = findViewById(R.id.buttonEnd);
        optionButtons[0] = buttonOption1;
        optionButtons[1] = buttonOption2;
        optionButtons[2] = buttonOption3;

        textView = findViewById(R.id.textViewNPC);
        textView.setMovementMethod(new ScrollingMovementMethod());

        buttonOption1.setOnClickListener(view -> {
            chooseOption(0, player);
        });
        buttonOption2.setOnClickListener(view -> {
            chooseOption(1, player);
        });
        buttonOption3.setOnClickListener(view -> {
            chooseOption(2, player);
        });
        buttonEnd.setOnClickListener(view -> {
            onBackPressed();
        });

        npc = LoadMap.getCurrentRoom().getNpcs().get(0);

        textView.setText("");

        if (npc.getDialog() != null) {
            textView.setText(npc.getDialog());
        }

        executeStateTrigger();
        buttonRoutine();
    }

    private void buttonRoutine() {
        buttonOption1.setVisibility(View.INVISIBLE);
        buttonOption2.setVisibility(View.INVISIBLE);
        buttonOption3.setVisibility(View.INVISIBLE);

        buttonOption1.setClickable(false);
        buttonOption2.setClickable(false);
        buttonOption3.setClickable(false);

        HashMap<String, Option> options = npc.getStates().get(npc.getCurrentStateId()).getOptions();

        options.forEach((s, option) -> {
            optionButtons[Integer.parseInt(s)].setVisibility(View.VISIBLE);
            optionButtons[Integer.parseInt(s)].setClickable(true);
            optionButtons[Integer.parseInt(s)].setText(option.getText());
        });

        appendText(npc.getStates().get(npc.getCurrentStateId()).getNpc_text(), true);
    }

    private void appendText(String text, boolean NPC) {
        if (NPC) {
            textView.append(npc.getName() + ": " + text + "\r\n");
        } else {
            textView.append(player.getName() + ": " + text + "\r\n");
        }
    }

    private void executeStateTrigger() {
        State currentState = npc.getStates().get(npc.getCurrentStateId());
        for (Trigger trigger : currentState.getTrigger()) {
            if(!trigger.isExecuted()) {
                trigger.execute(player);
            }
        }
        if (!player.isAlive()) {
            Intent intent = new Intent(this, LostScreen.class);
            startActivity(intent);
        }
        if (!player.hasWon()) {
            Intent intent = new Intent(this, WinScreen.class);
            startActivity(intent);
        }
    }

    private void chooseOption(int option, Player player) {
        State currentState = npc.getStates().get(npc.getCurrentStateId());
        Option chosenOption = currentState.getOptions().get(String.valueOf(option));

        ArrayList<Condition> conditions = chosenOption.getConditions();
        boolean allConditionsFullfilled = true;
        for (Condition condition : conditions) {
            if (!condition.isCorrect(player)) {
                allConditionsFullfilled = false;
            }
        }
        if (!allConditionsFullfilled) {
            npc.setCurrentStateId(chosenOption.getConditionsFalse());
        } else {
            npc.setCurrentStateId(chosenOption.getConditionsTrue());
        }

        for (Trigger trigger : chosenOption.getTrigger()) {
            if(!trigger.isExecuted()) {
                trigger.execute(player);
            }
        }
        if (!player.isAlive()) {
            Intent intent = new Intent(this, LostScreen.class);
            startActivity(intent);
        }
        if (!player.hasWon()) {
            Intent intent = new Intent(this, WinScreen.class);
            startActivity(intent);
        }
        executeStateTrigger();
        appendText(chosenOption.getText(), false);
        buttonRoutine();

        npc.setDialog(textView.getText());
    }
}