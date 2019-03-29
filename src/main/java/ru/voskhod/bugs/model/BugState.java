package ru.voskhod.bugs.model;

import java.util.List;

public class BugState {

    private int id;
    private String name;
    private List<Bug> bugs;
    private List<BugButton> buttons;

    public BugState(int id, String name, List<Bug> bugs, List<BugButton> buttons) {
        this.id = id;
        this.name = name;
        this.bugs = bugs;
        this.buttons = buttons;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Bug> getBugs() {
        return bugs;
    }

    public List<BugButton> getButtons() {
        return buttons;
    }
}
