package com.lab73.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Project {
    private Map<String, ArrayList<ClassFile>> packages;

    public Project() {
        packages = new HashMap<>();
    }

    public Map<String, ArrayList<ClassFile>> getPackages() {
        return packages;
    }

    public void addClassFile(String packageName, ClassFile classFile) {
        packages.computeIfAbsent(packageName, k -> new ArrayList<>());
        packages.get(packageName).add(classFile);
    }
}
