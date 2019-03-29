package ru.voskhod.bugs.view;

import freemarker.cache.FileTemplateLoader;
import freemarker.core.HTMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class TemplateUtil {

    private static final Configuration CONFIG = new Configuration(Configuration.VERSION_2_3_28);

    private static final Logger logger = LoggerFactory.getLogger(TemplateUtil.class);

    static {
        CONFIG.setDefaultEncoding("UTF-8");
        try {
            CONFIG.setTemplateLoader(new FileTemplateLoader(new File(".")));
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        CONFIG.setOutputFormat(HTMLOutputFormat.INSTANCE);
    }

    public static void render(String templateName, Map<String, Object> data,
                              Writer output) throws IOException, TemplateException {
        Template template = CONFIG.getTemplate(templateName);
        template.process(data, output);
    }
}
