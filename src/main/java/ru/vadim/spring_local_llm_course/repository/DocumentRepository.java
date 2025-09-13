package ru.vadim.spring_local_llm_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadim.spring_local_llm_course.model.LoadedDocument;

public interface DocumentRepository extends JpaRepository<LoadedDocument, Long> {
    boolean existsByFilenameAndContentHash(String filename, String contentHash);
}
