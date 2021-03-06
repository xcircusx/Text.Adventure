package com.Text.Adventure.Game;

import com.Text.Adventure.Firebase.FirebaseFunctions;
import com.Text.Adventure.Game.Dialog.Condition;
import com.Text.Adventure.json.JSONArray;
import com.Text.Adventure.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadMap {

    private static String map;
    private static JSONObject mapJson;

    static {
        map = FirebaseFunctions.map;
        mapJson = new JSONObject(map);
    }

    public static void init() {
        map = FirebaseFunctions.map;
        mapJson = new JSONObject(map);
        currentPosition = new int[]{mapJson.getJSONObject("start").getInt("x"), mapJson.getJSONObject("start").getInt("y")};
        loadedRooms = new HashMap<>();
    }

    private static int[] currentPosition = new int[]{mapJson.getJSONObject("start").getInt("x"), mapJson.getJSONObject("start").getInt("y")};
    private static HashMap<Integer, Room> loadedRooms = new HashMap<>();

    private static int getNumberInMapBinding(int x, int y) {
        return mapJson.getJSONArray("roomMappings").getJSONArray(y).getInt(x);
    }

    private static void setNumberInMapBinding(int x, int y, int v) {
        mapJson.getJSONArray("roomMappings").getJSONArray(y).put(x, v);
    }

    public static void setNumberInDirection(int direction, int number) {
        switch (direction) {
            case 1:
                setNumberInMapBinding(currentPosition[0], currentPosition[1] - 1, number);
                break;
            case 2:
                setNumberInMapBinding(currentPosition[0] + 1, currentPosition[1], number);
                break;
            case 3:
                setNumberInMapBinding(currentPosition[0], currentPosition[1] + 1, number);
                break;
            case 4:
                setNumberInMapBinding(currentPosition[0] - 1, currentPosition[1], number);
                break;
        }
    }

    private static Room loadRoomFromJson(int x, int y) {
        int roomId = getNumberInMapBinding(x, y);

        JSONObject room = mapJson.getJSONObject("rooms").getJSONObject(String.valueOf(roomId));

        ArrayList<Condition> conditions = new ArrayList<>();
        JSONArray conditions_array = mapJson.getJSONObject("rooms").getJSONObject(String.valueOf(roomId)).getJSONArray("conditions");
        for (Object condition : conditions_array) {
            conditions.add(new Condition(String.valueOf(condition)));
        }

        Room returnRoom = new Room(roomId, room.getString("name"), room.getString("description"), conditions);

        if (room.getJSONArray("npc").length() > 0) {
            for (Object npcName : room.getJSONArray("npc")) {
                returnRoom.addNpc(LoadNPC.getNpc(String.valueOf(npcName)));
            }
        }

        return returnRoom;
    }

    private static Room getRoom(int x, int y) {
        int roomId = getNumberInMapBinding(x, y);

        if (!loadedRooms.containsKey(roomId)) {
            loadedRooms.put(roomId, loadRoomFromJson(x, y));
        }

        return loadedRooms.get(roomId);
    }

    public static Room getCurrentRoom() {
        return getRoom(currentPosition[0], currentPosition[1]);
    }

    public static boolean northAvailable() {
        return getNumberInMapBinding(currentPosition[0], currentPosition[1] - 1) != 0;
    }

    public static boolean eastAvailable() {
        return getNumberInMapBinding(currentPosition[0] + 1, currentPosition[1]) != 0;
    }

    public static boolean southAvailable() {
        return getNumberInMapBinding(currentPosition[0], currentPosition[1] + 1) != 0;
    }

    public static boolean westAvailable() {
        return getNumberInMapBinding(currentPosition[0] - 1, currentPosition[1]) != 0;
    }

    public static String[] changeRoom(int direction, Player player) {
        String[] returnArray = {"", ""};
        int[] newPosition;
        switch (direction) {
            case 1:
                if (!northAvailable()) {
                    returnArray[0] = "False";
                    returnArray[1] = "Kein Raum.";
                    return returnArray;
                }
                newPosition = new int[]{currentPosition[0], currentPosition[1] - 2};
                if (getNumberInMapBinding(currentPosition[0], currentPosition[1] - 1) > 0) {
                    setNumberInMapBinding(currentPosition[0], currentPosition[1] - 1, getNumberInMapBinding(currentPosition[0], currentPosition[1] - 1) - 1);
                }
                break;
            case 2:
                if (!eastAvailable()) {
                    returnArray[0] = "False";
                    returnArray[1] = "Kein Raum.";
                    return returnArray;
                }
                newPosition = new int[]{currentPosition[0] + 2, currentPosition[1]};
                if (getNumberInMapBinding(currentPosition[0] + 1, currentPosition[1]) > 0) {
                    setNumberInMapBinding(currentPosition[0] + 1, currentPosition[1], getNumberInMapBinding(currentPosition[0] + 1, currentPosition[1]) - 1);
                }
                break;
            case 3:
                if (!southAvailable()) {
                    returnArray[0] = "False";
                    returnArray[1] = "Kein Raum.";
                    return returnArray;
                }
                newPosition = new int[]{currentPosition[0], currentPosition[1] + 2};
                if (getNumberInMapBinding(currentPosition[0], currentPosition[1] + 1) > 0) {
                    setNumberInMapBinding(currentPosition[0], currentPosition[1] + 1, getNumberInMapBinding(currentPosition[0], currentPosition[1] + 1) - 1);
                }
                break;
            case 4:
                if (!westAvailable()) {
                    returnArray[0] = "False";
                    returnArray[1] = "Kein Raum.";
                    return returnArray;
                }
                newPosition = new int[]{currentPosition[0] - 2, currentPosition[1]};
                if (getNumberInMapBinding(currentPosition[0] - 1, currentPosition[1]) > 0) {
                    setNumberInMapBinding(currentPosition[0] - 1, currentPosition[1], getNumberInMapBinding(currentPosition[0] - 1, currentPosition[1]) - 1);
                }
                break;
            default:
                returnArray[0] = "False";
                returnArray[1] = "Kein Raum.";
                return returnArray;
        }
        for (Condition condition : getRoom(newPosition[0], newPosition[1]).getConditions()) {
            if (!condition.isCorrect(player)) {
                returnArray[0] = "False";
                returnArray[1] = "";
                return returnArray;
            }
        }

        currentPosition = newPosition;
        returnArray[0] = "True";
        returnArray[1] = "";
        return returnArray;
    }

    public static Room getNextRoomDirection(int dir) {
        switch (dir) {
            case 1:
                if (!northAvailable()){
                    return null;
                }
                return loadRoomFromJson(currentPosition[0], currentPosition[1]-2);
            case 2:
                if (!eastAvailable()){
                    return null;
                }
                return loadRoomFromJson(currentPosition[0]+2, currentPosition[1]);
            case 3:
                if (!southAvailable()){
                    return null;
                }
                return loadRoomFromJson(currentPosition[0], currentPosition[1]+2);
            case 4:
                if (!westAvailable()){
                    return null;
                }
                return loadRoomFromJson(currentPosition[0]-2, currentPosition[1]);
        }
        return null;
    }

    public static boolean hasVisitedRoom(int id) {
        return loadedRooms.containsKey(id);
    }

    public static String toJson() {
        System.out.println(mapJson);
        return mapJson.toString();
    }

    public static void fromJson(String json)  {
        mapJson = new JSONObject(json);
        System.out.println(mapJson);
    }
}
