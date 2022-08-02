# z3r0ing-spring-boot-tgbot-starter

Spring Boot Starter for faster creation bots based on [org.telegram:telegrambots](https://github.com/rubenlagus/TelegramBots).

## Idea

The main idea of the project is adding update processing logic for bots without editing higher level code of the logic.
Write only new code with specific for your bot logic, without editing other code.
All global checks, "if", switch, routing logic to specific logic are already implemented.

## Dependency

1. Clone repository

2. Run `./gradlew build publishToMavenLocal`

3. Add dependency in your project

```groovy
// build.gradle
implementation group: 'ru.z3r0ing.telegrambots',
        name: 'z3r0ing-spring-boot-tgbot-starter',
        version: '0.1.0'
// OR
implementation 'ru.z3r0ing.telegrambots:z3r0ing-spring-boot-tgbot-starter:0.1.0'
```

```xml
<!-- pom.xml -->
<dependency>
    <groupId>ru.z3r0ing.telegrambots</groupId>
    <artifactId>z3r0ing-spring-boot-tgbot-starter</artifactId>
    <version>0.1.0</version>
</dependency>
```

4. Add `z3r0ing.telegrambots.bot.username` and `z3r0ing.telegrambots.bot.token` properties in application.properties
   or add them to environment

## Usage

**N.B.** Starter supports only long polling bot now.



### Types

This implementation of [Rubenlagus telegrambots](https://github.com/rubenlagus/TelegramBots)
has own types for Telegram API:

1. **UpdateType**: Indicate what Update contains.

```java
enum UpdateType {
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
```

2. **MessageType**: Indicate what Message contains.

```java
enum MessageType {
    TEXT,
    COMMAND,
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
```

3. **UpdateContext**: Wrapper class for **Update** provides quick access to Chat, User and Update. Very similar to
   MessageContext from telegrambots-abilities [MessageContext](https://github.com/rubenlagus/TelegramBots/blob/master/telegrambots-abilities/src/main/java/org/telegram/abilitybots/api/objects/MessageContext.java)

### Update Handlers

When bot receives an Update it iterates over list of **UpdateHandler** implementations to find handler
with same **UpdateType** as **Update** is. Then, it passes the update to such **UpdateHandler** and gets result of
handling as **BotApiMethod**. If result isn't null, it executes result.

```java
// src/main/java/ru/z3r0ing/telegrambots/bots/StarterLongPollingBot.java

@Override
public void onUpdateReceived(Update update) {
    UpdateType updateType = TelegramUpdates.getUpdateType(update);

    BotApiMethod<?> handlingResult = null;
    for (UpdateHandler updateHandler : updateHandlers) {
        if (updateHandler.getHandleableUpdateType() == updateType) {
            handlingResult = updateHandler.handleUpdate(update);
            break;
        }
    }

    if (handlingResult != null) {
        try {
            execute(handlingResult);
            log.info(handlingResult.toString());
        } catch (TelegramApiException e) {
            log.error("Excption while execute handling result", e);
        }
    }
}
```

#### Add new Update Handler

If you want to add **UpdateHandler** for some **UpdateType** just make class implements **UpdateHandler** and
add annotation **@BotHandler** to create the bean for this handler.
For example, for **UpdateType._CHANNEL_POST_**:

```java
@BotHandler
public class ChannelPostHandler implements UpdateHandler {

    @Nullable
    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        // do smth
    }

    @Override
    public UpdateType getHandleableUpdateType() {
        return UpdateType.CHANNEL_POST;
    }
}
```

### Message Handler

For **UpdateType._MESSAGE_** starter has base implementation of **UpdateHandler** - **MessageHandler**.
**MessageHandler** gets **MessageType** of received **Message** and passes this **Message** to suitable implementation
of **MessageTypeHandler**, but if **MessageType** is command, it passes **Message** to suitable implementation
of **CommandHandler** which has the same command as in message text.

```java
// src/main/java/ru/z3r0ing/telegrambots/handlers/message/MessageHandler.java

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
```

#### Add message content handlers

**Message** can contain different content. You can create handlers for any content type of **Message**. Just make class
implements **MessageTypeHandler** and add annotation **@BotHandler** to create the bean for this handler.
For example, handler of sticker message.

```java
@BotHandler
public class StickerMessageHandler implements MessageTypeHandler {

    @Nullable
    @Override
    public BotApiMethod<?> handleMessage(Message message, UpdateContext updateContext) {
        // do smth
    }

    @Override
    public UpdateType getHandleableUpdateType() {
        return UpdateType.CHANNEL_POST;
    }
}
```

#### Add command handler
**Message** can contain commands for a bot. You can create handlers for commands. Just make class implements
**CommandHandler** and add annotation **@BotHandler** to create the bean for this handler.
For example, handler of start command:

```java
@BotHandler
public class StartCommandHandler implements CommandHandler {
    @Override
    public BotApiMethod<?> handleCommandMessage(Message message, UpdateContext updateContext) {
        return TelegramMessages.createPlainTextMessage(updateContext.getChat(), "Hello!");
    }

    @Override
    public String getCommandName() {
        return "start";
    }
}
```

## TODO

1. Executing BotApiMethod without receiving Update