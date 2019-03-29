package ru.voskhod.bugs.view;

import freemarker.template.TemplateException;
import ru.voskhod.bugs.model.BugData;

import java.io.IOException;
import java.io.Writer;

public interface MainView {

    void render(BugData data,
                Writer writer) throws IOException, TemplateException;
}
