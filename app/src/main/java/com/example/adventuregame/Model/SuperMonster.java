package com.example.adventuregame.Model;

public class SuperMonster {
    protected String name;
    protected int hp;
    protected int attack;
    protected String type;

    public SuperMonster(String name, String type, int hp, int attack) {
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.attack = attack;
    }

    public String getType() {
        return type;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }
}
