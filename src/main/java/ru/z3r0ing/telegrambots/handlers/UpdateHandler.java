package ru.z3r0ing.telegrambots.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.z3r0ing.telegrambots.tgapi.UpdateType;

import javax.annotation.Nullable;

/**
 * Interface of handlers for different type of Update
 *
 * @author Z3R0ing
 */
public interface UpdateHandler {

    /**
     * Handles received update
     * @param update received update
     * @return {@link BotApiMethod} as result. Can be null
     * @apiNote should use with updates same type as from {@link #getHandleableUpdateType()}
     */
    @Nullable
    BotApiMethod<?> handleUpdate(Update update);

    /**
     * @return which type of update this handler can handle
     * @apiNote register more than one handler with same UpdateType in context can produce errors
     */
    UpdateType getHandleableUpdateType();

}
