package ru.z3r0ing.telegrambots.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.z3r0ing.telegrambots.annotations.BotHandler;
import ru.z3r0ing.telegrambots.handlers.commands.CommandHandler;
import ru.z3r0ing.telegrambots.handlers.message.MessageHandler;
import ru.z3r0ing.telegrambots.handlers.message.MessageTypeHandler;
import ru.z3r0ing.telegrambots.properties.TelegramBotProperties;

import java.util.List;

@Configuration
public class UpdateHandlersConfiguration {

    @ConditionalOnMissingClass
    @Bean
    MessageHandler messageHandler(List<MessageTypeHandler> messageTypeHandlerList,
                                  List<CommandHandler> commandHandlerList,
                                  TelegramBotProperties telegramBotProperties) {
        return new MessageHandler(messageTypeHandlerList, commandHandlerList, telegramBotProperties);
    }
}
