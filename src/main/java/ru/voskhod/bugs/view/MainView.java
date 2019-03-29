package ru.voskhod.bugs.view;

import ru.voskhod.bugs.model.BugData;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by master on 29.03.2019.
 */
public interface MainView {
    void render (BugData data, Writer writer) throws IOException;
}
