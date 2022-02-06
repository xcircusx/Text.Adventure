package com.Text.Adventure.Game.Dialog;

import com.Text.Adventure.Game.LoadMap;
import com.Text.Adventure.Game.Player;

import org.apache.commons.lang3.ArrayUtils;

public class Trigger {

    private String trigger;
    private boolean executed = false;

    private String[] parseTrigger() {
        if (!trigger.contains("::")) {
            return new String[]{trigger};
        }
        String keyWord = trigger.split("::")[0];
        String[] args = trigger.split("::")[1].split(":");

        return ArrayUtils.insert(0, args, keyWord);
    }

    public Trigger(String trigger) {
        this.trigger = trigger;
    }

    public void execute(Player player) {
        String[] trigger = parseTrigger();

        String keyword = trigger[0];
        String[] args = ArrayUtils.removeElement(trigger, keyword);

        switch (keyword) {
            case "damage_player":
                player.takeDamage(Integer.valueOf(args[0]));
                break;
            case "heal_player":
                player.takeHeal(Integer.valueOf(args[0]));
                break;
            case "kill_player":
                player.die();
                break;
            case "open_path":
                for (String direction : args) {
                    LoadMap.setNumberInDirection(Integer.parseInt(direction), -1);
                }
                break;
            case "win_game":
                break;
        }
        this.executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}
