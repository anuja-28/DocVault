package com.acube.docvault.controller;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.acube.docvault.service.DocumentService;
import java.io.File;
import com.acube.docvault.entity.Document;
import com.acube.docvault.entity.User;
import com.acube.docvault.repository.UserRepository;

import java.util.List;
import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import com.acube.docvault.exception.DocumentAccessDeniedException;
import com.acube.docvault.exception.DocumentNotFoundException;
import com.acube.docvault.exception.UserNotFoundException;
@RestController
@RequestMapping("/api/docs")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")
    public String uploadDocument(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        String uploadDir = System.getProperty("user.dir") + "/uploads/";

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        document.setUser(user);

        documentService.saveDocument(document);

        return "File uploaded successfully: " + fileName;
    }

    @GetMapping("/list")
    public List<Document> listDocuments() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return documentService.getDocumentsByUserId(user.getUserId());
    }

    @GetMapping("/download/{documentId}")
    public ResponseEntity<Resource> downloadDocs(
            @PathVariable Long documentId) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Document document = documentService.getDocumentById(documentId);

        if (!document.getUser().getUserId().equals(user.getUserId())) {
            throw new DocumentAccessDeniedException(
                    "You are not allowed to download this document");
        }

        Path filePath = Paths.get(document.getFilePath());

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new DocumentNotFoundException(
                    "File not found: " + document.getFilePath());
        }

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                document.getOriginalFileName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{documentId}")
    public String deleteDocument(@PathVariable Long documentId) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        documentService.deleteDocument(documentId, user.getUserId());

        return "Document deleted successfully.";
    }

}