package ru.z3r0ing.telegrambots.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.z3r0ing.telegrambots.bots.StarterLongPollingBot;
import ru.z3r0ing.telegrambots.handlers.UpdateHandler;
import ru.z3r0ing.telegrambots.properties.TelegramBotProperties;

import java.util.List;

/**
 * Configured Telegram Bot
 *
 * @author Z3R0ing
 */
@Configuration
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramBotConfiguration {

    private final TelegramBotProperties telegramBotProperties;

    public TelegramBotConfiguration(TelegramBotProperties telegramBotProperties) {
        this.telegramBotProperties = telegramBotProperties;
    }

    @Bean
    public StarterLongPollingBot longPollingBot(List<UpdateHandler> updateHandlers) throws TelegramApiException {
        StarterLongPollingBot longPollingBot = new StarterLongPollingBot(updateHandlers);
        longPollingBot.setBotUsername(telegramBotProperties.getUsername());
        longPollingBot.setBotToken(telegramBotProperties.getToken());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(longPollingBot);
        return longPollingBot;
    }

}
