package com.Text.Adventure.Game.Dialog;

import java.util.ArrayList;

public class Option {

    private String text;
    private ArrayList<Trigger> trigger;
    private ArrayList<Condition> conditions;
    private String conditionsTrue;
    private String conditionsFalse;


    public Option(String text, ArrayList<Trigger> trigger, ArrayList<Condition> conditions, String conditionsTrue, String conditionsFalse) {
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

    public String getConditionsTrue() {
        return conditionsTrue;
    }

    public void setConditionsTrue(String conditionsTrue) {
        this.conditionsTrue = conditionsTrue;
    }

    public String getConditionsFalse() {
        return conditionsFalse;
    }

    public void setConditionsFalse(String conditionsFalse) {
        this.conditionsFalse = conditionsFalse;
    }
}
