package com.acube.docvault.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.acube.docvault.service.DocumentService;
import java.io.File;
import com.acube.docvault.entity.Document;
import com.acube.docvault.entity.User;
import java.util.List;

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

    Document document = new Document();

    document.setTitle(fileName);
    document.setOriginalFileName(fileName);
    document.setStoredFileName(fileName);
    document.setFileType(file.getContentType());
    document.setFileSize(file.getSize());
    document.setFilePath(uploadDir + fileName);

    User user = new User();
user.setUserId(1L);

document.setUser(user);

    documentService.saveDocument(document);

    return "File uploaded successfully: " + fileName;
}

@GetMapping("/list")
public List<Document> listDocuments() {
    return documentService.getDocumentsByUserId(1L);
}

}