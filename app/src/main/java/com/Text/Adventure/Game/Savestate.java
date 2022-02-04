package com.Text.Adventure.Game;

import com.google.gson.Gson;

import java.util.HashMap;

public class Savestate {

    private String mapSave;
    private String playerSave;
    private String npcSave;

    private HashMap<String, String> save;

    public Savestate(String mapSave, String playerSave, String npcSave) {
        save = new HashMap<>();

        this.mapSave = mapSave;
        this.playerSave = playerSave;
        this.npcSave = npcSave;

        save.put("map", mapSave);
        save.put("player", playerSave);
        save.put("npc", npcSave);
    }

    public String encodeSave() {
        String saveJson = new Gson().toJson(save);
        return saveJson;
    }

    public HashMap<String, String> getSave() {
        return save;
    }

    public static Savestate loadFromJson(String json) {
        HashMap<String, String> save = new Gson().fromJson(json, HashMap.class);
        return new Savestate(save.get("map"), save.get("player"), save.get("npc"));
    }

    public String getPlayerSave() {
        return this.save.get("player");
    }
    public String getMapSave() {
        return this.save.get("map");
    }
    public String getNpcSave() {
        return this.save.get("npc");
    }
}
