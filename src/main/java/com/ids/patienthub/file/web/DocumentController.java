package com.ids.patienthub.file.web;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;


@RestController
@RequestMapping("/v1/Files")
public class FileUploadController {

    //private final StorageService storageService;

    //@Autowired
    //public FileUploadController(StorageService storageService) {
    //	this.storageService = storageService;
    //}


    //Downeload
    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        Resource file = null; // storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "contentType")
                .header(HttpHeaders.CONTENT_LENGTH, "2113")
                .body(file);
    }

    //Upload
    @PostMapping("/")
    public String upload(@RequestParam("file") MultipartFile file) {
        //storageService.store(file);
        file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String contentType = file.getContentType();
        long size = file.getSize();

        return "le chemain de fichier";
    }

}




















