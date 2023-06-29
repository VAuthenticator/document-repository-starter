package com.vauthenticator.document.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.vauthenticator.document.repository.SafeOperationExecutor.contentTypeFor;
import static com.vauthenticator.document.repository.SafeOperationExecutor.readAllBytesFrom;
import static java.nio.file.Files.probeContentType;
import static java.nio.file.Files.readAllBytes;

public class FileSystemDocumentRepository implements DocumentRepository {

    private final String basePath;

    public FileSystemDocumentRepository(String basePath) {
        this.basePath = basePath;
    }


    @Override
    public Document loadDocument(String type, String path) {

        var filePath = Paths.get(basePath, documentKeyFor(type, path));
        var contentType = contentTypeFor(filePath);
        var content = readAllBytesFrom(filePath);
        return new Document(path, contentType, content);
    }


    @Override
    public void saveDocument(String type, Document document) {
        var filePath = Paths.get(basePath, documentKeyFor(type, document.path()));
        try {
            Files.createFile(filePath);
            Files.write(filePath, document.content());
        } catch (Exception e) {

        }
    }


}
