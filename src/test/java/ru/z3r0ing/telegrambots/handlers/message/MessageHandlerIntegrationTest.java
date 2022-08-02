package ru.z3r0ing.telegrambots.handlers.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.z3r0ing.telegrambots.tgapi.MessageType;
import ru.z3r0ing.telegrambots.tgapi.objects.UpdateContext;
import ru.z3r0ing.telegrambots.tgapi.utils.TelegramMessages;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageHandlerIntegrationTest {

    /*
     * Test working of Conditional annotation
     */

    @Configuration
    static class MessageHandlerTestConfiguration {
        @Bean
        CustomMessageHandler customMessageHandler(List<MessageTypeHandler> messageTypeHandlerList) {
            return new CustomMessageHandler(messageTypeHandlerList);
        }

        static class CustomMessageHandler extends MessageHandler {
            public CustomMessageHandler(List<MessageTypeHandler> messageTypeHandlerList) {
                super(messageTypeHandlerList);
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

    /*
     * Test basic logic, without context
     */

    private static class MessageTextHandler implements MessageTypeHandler {
        /**
         * Simple echo
         */
        @Override
        public BotApiMethod<?> handleMessage(Message message, UpdateContext updateContext) {
            return TelegramMessages.createPlainTextMessage(updateContext.getChat(), message.getText());
        }
        @Override
        public MessageType getHandleableMessageType() {
            return MessageType.TEXT;
        }
    }

    @Test
    void handleUpdateWithTextMessage() {
        Chat chat = new Chat();
        chat.setId(1L);
        Message message = new Message();
        String testMessage = "test message";
        message.setText(testMessage);
        Update update = new Update();
        update.setMessage(message);
        MessageHandler messageHandler = new MessageHandler(List.of(new MessageTextHandler()));
        BotApiMethod<?> handleResult = messageHandler.handleUpdate(update);
        assertTrue(handleResult instanceof SendMessage);
        String resultText = ((SendMessage) handleResult).getText();
        assertEquals(resultText, testMessage);
    }
}