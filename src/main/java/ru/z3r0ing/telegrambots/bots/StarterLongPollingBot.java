package ru.z3r0ing.telegrambots.bots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.z3r0ing.telegrambots.handlers.UpdateHandler;
import ru.z3r0ing.telegrambots.tgapi.UpdateType;
import ru.z3r0ing.telegrambots.tgapi.utils.TelegramUpdates;

import java.util.List;

/**
 * Long polling telegram bot
 *
 * @author Z3R0ing
 */
public class StarterLongPollingBot extends TelegramLongPollingBot {

    private final List<UpdateHandler> updateHandlers;

    public StarterLongPollingBot(List<UpdateHandler> updateHandlers) {
        this.updateHandlers = updateHandlers;
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
        UpdateType updateType = TelegramUpdates.getUpdateType(update);

        BotApiMethod<?> handlingResult = null;
        for (UpdateHandler updateHandler : updateHandlers) {
            if (updateHandler.getHandleableUpdateType() == updateType) {
                handlingResult = updateHandler.handleUpdate(update);
                break;
            }
        }

        if (handlingResult != null) {
            try {
                execute(handlingResult);
                log.info(handlingResult.toString());
            } catch (TelegramApiException e) {
                log.error("Excption while execute handling result", e);
            }
        }

    }
}
