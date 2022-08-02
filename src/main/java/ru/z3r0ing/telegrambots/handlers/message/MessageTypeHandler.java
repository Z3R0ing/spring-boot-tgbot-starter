package ru.z3r0ing.telegrambots.handlers.message;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.z3r0ing.telegrambots.tgapi.MessageType;
import ru.z3r0ing.telegrambots.tgapi.objects.UpdateContext;

import javax.annotation.Nullable;

/**
 * Interface of handlers for different type of Message
 *
 * @author Z3R0ing
 */
public interface MessageTypeHandler {

    /**
     * Handles received message
     * @param message result of {@link Update#getMessage}
     * @param updateContext context for received update
     * @return {@link BotApiMethod} as result. Can be null
     * @apiNote should use with updates same type as from {@link #getHandleableMessageType()}
     */
    @Nullable
    BotApiMethod<?> handleMessage(Message message, UpdateContext updateContext);

    /**
     * @return which type of message this handler can handle
     * @apiNote register more than one handler with same UpdateType in context can produce errors
     */
    MessageType getHandleableMessageType();

}
