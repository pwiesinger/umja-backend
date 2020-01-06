package com.lab73.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassFile {
    public String name;
    public List<Attribute> attributes;
    public List<Method> methods;
    public Stereotypes stereotype;

    // optional if stereotype is ENUM
    public List<String> enumCases;

    public ClassFile(String name, String methodsRaw, String attributesRaw, String stereotypeRaw) {
        this.name = name;

        // TODO: CONVERT STEREOTYPE
        if (!stereotypeRaw.equals("")) {
            switch (stereotypeRaw.toLowerCase()) {
                case("enumeration"):
                case("enum"):
                    this.stereotype = Stereotypes.ENUM;
                    break;
                case("interface"):
                    this.stereotype = Stereotypes.INTERFACE;
                    break;
                default:
                    this.stereotype = Stereotypes.CLASS;
            }
        } else {
            this.stereotype = Stereotypes.CLASS;
        }

        if (this.stereotype != Stereotypes.ENUM) {
            attributes = Attribute.parseAttributes(attributesRaw);
            methods = Method.parseMethods(methodsRaw);
        } else {
            // parse enumeration
            Pattern enumCasesPattern = Pattern.compile("\\w*");
            Matcher matcher = enumCasesPattern.matcher(attributesRaw);
            if (matcher.find()) {
                enumCases = new ArrayList<>();
                do {
                    String attr = matcher.group();
                    if (!attr.equals("")) enumCases.add(attr);
                } while(matcher.find());
            }
        }
    }


    public String generateFileContent(String packageName) {
        StringBuilder stringBuilder  = new StringBuilder();
        stringBuilder.append("package " + packageName + ";\n");
        stringBuilder.append("\n");
        String[] a = packageName.split("\\.");
        stringBuilder.append("import " + packageName.split("\\.")[0] + ".*;\n");
        stringBuilder.append("import java.util.*;\n"); // should cover most of the things used...
        stringBuilder.append("\n");
        stringBuilder.append("public " + stereotype.toString().toLowerCase() + " " + name + "{\n");
        stringBuilder.append("\n");

        if (stereotype != Stereotypes.ENUM) {
            for (Attribute attribute : attributes) {
                stringBuilder.append(attribute + "\n");
            }
            for (Method method : methods) {
                stringBuilder.append(method.toString(stereotype == Stereotypes.INTERFACE) + "\n");
            }
        } else {
            stringBuilder.append(String.join(", ", enumCases) + ";\n");
        }

        stringBuilder.append("}");


        return stringBuilder.toString();
    }
}
