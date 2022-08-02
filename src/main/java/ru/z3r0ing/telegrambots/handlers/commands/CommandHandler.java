package ru.z3r0ing.telegrambots.handlers.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.z3r0ing.telegrambots.tgapi.objects.UpdateContext;

import javax.annotation.Nullable;

public interface CommandHandler {

    /**
     * Handles received message with command
     * @param message result of {@link Update#getMessage}
     * @param updateContext context for received update
     * @return {@link BotApiMethod} as result. Can be null
     * @apiNote should use with updates with command same as from {@link #getCommandName()}
     */
    @Nullable
    BotApiMethod<?> handleCommandMessage(Message message, UpdateContext updateContext);

    /**
     * @return Text of the command. 1-32 characters. Can contain only lowercase English letters, digits and underscores.
     */
    String getCommandName();

}
