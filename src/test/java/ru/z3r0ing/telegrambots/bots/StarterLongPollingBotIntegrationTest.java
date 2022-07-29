package ru.z3r0ing.telegrambots.bots;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.z3r0ing.telegrambots.config.TelegramBotConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TelegramBotConfiguration.class)
class StarterLongPollingBotIntegrationTest {

    @Autowired
    private StarterLongPollingBot starterLongPollingBot;

    @BeforeEach
    void setUp() {
        Assumptions.assumeTrue(starterLongPollingBot.getBotToken() != null
                && !starterLongPollingBot.getBotToken().isBlank(),
                "No bot token");
    }

    @Test
    void execute() {
        SendMessage sendMessage = new SendMessage("1", "test");
        try {
            starterLongPollingBot.execute(sendMessage);
        } catch (TelegramApiRequestException e) {
            Assertions.assertNotEquals(401, e.getErrorCode(), "Wrong bot token");
            Assertions.assertEquals(400, e.getErrorCode());
        } catch (TelegramApiException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void getMe() {
        GetMe getMe = new GetMe();
        try {
            User me = starterLongPollingBot.execute(getMe);
            Assertions.assertNotNull(me);
            Assertions.assertTrue(me.getIsBot());
        } catch (TelegramApiException e) {
            Assertions.fail(e);
        }
    }
}