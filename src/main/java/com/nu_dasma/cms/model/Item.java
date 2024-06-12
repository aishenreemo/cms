package com.nu_dasma.cms.model;

public class Item {
    public int id;
    public String name;
    public boolean isBorrowed;
    public Item(int id, String name, boolean isBorrowed) {
        this.id = id;
        this.name = name;
        this.isBorrowed = isBorrowed;
    }
}
