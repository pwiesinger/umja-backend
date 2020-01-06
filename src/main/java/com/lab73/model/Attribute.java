package com.lab73.model;

import com.lab73.logic.UppercaseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Attribute {
    private boolean isPublic;
    private boolean isStatic;
    private boolean isFinal;
    private String type;
    private String name;

    public Attribute(boolean isPublic, boolean isStatic, boolean isFinal, String type, String name) {
        this.isPublic = isPublic;
        this.isStatic = isStatic;
        this.isFinal = isFinal;
        this.type = type;
        this.name = name;
    }

    public static List<Attribute> parseAttributes(String raw) {
        if (raw.contains("<html>")) {
            return parseHTML(raw);
        } else {
            return parseRegular(raw);
        }
    }

    private static List<Attribute> parseHTML(String raw) {
        List<Attribute> attributes = new ArrayList<>();

        Pattern FIND_METHODS = Pattern.compile("[+|-].*(<br>)*");
        Matcher matcher = FIND_METHODS.matcher(raw);
        while(matcher.find()) {
            String rawLine = matcher.group().trim();
            boolean isStatic = rawLine.contains("<u>") || rawLine.contains("</u>");
            boolean isPublic = rawLine.startsWith("+");
            String[] items = rawLine.replaceAll("[+|-]","")
                    .replace("<u>", "")
                    .replace("</u>", "")
                    .replace("<br>", "")
                    .split(":");
            String name = items[0].trim();
            String type = items[1].trim();
            boolean isu = UppercaseUtil.isUppercase("ASDFASDFA");
            attributes.add(new Attribute(isPublic, isStatic, UppercaseUtil.isUppercase(name), type, name));
        }

        return attributes;
    }

    private static List<Attribute> parseRegular(String raw) {
        List<Attribute> attributes = new ArrayList<>();

        String[] lines = raw.split("\n");
        for (String line : lines) {
            line = line.trim();
            boolean isPublic = line.startsWith("+");
            String[] items = line.replaceAll("[-|+]", "").split(":");
            String name = items[0].trim();
            String type = items[1].trim();

            attributes.add(new Attribute(isPublic, false, UppercaseUtil.isUppercase(name), type, name));
        }

        return attributes;
    }
}
