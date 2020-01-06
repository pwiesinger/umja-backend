package com.lab73.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Method {

    private String name;
    private String returnType;
    private List<String> parameters;
    private boolean isStatic;
    private boolean isPublic;

    public Method(String name, String returnType, List<String> parameters, boolean isStatic, boolean isPublic) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.isStatic = isStatic;
        this.isPublic = isPublic;
    }

    public static List<Method> parseMethods(String raw) {
        if (raw.contains("<html>")) {
            return parseHTML(raw);
        } else {
            return parseRegular(raw);
        }
    }

    private static List<Method> parseHTML(String raw) {
        return null;
    }

    private static List<Method> parseRegular(String raw) {
        List<Method> methods = new ArrayList<>();

        Pattern NAME_PATTERN = Pattern.compile("\\w*(?=\\()");
        Pattern RETURN_TYPE = Pattern.compile("(?<=(\\)\\s:\\s)).+");
        Pattern PARAMETER_PAIR = Pattern.compile("\\w+\\s*:\\s*\\w*");

        String[] lines = raw.split("\n");
        for (String line : lines) {
            if (!line.trim().equals("")) {
                boolean isPublic = line.startsWith("+");

                Matcher matcher = NAME_PATTERN.matcher(line);
                matcher.find();
                String name = matcher.group();

                matcher = RETURN_TYPE.matcher(line);
                String returnType = matcher.find() ? matcher.group() : null;

                List<String> parameters = new ArrayList<>();
                matcher = PARAMETER_PAIR.matcher(line);
                while (matcher.find()) {
                    String pair = matcher.group();
                    String[] items = pair.split(":");
                    parameters.add(items[1].trim() + " " + items[0].trim());
                }

                methods.add(new Method(name, returnType, parameters, false, isPublic));
            }
        }

        return methods;
    }
}
