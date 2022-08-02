package ru.z3r0ing.telegrambots.handlers.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.z3r0ing.telegrambots.annotations.BotHandler;
import ru.z3r0ing.telegrambots.handlers.UpdateHandler;
import ru.z3r0ing.telegrambots.handlers.commands.Command;
import ru.z3r0ing.telegrambots.handlers.commands.CommandHandler;
import ru.z3r0ing.telegrambots.properties.TelegramBotProperties;
import ru.z3r0ing.telegrambots.tgapi.MessageType;
import ru.z3r0ing.telegrambots.tgapi.UpdateType;
import ru.z3r0ing.telegrambots.tgapi.objects.UpdateContext;
import ru.z3r0ing.telegrambots.tgapi.utils.TelegramMessages;

import javax.annotation.Nullable;
import java.util.List;

/**
 * UpdateHandler for {@link UpdateType#MESSAGE}
 *
 * @author Z3R0ing
 */
@ConditionalOnMissingClass
@BotHandler
public class MessageHandler implements UpdateHandler {

    private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);

    private final List<MessageTypeHandler> messageTypeHandlerList;
    private final List<CommandHandler> commandHandlerList;
    private final TelegramBotProperties telegramBotProperties;

    public MessageHandler(List<MessageTypeHandler> messageTypeHandlerList,
                          List<CommandHandler> commandHandlerList,
                          TelegramBotProperties telegramBotProperties) {
        this.messageTypeHandlerList = messageTypeHandlerList;
        this.commandHandlerList = commandHandlerList;
        this.telegramBotProperties = telegramBotProperties;
    }

    @Nullable
    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        Message message = update.getMessage();
        if (message == null) {
            log.error("message is null in MessageHandler");
            return null;
        }
        MessageType messageType = TelegramMessages.getMessageType(message);

        if (messageType == MessageType.COMMAND) {
            return handleCommand(message, update);
        }

        for (MessageTypeHandler messageTypeHandler : messageTypeHandlerList) {
            if (messageTypeHandler.getHandleableMessageType() == messageType) {
                return messageTypeHandler.handleMessage(message, new UpdateContext(message.getFrom(), message.getChat(), update));
            }
        }

        return null;
    }

    @Override
    public UpdateType getHandleableUpdateType() {
        return UpdateType.MESSAGE;
    }

    private BotApiMethod<?> handleCommand(Message message, Update update) {
        // get command from message text
        Command command = new Command(message.getText());
        // if mentioned not this bot - skip
        if (command.getMention() != null && !command.getMention().equals(telegramBotProperties.getUsername())) {
            return null;
        }

        for (CommandHandler commandHandler : commandHandlerList) {
            if (commandHandler.getCommandName().equals(command.getCommand())) {
                return commandHandler.handleCommandMessage(message,
                        new UpdateContext(message.getFrom(), message.getChat(), update, command.getArgs()));
            }
        }

        return null;
    }


}
