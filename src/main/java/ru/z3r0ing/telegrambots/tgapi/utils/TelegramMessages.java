package ru.z3r0ing.telegrambots.tgapi.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.z3r0ing.telegrambots.tgapi.MessageType;

/**
 * Utility class for telegram Message
 *
 * @see Message
 *
 * @author Z3R0ing
 */
public final class TelegramMessages {

    private TelegramMessages() {}

    /**
     * @param message received {@link Message} instance
     * @return what type of message
     */
    public static MessageType getMessageType(Message message) {
        if (message.getText() != null) {
            if (message.getText().startsWith("/")) {
                return MessageType.COMMAND;
            } else {
                return MessageType.TEXT;
            }
        }
        if (message.getAnimation() != null) {
            return MessageType.ANIMATION;
        }
        if (message.getAudio() != null) {
            return MessageType.AUDIO;
        }
        if (message.getDocument() != null) {
            return MessageType.DOCUMENT;
        }
        if (message.getPhoto() != null) {
            return MessageType.PHOTO;
        }
        if (message.getSticker() != null) {
            return MessageType.STICKER;
        }
        if (message.getVideo() != null) {
            return MessageType.VIDEO;
        }
        if (message.getVoice() != null) {
            return MessageType.VOICE;
        }
        if (message.getContact() != null) {
            return MessageType.CONTACT;
        }
        if (message.getDice() != null) {
            return MessageType.DICE;
        }
        if (message.getGame() != null) {
            return MessageType.GAME;
        }
        if (message.getPoll() != null) {
            return MessageType.POLL;
        }
        if (message.getVenue() != null) {
            return MessageType.VENUE;
        }
        if (message.getLocation() != null) {
            return MessageType.LOCATION;
        }
        if (message.getNewChatMembers() != null) {
            return MessageType.NEW_CHAT_MEMBERS;
        }
        if (message.getLeftChatMember() != null) {
            return MessageType.LEFT_CHAT_MEMBER;
        }
        if (message.getPinnedMessage() != null) {
            return MessageType.PINNED_MESSAGE;
        }
        return MessageType.OTHER;
    }

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
