package com.gabriel_nunez.oficina_mecanica.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

    public String uploadFile(MultipartFile arquivo);
}