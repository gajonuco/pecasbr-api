/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.service.IUploadService
 *  org.springframework.web.multipart.MultipartFile
 */
package com.gajonuco.pecasbr.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
    public String uploadFile(MultipartFile var1);
}

