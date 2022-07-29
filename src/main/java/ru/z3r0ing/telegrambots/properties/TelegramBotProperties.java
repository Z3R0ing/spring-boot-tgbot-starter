package ru.z3r0ing.telegrambots.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@ConfigurationProperties(prefix = "z3r0ing.telegrambots.bot")
public class TelegramBotProperties {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private String username;
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
