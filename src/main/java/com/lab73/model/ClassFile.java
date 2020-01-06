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
                    enumCases.add(matcher.group());
                } while(matcher.find());
            }
        }
    }
}
