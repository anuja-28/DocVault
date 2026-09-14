package com.acube.docvault.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.acube.docvault.entity.Document;
import java.util.List;

public interface DocumentRepository
        extends JpaRepository<Document, Long> {

                List<Document> findByUserUserId(Long userId);
                List<Document> findByUserUserIdAndTitleContainingIgnoreCase(
        Long userId,
        String title);

}