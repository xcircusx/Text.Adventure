package com.Text.Adventure.Game;

import android.widget.TextView;

import com.Text.Adventure.Game.Dialog.Condition;
import com.Text.Adventure.Game.Dialog.State;

import java.util.ArrayList;
import java.util.HashMap;

public class NPC {

    private String name;
    private HashMap<String, State> states;
    private String currentStateId = "0";

    private CharSequence dialog;

    public NPC(String name, HashMap<String, State> states) {
        this.name = name;
        this.states = states;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, State> getStates() {
        return states;
    }

    public void setStates(HashMap<String, State> states) {
        this.states = states;
    }

    public String getCurrentStateId() {
        return currentStateId;
    }

    public void setCurrentStateId(String currentStateId) {
        this.currentStateId = currentStateId;
    }

    public void setDialog(CharSequence dialog) {
        this.dialog = dialog;
    }

    public CharSequence getDialog() {
        return dialog;
    }
}
