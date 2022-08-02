package ru.z3r0ing.telegrambots.handlers.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.z3r0ing.telegrambots.handlers.commands.CommandHandler;
import ru.z3r0ing.telegrambots.properties.TelegramBotProperties;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EnableConfigurationProperties(TelegramBotProperties.class)
class MessageHandlerIntegrationTest {

    @Configuration
    @EnableConfigurationProperties(TelegramBotProperties.class)
    static class MessageHandlerTestConfiguration {
        @Bean
        CustomMessageHandler customMessageHandler(List<MessageTypeHandler> messageTypeHandlerList,
                                                  List<CommandHandler> commandHandlerList,
                                                  TelegramBotProperties telegramBotProperties) {
            return new CustomMessageHandler(messageTypeHandlerList,
                                            commandHandlerList,
                                            telegramBotProperties);
        }

        static class CustomMessageHandler extends MessageHandler {
            public CustomMessageHandler(List<MessageTypeHandler> messageTypeHandlerList,
                                        List<CommandHandler> commandHandlerList,
                                        TelegramBotProperties telegramBotProperties) {
                super(messageTypeHandlerList, commandHandlerList, telegramBotProperties);
            }
        }
    }

    @Autowired
    ApplicationContext context;

    @Test
    void customMessageHandleTest() {
        Map<String, MessageHandler> beans = context.getBeansOfType(MessageHandler.class);
        assertEquals(1, beans.size());
        assertDoesNotThrow(() -> beans.get("customMessageHandler"));
    }
}