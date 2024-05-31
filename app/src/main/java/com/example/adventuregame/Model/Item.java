package com.example.adventuregame.Model;

public class Item {
    private final String name;

    protected String type;
    public Item(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
