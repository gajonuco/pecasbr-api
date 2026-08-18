/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.config.FirebaseConfig
 *  com.google.auth.oauth2.GoogleCredentials
 *  com.google.firebase.FirebaseApp
 *  com.google.firebase.FirebaseOptions
 *  javax.annotation.PostConstruct
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.io.ClassPathResource
 */
package com.gajonuco.pecasbr.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void initialize() {
        try {
            String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
            InputStream serviceAccount = credentialsPath != null && !credentialsPath.isBlank() ? new FileInputStream(credentialsPath) : new ClassPathResource("firebase/service-account.json").getInputStream();
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream((InputStream)serviceAccount)).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp((FirebaseOptions)options);
                System.out.println("\u2705 Firebase inicializado com sucesso!");
            }
        }
        catch (Exception e) {
            System.err.println("\u274c Erro ao inicializar Firebase: " + e.getMessage());
            System.err.println("\u274c Causa: " + String.valueOf(e.getCause()));
            e.printStackTrace();
            throw new RuntimeException("Firebase falhou ao inicializar", e);
        }
    }
}

