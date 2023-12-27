package com.example.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileDownloadController {

    @GetMapping("/download/{uuid}/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String uuid,
                                                 @PathVariable String fileName) {
        Path filePath = Paths.get("C:/Users/G/Desktop/green/Board Files/" + uuid + fileName);

        try{
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists() || resource.isReadable())
                return ResponseEntity.ok()
                        .header("Content-Disposition",
                                "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
