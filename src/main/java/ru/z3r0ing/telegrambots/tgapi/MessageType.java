package ru.z3r0ing.telegrambots.tgapi;

/**
 * Indicate what Message contains
 *
 * @see org.telegram.telegrambots.meta.api.objects.Message
 *
 * @author Z3R0ing
 */
public enum MessageType {
    TEXT,
    ANIMATION,
    AUDIO,
    DOCUMENT,
    PHOTO,
    STICKER,
    VIDEO,
    VOICE,
    CONTACT,
    DICE,
    GAME,
    POLL,
    VENUE,
    LOCATION,
    NEW_CHAT_MEMBERS,
    LEFT_CHAT_MEMBER,
    PINNED_MESSAGE,
    OTHER
}
