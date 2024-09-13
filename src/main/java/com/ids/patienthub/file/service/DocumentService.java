package com.ids.patienthub.file.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import com.ids.patienthub.file.config.StorageProperties;
import com.ids.patienthub.file.dao.DocumentRepository;
import com.ids.patienthub.file.dto.DocumentDto;
import com.ids.patienthub.file.entity.Document;
import com.ids.patienthub.file.exception.StorageException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DocumentService {

	private final StorageProperties storageProperties;
	private final DocumentRepository documentRepository;

	private Path rootLocation;

	/**
	 * Initializes the storage location based on the configured properties.
	 * @throws StorageException if the storage location is invalid or cannot be created.
	 */
	@PostConstruct
	public void init() {
		if (!StringUtils.hasText(storageProperties.getLocation())) {
			throw new StorageException("File upload location can not be Empty.");
		}
		try {
			this.rootLocation = Paths.get(storageProperties.getLocation());
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException(e.getMessage(), e);
		}
	}

	/**
	 * Stores a file in the configured storage location.
	 *
	 * @param file The file to store.
	 * @return The ID of the stored document.
	 * @throws StorageException if the file is empty or cannot be stored.
	 */
	public String store(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new StorageException("Failed to store empty file.");
		}
		try {
			Document document = documentRepository.save(Document.builder()
					.filename(file.getOriginalFilename())
					.contentType(file.getContentType())
					.size(file.getSize())
					.build());

			Path destinationFile = this.rootLocation.resolve(document.getId()).normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException("Cannot store file outside current directory.");
			}
			file.transferTo(destinationFile);
			return document.getId();
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	Path load(String filename) {
		return rootLocation.resolve(filename);
	}




	public void delete(String id) {
		//TODO il ne faut pas oublier de supprimer le document
		documentRepository.deleteById(id);
	}

}