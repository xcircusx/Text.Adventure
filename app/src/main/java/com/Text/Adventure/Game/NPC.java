package com.Text.Adventure.Game;

import com.Text.Adventure.Game.Dialog.State;

import java.util.HashMap;

public class NPC {

    private String name;
    private HashMap<String, State> states;
    private String currentStateId = "0";

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
}
