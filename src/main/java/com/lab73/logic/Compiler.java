package com.lab73.logic;

import com.lab73.model.ClassFile;
import com.lab73.model.Project;
import org.zeroturnaround.zip.ZipUtil;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Compiler {

    private Project project;
    private String uid;
    private File root;

    public Compiler(Project project) {
        this.project = project;
        this.uid = UUID.randomUUID().toString();
        this.root = new File("conversions/" + uid + "/generatedProject");
        this.root.mkdirs();
    }

    public void writeOutFiles() {

        // creating packages

        for (String packageName : project.getPackages().keySet()){
            File packageFile = new File(root.getPath() + "/" + packageName.replace(".", "/"));
            packageFile.mkdirs();

            // writing source Files
            List<ClassFile> classFiles = project.getPackages().get(packageName);

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


    // NOTE: des kummt davau waun ma de verfickte angabe nd gscheid lest und meint ma soi wirkle wos compilieren um zu schauen obs geht...

    /*public void tryToCompile() {
        List<String> filesToCompile = new ArrayList<>();
        for (String packagePath :
                project.getPackages().keySet()) {
            List<ClassFile> classFiles = project.getPackages().get(packagePath);

            for (ClassFile classFile :
                    classFiles) {
                String fileToCompile = root.getPath() + "/" + packagePath.replace(".", "/") + "/" + classFile.name + ".java";
                filesToCompile.add(fileToCompile);
            }
        }

        System.out.println("Compiling...");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, filesToCompile.toArray(new String[filesToCompile.size()]));
        System.out.println(result);
    }*/

    public File generateZip() {
        File zipFile = new File("conversions/"+ uid + "/umja_files.zip");
        ZipUtil.pack(root, zipFile);
        return zipFile;
    }

    public void cleanUp() {
        File file = new File("conversions/" + uid);
        deleteDirectory(file);
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }


    public static File getErrorFile() throws IOException {
        File error = new File("error.txt");
        if (error.exists()) {
            return error;
        } else {
            FileWriter fw = new FileWriter(error);
            fw.write("Es gab Probleme beim Parsen überprüfe deinen Syntax!");
            fw.flush();
            return error;
        }
    }
}
