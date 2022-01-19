package com.Text.Adventure.Game;

import com.Text.Adventure.Game.Dialog.Condition;
import com.Text.Adventure.Game.Dialog.Option;
import com.Text.Adventure.Game.Dialog.State;
import com.Text.Adventure.Game.Dialog.Trigger;
import com.Text.Adventure.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class LoadNPC {
    static String test = "{\"Knecht Ruprecht\":{\"states\":{\"0\":{\"1\":{\"2\":{\"npc_text\":\"Du gieriger Hurensohn!\",\"conditions\":[],\"trigger\":[\"damage_player::20\"],\"options\":{},\"conditionsTrue\":\"\",\"conditionsFalse\":\"\",\"next\":1},\"3\":{\"4\":{\"npc_text\":\"Ich komme aus Koblenz\",\"conditions\":[],\"trigger\":[],\"options\":{\"1\":{\"text\":\"Sehr interressant!\",\"trigger\":[],\"conditionsTrue\":\"1\",\"conditionsFalse\":\"2\"}}},\"npc_text\":\"\",\"trigger\":[],\"options\":{\"1\":{\"text\":\"Wo kommst du denn her?\",\"trigger\":[],\"conditionsTrue\":\"1\",\"conditionsFalse\":\"2\"},\"2\":{\"text\":\"Was machst du Hier?\",\"trigger\":[],\"conditionsTrue\":\"1\",\"conditionsFalse\":\"2\"}}},\"npc_text\":\"Hier ist ein Apfel für dich!\",\"trigger\":[\"heal_player::50\"],\"options\":{\"0\":{\"text\":\"Danke!\",\"conditions\":[],\"trigger\":[],\"conditionsTrue\":\"1\",\"conditionsFalse\":\"2\"}},\"Beispiel\":{\"states\":{\"0\":{\"npc_text\":\"\",\"trigger\":[\"heal_player::50\"],\"options\":{\"0\":{\"text\":\"gfddsdgfdsg\",\"conditions\":[],\"trigger\":[],\"conditionsTrue\":0,\"conditionsFalse\":0}}}}}},\"npc_text\":\"Wie kann ich Helfen?\",\"trigger\":[],\"options\":{\"0\":{\"text\":\"Kannst du mir mit Leben helfen?\",\"conditions\":[\"hp_below::50\"],\"trigger\":[],\"conditionsTrue\":\"1\",\"conditionsFalse\":\"2\"},\"1\":{\"text\":\"Nein danke mit ist nicht mehr zu Helfen.\",\"conditions\":[],\"trigger\":[],\"conditionsTrue\":\"1\",\"conditionsFalse\":\"2\"},\"2\":{\"text\":\"Nach Informationen Fragen\",\"conditions\":[],\"trigger\":[],\"conditionsTrue\":\"1\",\"conditionsFalse\":\"2\"}}}}}}";

    private static final String PATH = "src/main/resources/actors/";

    private static final HashMap<String, NPC> npcs = new HashMap<>();

    static {
        JSONObject npcsJson = new JSONObject(test);

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
            System.out.println("test");
            npcs.put(npc, new NPC(npc, states));
        }
    }

    public static HashMap<String, NPC> getNpcs() {
        return npcs;
    }

    public static NPC getNpc(String name) {
        return npcs.get(name);
    }
}
