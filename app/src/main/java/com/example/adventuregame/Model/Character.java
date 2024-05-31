package com.example.adventuregame.Model;

import com.example.adventuregame.View.GameScreen;

import java.io.Serializable;
import java.util.ArrayList;

public class Character implements Serializable {
    private int hp;
    private SuperWeapon weapon;
    private final ArrayList<Item> inventory;
    private final GameScreen game;

    public SuperWeapon getWeapon() {
        return weapon;
    }
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void setWeapon(SuperWeapon weapon) {
        this.weapon = weapon;
        game.getWeaponName().setText(String.valueOf(weapon.getName()));
    }

    public Character(int hp, SuperWeapon weapon, ArrayList<Item> inventory, GameScreen game){
        this.hp = hp;
        this.inventory = inventory;
        this.weapon = weapon;
        this.game = game;
        game.getHealthBar().setText(String.valueOf(hp));
        game.getWeaponName().setText(String.valueOf(weapon.getName()));
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        game.getHealthBar().setText(String.valueOf(hp));
    }


}
