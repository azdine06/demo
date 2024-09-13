package com.ids.patienthub.file.dao;

import com.ids.patienthub.file.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Document, String> {
}