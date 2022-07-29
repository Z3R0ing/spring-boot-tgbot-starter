package ru.z3r0ing.telegrambots.bots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StarterLongPollingBot extends TelegramLongPollingBot {

    public StarterLongPollingBot() {
    }

    private static final Logger log = LoggerFactory.getLogger(StarterLongPollingBot.class);

    private String botUsername;
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // do nothing
    }
}
