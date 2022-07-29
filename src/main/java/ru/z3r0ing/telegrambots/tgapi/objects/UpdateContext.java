package ru.z3r0ing.telegrambots.tgapi.objects;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.z3r0ing.telegrambots.tgapi.utils.TelegramUpdates;

import java.util.Objects;

/**
 * It is wrapper class for provides quick access to {@link Chat}, {@link User} and {@link Update}
 * <p>
 * Very similar to MessageContext from telegrambots-abilities
 * @see <a href="https://github.com/rubenlagus/TelegramBots/blob/master/telegrambots-abilities/src/main/java/org/telegram/abilitybots/api/objects/MessageContext.java">
 *     MessageContext
 *     </a>
 * @author Z3R0ing
 */
public class UpdateContext {

    private final User user;
    private final Chat chat;
    private final Update update;
    private final String[] params;

    public UpdateContext(User user, Chat chat, Update update) {
        this.user = user;
        this.chat = chat;
        this.update = update;
        this.params = null;
    }

    public UpdateContext(User user, Chat chat, Update update, String[] params) {
        this.user = user;
        this.chat = chat;
        this.update = update;
        this.params = params;
    }

    public User getUser() {
        return user;
    }

    public Chat getChat() {
        return chat;
    }

    public Update getUpdate() {
        return update;
    }

    public String[] getParams() {
        return params;
    }

    public boolean hasParams() {
        return params != null && params.length > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateContext that = (UpdateContext) o;
        return Objects.equals(user, that.user)
                && Objects.equals(chat, that.chat)
                && Objects.equals(update, that.update);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, chat, update);
    }

    @Override
    public String toString() {
        return "UpdateContext{" +
                "user=" + user +
                ", chatId=" + chat +
                ", update=" + update +
                '}';
    }

    public static UpdateContext createWithoutParameters(Update update, int paramsLength) {
        return TelegramUpdates.createUpdateContext(update, paramsLength);
    }

}
