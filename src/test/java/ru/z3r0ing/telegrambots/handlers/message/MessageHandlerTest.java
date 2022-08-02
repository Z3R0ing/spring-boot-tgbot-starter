package ru.z3r0ing.telegrambots.handlers.message;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.z3r0ing.telegrambots.handlers.commands.CommandHandler;
import ru.z3r0ing.telegrambots.properties.TelegramBotProperties;
import ru.z3r0ing.telegrambots.tgapi.MessageType;
import ru.z3r0ing.telegrambots.tgapi.objects.UpdateContext;
import ru.z3r0ing.telegrambots.tgapi.utils.TelegramMessages;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageHandlerTest {

    static TelegramBotProperties telegramBotProperties = new TelegramBotProperties();
    static String TEST_USERNAME = "test_bot";
    static String TEST_MESSAGE = "test message";
    static String TEST_MESSAGE_WITH_COMMAND = "/start@test_bot bla bla bla";
    static String HELLO_MESSAGE = "Hello!";

    @BeforeAll
    static void setUp() {
        telegramBotProperties = new TelegramBotProperties();
        telegramBotProperties.setUsername(TEST_USERNAME);
    }

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
        message.setText(TEST_MESSAGE);
        message.setChat(chat);
        Update update = new Update();
        update.setMessage(message);
        MessageHandler messageHandler = new MessageHandler(List.of(new MessageTextHandler()),
                                                            Collections.emptyList(),
                                                            telegramBotProperties);
        BotApiMethod<?> handleResult = messageHandler.handleUpdate(update);
        assertTrue(handleResult instanceof SendMessage);
        String resultText = ((SendMessage) handleResult).getText();
        assertEquals(resultText, TEST_MESSAGE);
    }

    private static class StartCommandHandler implements CommandHandler {
        @Nullable
        @Override
        public BotApiMethod<?> handleCommandMessage(Message message, UpdateContext updateContext) {
            return TelegramMessages.createPlainTextMessage(updateContext.getChat(), HELLO_MESSAGE);
        }

        @Override
        public String getCommandName() {
            return "start";
        }
    }

    @Test
    void handleUpdateWithCommand() {
        Chat chat = new Chat();
        chat.setId(1L);
        Message message = new Message();
        message.setText(TEST_MESSAGE_WITH_COMMAND);
        message.setChat(chat);
        Update update = new Update();
        update.setMessage(message);
        MessageHandler messageHandler = new MessageHandler(List.of(new MessageTextHandler()),
                                                            List.of(new StartCommandHandler()),
                                                            telegramBotProperties);
        BotApiMethod<?> handleResult = messageHandler.handleUpdate(update);
        assertTrue(handleResult instanceof SendMessage);
        String resultText = ((SendMessage) handleResult).getText();
        assertEquals(resultText, HELLO_MESSAGE);
    }

}