package ru.voskhod.bugs.view;

import ru.voskhod.bugs.model.BugData;

import java.io.IOException;
import java.io.Writer;

public interface MainView {

    void render(BugData data,
                Writer writer) throws IOException;
}
