package ru.voskhod.bugs.model;

import java.util.List;

public class BugState {

    private String name;
    private List<Bug> bugs;
    private List<BugButton> buttons;

    public BugState(String name, List<Bug> bugs, List<BugButton> buttons) {
        this.name = name;
        this.bugs = bugs;
        this.buttons = buttons;
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
