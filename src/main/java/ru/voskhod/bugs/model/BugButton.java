package ru.voskhod.bugs.model;

public class BugButton {

    private String text;
    private int stateTo;

    public BugButton(String text, int stateTo) {
        this.text = text;
        this.stateTo = stateTo;
    }

    public String getText() {
        return text;
    }

    public int getStateTo() {
        return stateTo;
    }
}
