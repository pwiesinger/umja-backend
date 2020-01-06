package com.lab73.model;

import java.util.List;

public class ClassFile {
    public String name;
    public List<Attribute> attributes;
    public List<Method> methods;

    public ClassFile(String name, String methodsRaw, String attributesRaw) {
        this.name = name;
//        System.out.println("---------------");
//        System.out.println(methodsRaw);
        System.out.println();
        System.out.println(attributesRaw);

        methods = Method.parseMethods(methodsRaw);
    }
}
