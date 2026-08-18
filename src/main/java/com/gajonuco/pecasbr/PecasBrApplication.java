/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.OficinaMecanicaApplication
 *  org.springframework.boot.SpringApplication
 *  org.springframework.boot.autoconfigure.SpringBootApplication
 *  org.springframework.scheduling.annotation.EnableScheduling
 */
package com.gajonuco.pecasbr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PecasBrApplication {
    public static void main(String[] args) {
        SpringApplication.run(PecasBrApplication.class, (String[])args);
    }
}

