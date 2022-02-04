package com.Text.Adventure.Game;

import android.content.Intent;

import com.google.gson.Gson;

import java.io.Serializable;

public class Player {

    private String name;
    private int health;

    public Player(String name) {
        this.name = name;
        this.health = 100;
    }

    public void takeDamage(int amount) {
        if (health - amount <= 0) {
            this.die();
        } else {
            health -= amount;
        }
    }

    public void takeHeal(int amount) {
        if (health + amount > 100) {
            health = 100;
        } else {
            health += amount;
        }
    }

    public void die() {
        this.health = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Player fromJson(String json) {
        return new Gson().fromJson(json, Player.class);
    }
}
