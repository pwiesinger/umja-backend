package com.lab73.model;

import java.util.ArrayList;
import java.util.List;
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
        List<Method> methods = new ArrayList<>();

        Pattern FIND_METHODS = Pattern.compile("[-|+].*(<br>)*");
        Pattern NAME_PATTERN = Pattern.compile("\\w*(?=\\()");
        Pattern RETURN_TYPE = Pattern.compile("(?<=(\\)\\s:\\s)).+");
        Pattern PARAMETER_PAIR = Pattern.compile("\\w+\\s*:\\s*[^\\)\\,]*");

        raw = raw.replace("<html>", "").replace("</html>", "");

        Matcher matcher = FIND_METHODS.matcher(raw);
        while(matcher.find()) {
            String rawLine = matcher.group();
            boolean isStatic = rawLine.contains("<u>") || rawLine.contains("</u>");
            boolean isPublic = rawLine.startsWith("+");
            rawLine = rawLine.replace("<u>", "")
                    .replace("</u>", "").replace("<br>", "")
                    .trim();
            Matcher m2 = NAME_PATTERN.matcher(rawLine);
            m2.find();
            String name = m2.group();

            m2 = RETURN_TYPE.matcher(rawLine);
            String returnType = m2.find() ? m2.group() : null;

            List<String> parameters = new ArrayList<>();
            m2 = PARAMETER_PAIR.matcher(rawLine);
            while (m2.find()) {
                String pair = m2.group();
                String[] items = pair.split(":");
                parameters.add(items[1].trim() + " " + items[0].trim());
            }

            methods.add(new Method(name, returnType, parameters, isStatic, isPublic));
        }

        return methods;
    }

    private static List<Method> parseRegular(String raw) {
        List<Method> methods = new ArrayList<>();

        Pattern NAME_PATTERN = Pattern.compile("\\w*(?=\\()");
        Pattern RETURN_TYPE = Pattern.compile("(?<=(\\)\\s:\\s)).+");
        Pattern PARAMETER_PAIR = Pattern.compile("\\w+\\s*:\\s*[^\\)\\,]*");

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
