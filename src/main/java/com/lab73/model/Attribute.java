package com.lab73.model;

import java.util.ArrayList;
import java.util.List;

public class Attribute {
    private String accessLevel;
    private boolean isStatic;
    private boolean isFinal;
    private String type;
    private String name;

    public Attribute(String accessLevel, boolean isStatic, boolean isFinal, String type, String name) {
        this.accessLevel = accessLevel;
        this.isStatic = isStatic;
        this.isFinal = isFinal;
        this.type = type;
        this.name = name;
    }

    public static List<Attribute> parseMethods(String raw) {
        if (raw.contains("<html>")) {
            return parseHTML(raw);
        } else {
            return parseRegular(raw);
        }
    }

    private static List<Attribute> parseHTML(String raw) {
        return null;
    }

    private static List<Attribute> parseRegular(String raw) {
        List<Attribute> attributes = new ArrayList<>();

        String[] lines = raw.split("\n");
        for (String line : lines) {
            line = line.trim();
            boolean isPublic = line.startsWith("+");
            line.
        }

        return null;
    }
}
