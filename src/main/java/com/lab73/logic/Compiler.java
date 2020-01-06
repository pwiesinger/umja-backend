package com.lab73.logic;

import com.lab73.model.ClassFile;
import com.lab73.model.Project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Compiler {

    private Project project;

    public Compiler(Project project) {
        this.project = project;
    }

    public void writeOutFiles() {
        for (String packageName : project.getPackages().keySet())  {
            File theDir = new File("/Users/davidgangl/Desktop/AUD/src/main/java/com/lab73/testProject/" + packageName);

            if (!theDir.exists()) {
                System.out.println("creating directory: " + theDir.getName());
                boolean result = false;

                try{
                    theDir.mkdir();
                    result = true;
                }
                catch(SecurityException se){
                    //handle it
                }
                if(result) {
                    System.out.println("DIR created");
                }
            }


            ArrayList<ClassFile> classFile = project.getPackages().get(packageName);
        }
    }

    public void tryToCompile() {

    }

    public void generateZip() {

    }
}
