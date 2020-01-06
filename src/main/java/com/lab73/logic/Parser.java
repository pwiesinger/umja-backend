package com.lab73.logic;

import com.lab73.model.ClassFile;
import com.lab73.model.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Parser {

    private Document document;

    public Parser(File file) throws ParserConfigurationException, IOException, SAXException {
        // parse xml document
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        this.document = documentBuilder.parse(file);
        this.document.setXmlStandalone(false);
    }

    /*
    *   ASSUMPTION
    *
    *   -Node (group)
    *       -Node (uml)
    *       -Node (uml)
    *   -Node (group)
    *       -Node (uml)
    *
    *
    *   the nodes can be identified by an id
    *
    *   - n0
    *       -n0::n0
    *       -n0:.n1
    *   - n1
    */
    public Project parse() {
        NodeList nodes = document.getElementsByTagName("node");
        Project project = new Project();

        // iterating through the node entries, here we will mostly catch the groups
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            System.out.println(element.getAttribute("id"));
            //System.out.println(element.getAttribute("yfiles.foldertype"));
            if (element.getAttribute("yfiles.foldertype").equals("group")) {
                String packageName = getTextContent(element, "y:NodeLabel");
                // get all node children of package and then add number of nodes to i to  skip them next time
                NodeList packageNodes = element.getElementsByTagName("node");
                i += packageNodes.getLength();
                for (int a = 0; a < packageNodes.getLength(); a++) {
                    Element classFileRaw = (Element) packageNodes.item(a);
                    project.addClassFile(packageName, parseNode(classFileRaw));
                }
            } else {
                // parse node and add it without package name.
                throw new NotImplementedException();
            }
        }
        return project;
    }



    // private helpers
    private String getTextContent(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private ClassFile parseNode(Element element) {
        String name = getTextContent(element, "y:NodeLabel");
        String attributes = getTextContent(element, "y:AttributeLabel");
        String methods = getTextContent(element, "y:MethodLabel");
        return new ClassFile(name, methods, attributes);
    }
}
