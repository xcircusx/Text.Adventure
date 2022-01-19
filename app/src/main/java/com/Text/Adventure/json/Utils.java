package com.Text.Adventure.json;

import java.util.ArrayList;

public class Utils {
    public static String[] jsonArrayToArray(JSONArray object) {
        try {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < object.length(); i++) {
                list.add(object.get(i).toString());
            }
            return list.toArray(new String[]{});
        } catch (Exception e) {
            return new String[]{""};
        }
    }
}
