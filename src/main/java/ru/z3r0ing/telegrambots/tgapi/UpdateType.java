package ru.z3r0ing.telegrambots.tgapi;

/**
 * Indicate what Update contains
 *
 * @see org.telegram.telegrambots.meta.api.objects.Update
 *
 * @author Z3R0ing
 */
public enum UpdateType {
    MESSAGE,
    EDITED_MESSAGE,
    CHANNEL_POST,
    EDITED_CHANNEL_POST,
    INLINE_QUERY,
    CHOSEN_INLINE_RESULT,
    CALLBACK_QUERY,
    SHIPPING_QUERY,
    PRE_CHECKOUT_QUERY,
    POLL,
    POLL_ANSWER,
    MY_CHAT_MEMBER,
    CHAT_MEMBER,
    CHAT_JOIN_REQUEST,
    NONE
}
