package com.Text.Adventure.Game;

import com.google.gson.Gson;

import java.util.Base64;
import java.util.HashMap;

public class Savestate {

    private String mapSave;
    private String playerSave;
    private String npcSave;

    private String encoded;

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

    public static Savestate decodeSave(String encodedSave) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedSave);
        String jsonSave = new String(decodedBytes);
        HashMap<String, String> save = new Gson().fromJson(jsonSave, HashMap.class);
        return new Savestate(save.get("mapSave"), save.get("playerSave"), save.get("npcSave"));
    }

    public void encodeSave() {
        String saveJson = new Gson().toJson(this);
        byte[] encodedBytes = Base64.getEncoder().encode(saveJson.getBytes());
        this.encoded = new String(encodedBytes);
    }

    public String getEncoded() {
        this.encodeSave();
        return encoded;
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
