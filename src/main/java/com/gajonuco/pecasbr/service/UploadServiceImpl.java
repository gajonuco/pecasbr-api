package com.gajonuco.pecasbr.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadServiceImpl implements IUploadService {
        // Uso local
        private final Path diretorio;

    public UploadServiceImpl(@Value("${app.images-dir}") String imagesDir) {
        this.diretorio = Paths.get(imagesDir);
    }

    @Override
    public String uploadFile(MultipartFile arquivo) {
        try {
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }
            String nome = Paths.get(arquivo.getOriginalFilename()).getFileName().toString();
            Files.copy(arquivo.getInputStream(), diretorio.resolve(nome), StandardCopyOption.REPLACE_EXISTING);
            return nome;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
