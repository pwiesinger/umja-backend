package com.lab73.main;


import com.lab73.logic.Compiler;
import com.lab73.logic.Parser;
import com.lab73.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

@RestController
public class Controller {

    Logger logger = LoggerFactory.getLogger(Controller.class);

    /*@PostMapping("/convert")
    public ResponseEntity<Resource> convert(@RequestParam("file") MultipartFile file) throws MalformedURLException {
        Resource resource = new UrlResource(new File("text.txt.zip").toURI());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }*/

    @RequestMapping(value = "/convertText", method = RequestMethod.POST)
    public ResponseEntity<Resource> Test(@RequestBody String input) throws MalformedURLException {
        Compiler compiler = null;
        File generated = null;


        logger.info(input);

        String uid = UUID.randomUUID().toString();
        File file = new File(uid + ".graphml");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(input);
            fw.flush();


            Parser parser = new Parser(file);
            Project project = parser.parse();

            compiler = new Compiler(project);
            compiler.writeOutFiles();
            generated = compiler.generateZip();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }finally {
            file.delete();
        }



        if (generated == null) {
            try {
                generated = Compiler.getErrorFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Resource resource = new UrlResource(generated.toURI());

        System.out.println(generated.toURI());


        //compiler.cleanUp();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(resource);
    }

}
