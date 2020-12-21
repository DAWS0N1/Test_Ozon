package ru.company.framework.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Item {
    private String name;
    private int current;

    static List<Item> items = new ArrayList<>();

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, int current) {
        this.name = name;
        this.current = current;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public static List<Item> getItems() {
        return items;
    }

    public static void addItems(Item item) {
        Item.items.add(item);
    }
}
