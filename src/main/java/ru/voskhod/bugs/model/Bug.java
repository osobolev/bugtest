package ru.voskhod.bugs.model;

public class Bug {

    private int id;
    private String shortText;

    public Bug(int id, String shortText) {
        this.id = id;
        this.shortText = shortText;
    }

    public int getId() {
        return id;
    }

    public String getShortText() {
        return shortText;
    }
}
