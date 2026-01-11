package com.gabriel_nunez.oficina_mecanica.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            InputStream serviceAccount;

            // 1️⃣ PRODUÇÃO — variável de ambiente
            String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

            if (credentialsPath != null && !credentialsPath.isBlank()) {
                serviceAccount = new FileInputStream(credentialsPath);
            } 
            // 2️⃣ LOCAL — classpath (resources)
            else {
                serviceAccount = new ClassPathResource(
                    "firebase/service-account.json"
                ).getInputStream();
            }

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase inicializado com sucesso!");
            }

        } catch (Exception e) {
            System.err.println("❌ Erro ao inicializar Firebase:");
            e.printStackTrace();
        }
    }
}
