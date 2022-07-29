package ru.z3r0ing.telegrambots.tgapi.utils;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.z3r0ing.telegrambots.tgapi.UpdateType;
import ru.z3r0ing.telegrambots.tgapi.objects.UpdateContext;

/**
 * Utils class for working with {@link Update} class
 *
 * @author Z3R0ing
 */
public final class TelegramUpdates {

    private TelegramUpdates() {}

    /**
     * @param update received {@link Update} instance
     * @return what type of update
     */
    public static UpdateType getUpdateType(Update update) {
        if (update.getMessage() != null) {
            return UpdateType.MESSAGE;
        }
        if (update.getEditedMessage() != null) {
            return UpdateType.EDITED_MESSAGE;
        }
        if (update.getChannelPost() != null) {
            return UpdateType.CHANNEL_POST;
        }
        if (update.getEditedChannelPost() != null) {
            return UpdateType.EDITED_CHANNEL_POST;
        }
        if (update.getInlineQuery() != null) {
            return UpdateType.INLINE_QUERY;
        }
        if (update.getChosenInlineQuery() != null) {
            return UpdateType.CHOSEN_INLINE_RESULT;
        }
        if (update.getCallbackQuery() != null) {
            return UpdateType.CALLBACK_QUERY;
        }
        if (update.getShippingQuery() != null) {
            return UpdateType.SHIPPING_QUERY;
        }
        if (update.getPreCheckoutQuery() != null) {
            return UpdateType.PRE_CHECKOUT_QUERY;
        }
        if (update.getPoll() != null) {
            return UpdateType.POLL;
        }
        if (update.getPollAnswer() != null) {
            return UpdateType.POLL_ANSWER;
        }
        if (update.getMyChatMember() != null) {
            return UpdateType.MY_CHAT_MEMBER;
        }
        if (update.getChatMember() != null) {
            return UpdateType.CHAT_MEMBER;
        }
        if (update.getChatJoinRequest() != null) {
            return UpdateType.CHAT_JOIN_REQUEST;
        }
        return UpdateType.NONE;
    }

    /**
     * @param update {@link Update} instance
     * @param paramsLength length of params array. If less then zero - params array is null.
     * @return {@link UpdateContext} with empty params
     */
    public static UpdateContext createUpdateContext(Update update, int paramsLength) {
        String[] params;
        if (paramsLength > 0) {
            params = new String[paramsLength];
        } else {
            params = null;
        }

        if (update.getMessage() != null) {
            return new UpdateContext(update.getMessage().getFrom(), update.getMessage().getChat(), update, params);
        }
        if (update.getEditedMessage() != null) {
            return new UpdateContext(update.getEditedMessage().getFrom(), update.getEditedMessage().getChat(), update, params);
        }
        if (update.getChannelPost() != null) {
            return new UpdateContext(update.getChannelPost().getFrom(), update.getChannelPost().getChat(), update, params);
        }
        if (update.getEditedChannelPost() != null) {
            return new UpdateContext(update.getEditedChannelPost().getFrom(), update.getEditedChannelPost().getChat(), update, params);
        }
        if (update.getInlineQuery() != null) {
            return new UpdateContext(update.getInlineQuery().getFrom(), null, update, params);
        }
        if (update.getChosenInlineQuery() != null) {
            return new UpdateContext(update.getChosenInlineQuery().getFrom(), null, update, params);
        }
        if (update.getCallbackQuery() != null) {
            return new UpdateContext(update.getCallbackQuery().getFrom(), update.getCallbackQuery().getMessage().getChat(), update, params);
        }
        if (update.getShippingQuery() != null) {
            return new UpdateContext(update.getShippingQuery().getFrom(), null, update, params);
        }
        if (update.getPreCheckoutQuery() != null) {
            return new UpdateContext(update.getPreCheckoutQuery().getFrom(), null, update, params);
        }
        if (update.getPoll() != null) {
            return new UpdateContext(null, null, update, params);
        }
        if (update.getPollAnswer() != null) {
            return new UpdateContext(update.getPollAnswer().getUser(), null, update, params);
        }
        if (update.getMyChatMember() != null) {
            return new UpdateContext(update.getMyChatMember().getFrom(), update.getMyChatMember().getChat(), update, params);
        }
        if (update.getChatMember() != null) {
            return new UpdateContext(update.getChatMember().getFrom(), update.getChatMember().getChat(), update, params);
        }
        if (update.getChatJoinRequest() != null) {
            return new UpdateContext(update.getChatJoinRequest().getUser(), update.getChatJoinRequest().getChat(), update, params);
        }
        return new UpdateContext(null, null, update, params);
    }
}
