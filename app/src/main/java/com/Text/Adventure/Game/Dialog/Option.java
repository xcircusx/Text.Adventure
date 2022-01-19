package com.Text.Adventure.Game.Dialog;

import java.util.ArrayList;

public class Option {

    private String text;
    private ArrayList<Trigger> trigger;
    private ArrayList<Condition> conditions;
    private int conditionsTrue;
    private int conditionsFalse;


    public Option(String text, ArrayList<Trigger> trigger, ArrayList<Condition> conditions, int conditionsTrue, int conditionsFalse) {
        this.text = text;
        this.trigger = trigger;
        this.conditions = conditions;
        this.conditionsTrue = conditionsTrue;
        this.conditionsFalse = conditionsFalse;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Trigger> getTrigger() {
        return trigger;
    }

    public void setTrigger(ArrayList<Trigger> trigger) {
        this.trigger = trigger;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<Condition> conditions) {
        this.conditions = conditions;
    }

    public int getConditionsTrue() {
        return conditionsTrue;
    }

    public void setConditionsTrue(int conditionsTrue) {
        this.conditionsTrue = conditionsTrue;
    }

    public int getConditionsFalse() {
        return conditionsFalse;
    }

    public void setConditionsFalse(int conditionsFalse) {
        this.conditionsFalse = conditionsFalse;
    }
}
