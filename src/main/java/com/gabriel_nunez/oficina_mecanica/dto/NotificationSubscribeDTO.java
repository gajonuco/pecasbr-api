package com.gabriel_nunez.oficina_mecanica.dto;

public class NotificationSubscribeDTO {

    private String token;
    private String topic; // ⚠️ ADICIONADO: campo topic

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}