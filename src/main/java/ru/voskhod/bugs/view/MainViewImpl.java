package ru.voskhod.bugs.view;

import freemarker.template.TemplateException;
import ru.voskhod.bugs.model.BugData;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class MainViewImpl implements MainView {

    @Override
    public void render(BugData data, Writer writer) throws IOException, TemplateException {
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("data", data);
        TemplateUtil.render("bugs.ftl", templateData, writer);
    }
}
