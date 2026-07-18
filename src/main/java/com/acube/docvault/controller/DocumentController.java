package com.acube.docvault.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import com.acube.docvault.service.DocumentService;
import java.io.File;


@RestController
@RequestMapping("/api/docs")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

  @PostMapping("/upload")
public String uploadDocument(
        @RequestParam("file") MultipartFile file)
        throws IOException {

    String uploadDir =
    System.getProperty("user.dir") + "/uploads/";

    System.out.println("UPLOAD DIR = " + uploadDir);

    File directory = new File(uploadDir);

    if (!directory.exists()) {
        directory.mkdirs();
    }

    String fileName = file.getOriginalFilename();

    file.transferTo(new File(uploadDir + fileName));

    return "File uploaded successfully: " + fileName;
}

}