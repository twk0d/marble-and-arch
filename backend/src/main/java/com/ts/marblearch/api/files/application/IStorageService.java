package com.ts.marblearch.api.files.application;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface IStorageService {
    String uploadImage(UUID propertyId, MultipartFile imageFile) throws IOException;
}
