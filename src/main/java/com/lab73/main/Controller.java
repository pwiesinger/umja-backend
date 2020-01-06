package com.lab73.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;

@RestController
public class Controller {

    Logger logger = LoggerFactory.getLogger(Controller.class);
    @PostMapping("/convert")
    public ResponseEntity<Resource> convert(@RequestParam("file") MultipartFile file) throws MalformedURLException {
        Resource resource = new UrlResource(new File("text.txt.zip").toURI());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @RequestMapping(value = "/convertText", method = RequestMethod.POST)
    public ResponseEntity<Resource> Test(@RequestBody String input) throws MalformedURLException {
        Resource resource = new UrlResource(new File("text.txt.zip").toURI());
        logger.info(input);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
