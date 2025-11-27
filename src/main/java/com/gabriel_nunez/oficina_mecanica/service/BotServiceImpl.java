package com.gabriel_nunez.oficina_mecanica.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotServiceImpl implements IBotService {

    @Value("${telegrambot_chat_id}")
	private long chatId;

    @Value("${telegrambot_url}")
	private String botUrl;
	
	@Value("${telegrambot_msg}")
	private String message;

    private final HttpClient httpClient = HttpClient.newBuilder()
                                            .version(HttpClient.Version.HTTP_2)
                                            .connectTimeout(Duration.ofSeconds(5)).build(); 


	@Override
	public boolean sendBotMessage(String userMessage) {
		try {
			String json = "{ " + "      \"chat_id\": " + chatId + "," + "       \"text\" : \"" + message.replace("%PED%",userMessage) + "\"" + "}";

			HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
					.uri(URI.create(botUrl))
					.setHeader("User-Agent", "Java 21 HttpClient Bot")
					.header("Content-type", "application/json")
					.build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

}
