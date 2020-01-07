package com.lab73.main;

import com.lab73.logic.Compiler;
import com.lab73.logic.Parser;
import com.lab73.model.Project;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class OfflineMain {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        File file = new File("sample/uml_v3.graphml");
        Parser parser = new Parser(file);
        Project project = parser.parse();

        Compiler compiler = new Compiler(project);
        compiler.writeOutFiles();
        //compiler.tryToCompile();
        compiler.generateZip();
    }
}
