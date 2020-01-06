package com.lab73.logic;

import com.lab73.model.ClassFile;
import com.lab73.model.Project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

public class Compiler {

    private Project project;
    private String uid;

    public Compiler(Project project) {
        this.project = project;
        this.uid = UUID.randomUUID().toString();
    }

    public void writeOutFiles() {

        // creating packages

        File root = new File("conversions/" + uid + "/generatedProject");
        root.mkdirs();

        for (String packageName : project.getPackages().keySet()){
            File packageFile = new File(root.getPath() + "/" + packageName.replace(".", "/"));
            packageFile.mkdirs();

            // writing source Files
            ArrayList<ClassFile> classFiles = project.getPackages().get(packageName);

            for (ClassFile classFile :
                    classFiles) {
                String content = classFile.generateFileContent(packageName);
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(packageFile.getPath() + "/" + classFile.name + ".java")))) {
                    bw.write(content);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void tryToCompile() {

    }

    public void generateZip() {

    }

    public void cleanUp() {
        File file = new File("conversions/" + uid);
        file.delete();
    }
}
