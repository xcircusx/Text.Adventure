package com.Text.Adventure.Game.Dialog;

import java.util.ArrayList;
import java.util.HashMap;

public class State {

    private String id;
    private ArrayList<Trigger> trigger;
    private HashMap<String, Option> options;
    private String npc_text;

    public State(String id, ArrayList<Trigger> trigger, HashMap<String, Option> options, String npc_text) {
        this.id = id;
        this.trigger = trigger;
        this.options = options;
        this.npc_text = npc_text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Trigger> getTrigger() {
        return trigger;
    }

    public void setTrigger(ArrayList<Trigger> trigger) {
        this.trigger = trigger;
    }

    public HashMap<String, Option> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, Option> options) {
        this.options = options;
    }

    public String getNpc_text() {
        return npc_text;
    }

    public void setNpc_text(String npc_text) {
        this.npc_text = npc_text;
    }
}
