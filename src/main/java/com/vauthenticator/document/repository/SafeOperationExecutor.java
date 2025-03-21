package com.vauthenticator.document.repository;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.probeContentType;
import static java.nio.file.Files.readAllBytes;

class SafeOperationExecutor {

    static byte[] readAllBytesFrom(Path filePath) {
        try {
            return readAllBytes(filePath);
        } catch (IOException e) {
            return new byte[0];
        }

    }

    static byte[] readAllBytesFrom(ResponseInputStream<GetObjectResponse> response) {
        try {
            return response.readAllBytes();
        } catch (IOException e) {
            return new byte[0];
        }

    }

    static String contentTypeFor(Path filePath) {
        String contentType;
        try {
            contentType = probeContentType(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contentType;
    }
}
