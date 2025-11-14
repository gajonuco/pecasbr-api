package com.gabriel_nunez.oficina_mecanica.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadServiceImpl implements IUploadService {

    //private static final String UPLOAD_DIR = "/home/user/Área de trabalho/projeto/images"; 
    private static final String UPLOAD_DIR = "/var/www/projetoreal.dev.br/browser/assets/img/";

    // pasta pública para imagens (fora do Angular)

    @Override
    public String uploadFile(MultipartFile arquivo) {
        try {
            System.out.println("DEBUG: " + arquivo.getOriginalFilename());

            // cria diretório se não existir
            Path diretorio = Paths.get(UPLOAD_DIR);
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }

            // define o destino do arquivo
            Path path = diretorio.resolve(arquivo.getOriginalFilename());

            // copia o arquivo para a pasta de images
            Files.copy(arquivo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("DEBUG - Arquivo copiado em: " + path);

            // retorna só o nome do arquivo, não o caminho físico
            return arquivo.getOriginalFilename();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
