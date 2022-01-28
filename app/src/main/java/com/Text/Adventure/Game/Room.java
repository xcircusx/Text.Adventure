package com.Text.Adventure.Game;

import com.Text.Adventure.Game.Dialog.Condition;

import java.util.ArrayList;

public class Room {

    int id = 0;
    String name = "";
    String description = "";
    ArrayList<Condition> conditions = null;
    ArrayList<NPC> npcs = null;

    public Room(int id, String name, String description, ArrayList<Condition> conditions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.conditions = conditions;
        this.npcs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<Condition> conditions) {
        this.conditions = conditions;
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public void setNpcs(ArrayList<NPC> npcs) {
        this.npcs = npcs;
    }

    public void addNpc(NPC npc) {
        this.npcs.add(npc);
    }
}
