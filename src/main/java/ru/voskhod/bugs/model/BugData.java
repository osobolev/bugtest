package ru.voskhod.bugs.model;

import java.util.List;

public class BugData {

    private List<BugState> states;

    public BugData(List<BugState> states) {
        this.states = states;
    }

    public List<BugState> getStates() {
        return states;
    }
}
