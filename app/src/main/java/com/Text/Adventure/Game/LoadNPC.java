package com.Text.Adventure.Game;

import com.Text.Adventure.Firebase.FirebaseFunctions;
import com.Text.Adventure.Game.Dialog.Condition;
import com.Text.Adventure.Game.Dialog.Option;
import com.Text.Adventure.Game.Dialog.State;
import com.Text.Adventure.Game.Dialog.Trigger;
import com.Text.Adventure.json.JSONObject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadNPC {

    static String npcString = "";

    private static final String PATH = "src/main/resources/actors/";

    private static HashMap<String, NPC> npcs = new HashMap<String, NPC>();

    static {
        npcString = FirebaseFunctions.npcs;

        JSONObject npcsJson = new JSONObject(npcString);

        for (String npc : npcsJson.keySet()) {
            HashMap<String, State> states = new HashMap<>();
            for (String state : npcsJson.getJSONObject(npc).getJSONObject("states").keySet()) {
                JSONObject current_state = npcsJson.getJSONObject(npc).getJSONObject("states").getJSONObject(state);
                String id = state;
                String npc_text = current_state.getString("npc_text");
                ArrayList<Trigger> stateTriggerList = new ArrayList<>();
                for (Object trigger : current_state.getJSONArray("trigger"))
                    stateTriggerList.add(new Trigger(String.valueOf(trigger)));

                HashMap<String, Option> options = new HashMap<>();
                for (String option : current_state.getJSONObject("options").keySet()) {
                    JSONObject current_option = current_state.getJSONObject("options").getJSONObject(option);
                    String text = current_option.getString("text");
                    String conditionTrueNextState = current_option.getString("conditionsTrue");
                    String conditionFalseNextState = current_option.getString("conditionsFalse");

                    ArrayList<Trigger> optionTriggerList = new ArrayList<>();
                    for (Object trigger : current_option.getJSONArray("trigger"))
                        optionTriggerList.add(new Trigger(String.valueOf(trigger)));
                    ArrayList<Condition> optionConditionList = new ArrayList<>();
                    for (Object condition : current_option.getJSONArray("conditions"))
                        optionConditionList.add(new Condition(String.valueOf(condition)));

                    options.put(
                            option,
                            new Option(text, optionTriggerList, optionConditionList, conditionTrueNextState, conditionFalseNextState)
                    );
                }
                states.put(
                        state,
                        new State(id, stateTriggerList, options, npc_text)
                );
            }
            npcs.put(npc, new NPC(npc, states));
        }
    }

    public static HashMap<String, NPC> getNpcs() {
        return npcs;
    }

    public static NPC getNpc(String name) {
        return npcs.get(name);
    }

    public static String toJson() {
        HashMap<String, String> currentStates = new HashMap<>();
        npcs.forEach((name, npc) -> {
            currentStates.put(name, npc.getCurrentStateId());
        });
        return new Gson().toJson(currentStates);
    }

    public static void fromJson(String json) {
        HashMap<String, String> newStates = new Gson().fromJson(json, HashMap.class);
        newStates.forEach((npc_name, state) -> {
            npcs.get(npc_name).setCurrentStateId(state);
        });
    }
}
