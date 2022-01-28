package com.Text.Adventure.Game.Dialog;

import com.Text.Adventure.Game.LoadMap;
import com.Text.Adventure.Game.Player;

import org.apache.commons.lang3.ArrayUtils;

public class Condition {

    private String condition;

    private String[] parseCondition() {
        if (!condition.contains("::")) {
            return new String[]{condition};
        }
        String keyWord = condition.split("::")[0];
        String[] args = condition.split("::")[1].split(":");

        return ArrayUtils.insert(0, args, keyWord);
    }

    public Condition(String trigger) {
        this.condition = trigger;
    }

    public boolean isCorrect(Player player) {
        String[] condition = parseCondition();

        String keyword = condition[0];
        String[] args = ArrayUtils.removeElement(condition, keyword);

        switch (keyword) {
            case "hp_below":
                if (player.getHealth() < Integer.parseInt(args[0])) {
                    return true;
                }
                return false;
            case "hasVisitedRoom":
                for (String id : args) {
                    if (!(LoadMap.hasVisitedRoom(Integer.valueOf(id)))) {
                        return false;
                    }
                }
                return true;
            default:
                return true;
        }
    }
}
