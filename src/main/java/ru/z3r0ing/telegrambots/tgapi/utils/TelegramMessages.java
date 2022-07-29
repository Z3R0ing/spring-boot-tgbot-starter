package ru.z3r0ing.telegrambots.tgapi.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;

/**
 * Utility class for telegram text message
 *
 * @author Z3R0ing
 */
public final class TelegramMessages {

    private TelegramMessages() {}

    /**
     * Create plain text message
     * @param chat message receiver chat
     * @param text text of the message
     * @return SendMessage with chatId and text
     */
    public static SendMessage createPlainTextMessage(Chat chat, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());
        sendMessage.setText(text);
        return sendMessage;
    }

    /**
     * Create plain text message with text in MarkdownV2
     * @param chat message receiver chat
     * @param text text of the message in MarkdownV2
     * @return SendMessage with chatId and formatted text
     */
    public static SendMessage createPlainMarkdownTextMessage(Chat chat, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());
        sendMessage.setText(escapeSymbolsForMarkdownV2(text));
        sendMessage.setParseMode("MarkdownV2");
        return sendMessage;
    }

    private static String escapeSymbolsForMarkdownV2(String rawString) {
        return rawString.replace(".", "\\.")
                .replace("!", "\\!")
                .replace("-", "\\-");
    }

}
