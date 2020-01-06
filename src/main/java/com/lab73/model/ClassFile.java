package com.lab73.model;

public class ClassFile {
    public String name;
    public Parameter parameters;
    public Method methods;

    public ClassFile(String name, String methodsRaw, String parametersRaw) {
        this.name = name;
    }
}
