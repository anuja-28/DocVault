package com.acube.docvault.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.acube.docvault.repository.DocumentRepository;
import com.acube.docvault.entity.Document;
import java.util.List;

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

}
