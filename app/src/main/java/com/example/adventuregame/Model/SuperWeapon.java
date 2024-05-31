package com.example.adventuregame.Model;

public class SuperWeapon extends Item {

    private final int damage;

    public SuperWeapon(String name, int damage) {
        super(name, "Weapon");
        this.damage = damage;
    }


    public int getDamage() {
        return damage;
    }
}
