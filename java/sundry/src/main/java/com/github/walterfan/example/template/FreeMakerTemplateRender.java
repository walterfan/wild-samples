package com.github.walterfan.example.template;

import com.github.walterfan.example.config.CountryCode;
import com.github.walterfan.example.config.PstnNumber;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by walter on 21/12/2016.
 */
public class FreeMakerTemplateRender {

    private static Configuration cfg;

    static {

        cfg = new Configuration();

        cfg.setClassForTemplateLoading(FreeMakerTemplateRender.class, "/templates/");

        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    }

    public FreeMakerTemplateRender() {
        LoadingCache<String, Template> templateCache =
                CacheBuilder.newBuilder()
                        .maximumSize(100) // maximum 100 records can be cached
                        .expireAfterAccess(30, TimeUnit.MINUTES) // cache will expire after 30 minutes of access
                        .build(new CacheLoader<String, Template>(){ // build the cacheloader

                            @Override
                            public Template load(String templateName) throws Exception {
                                return cfg.getTemplate(templateName);
                            }
                        });
    }

    public String renderAsTemplateId(Map<String, Object> map, String templateName) throws IOException, TemplateException {
        return null;
    }

    public String renderAsTemplateName(Map<String, Object> map, String templateName) throws IOException, TemplateException {
        Template template = cfg.getTemplate(templateName);

        StringWriter writer = new StringWriter();
        template.process(map, writer);

        return writer.toString();
    }


    public String renderAsTemplateContent(Map<String, Object> map, String content) throws IOException, TemplateException {

        //Template template = Template.getPlainTextTemplate("", content, cfg);
        Configuration theCfg = new Configuration();
        theCfg.setObjectWrapper(new DefaultObjectWrapper());
        Template template = new Template("", new StringReader(content), theCfg);

        StringWriter writer = new StringWriter();
        template.process(map, writer);

        return writer.toString();
    }


    public static String processTextTemplate(String textTemplate, Map<String, Object> replacementValues, String templateName) {
        if (replacementValues == null || replacementValues.size() == 0) {
            return textTemplate;
        }

        if (textTemplate == null || "".equals(textTemplate)) {
            throw new IllegalArgumentException("The textTemplate cannot be null or empty string, " +
                    "please pass in at least something in the template or do not call this method");
        }

        // setup freemarker
        Configuration cfg = new Configuration();

        // Specify how templates will see the data-model
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        // get the template
        Template template;
        try {
            template = new Template(templateName, new StringReader(textTemplate), cfg);
        } catch (IOException e) {
            throw new RuntimeException("Failure while creating freemarker template", e);
        }

        Writer output = new StringWriter();
        try {
            template.process(replacementValues, output);
        } catch (TemplateException e) {
            throw new RuntimeException("Failure while processing freemarker template", e);
        } catch (IOException e) {
            throw new RuntimeException("Failure while sending freemarker output to stream", e);
        }

        return output.toString();
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("primaryText", "Walter FTL example");
        input.put("number", new PstnNumber("Java object", CountryCode.CN, false));
        input.put("common_name", "Walter");

        //FreeMakerTemplateRender render = new FreeMakerTemplateRender();
        //String output = render.renderAsTemplateName(input, "sample.ftl");
        //System.out.println(output);

        String content = "hello ${common_name}";
        //output = render.renderAsTemplateContent(input, content);
        //System.out.println(output);


        String str = processTextTemplate(content, input, "abc");

        System.out.println(str);

    }
}