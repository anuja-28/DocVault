package com.acube.docvault.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.acube.docvault.repository.DocumentRepository;

@Service
public class DocumentService {
  
    @Autowired
    private DocumentRepository documentRepository;
}
