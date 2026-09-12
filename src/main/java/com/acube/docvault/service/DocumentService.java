package com.acube.docvault.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.acube.docvault.repository.DocumentRepository;
import com.acube.docvault.entity.Document;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Path;

import org.springframework.transaction.annotation.Transactional;
import com.acube.docvault.exception.DocumentNotFoundException;
import com.acube.docvault.exception.DocumentAccessDeniedException;


@Service
public class DocumentService {
  
    @Autowired
    private DocumentRepository documentRepository;

    public Document saveDocument(Document document) {
    return documentRepository.save(document);
}

public List<Document> getDocumentsByUserId(Long userId) {
    return documentRepository.findByUserUserId(userId);
}

public Document getDocumentById(Long documentId) {
    return documentRepository.findById(documentId)
            .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + documentId));

}
@Transactional
public void deleteDocument(Long documentId, Long userId) throws Exception {

    Document document = getDocumentById(documentId);

    if (!document.getUser().getUserId().equals(userId)) {
        throw new DocumentAccessDeniedException("You are not allowed to delete this document");
    }

    Path path = Paths.get(document.getFilePath());

    Files.deleteIfExists(path);

    documentRepository.delete(document);
}

}
