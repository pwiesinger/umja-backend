package com.lab73.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

        String[] lines = raw.split("\n");
        for (String line : lines) {
            if (!line.trim().equals("")) {
                StringTokenizer stringTokenizer = new StringTokenizer(line);
                boolean isPublic = stringTokenizer.nextToken().equals("+");
                String[] components = stringTokenizer.nextToken().split("\\(");
                String name = components[0];

                //stringTokenizer = new StringTokenizer(components[1]);
                List<String> parameters = new ArrayList<>();


                
                String lastParameterName = null;
                if (!components[1].equals(")")) lastParameterName = components[1];
                for (int i = 0; stringTokenizer.hasMoreTokens(); i++) {
                    String next = stringTokenizer.nextToken();
                    if (next.equals(")")) break;
                    if (lastParameterName != null) {
                        String type = next.replace(",", "").replace("\\)", "");
                        parameters.add(type + " " + lastParameterName);
                        lastParameterName = null;
                    } else {
                        lastParameterName = next;
                    }
                }

                String returnType = null;
                if (stringTokenizer.hasMoreTokens()) {
                    stringTokenizer.nextToken(); // :
                    returnType = stringTokenizer.nextToken();
                }

                methods.add(new Method(name, returnType, parameters, false, isPublic));
            }
        }

        return methods;
    }
}
