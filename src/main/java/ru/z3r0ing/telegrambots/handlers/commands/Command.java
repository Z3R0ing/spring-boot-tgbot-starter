package ru.z3r0ing.telegrambots.handlers.commands;

/**
 * Class for more useful command representation
 *
 * @author Z3R0ing
 */
public class Command {

    private String command;
    private String mention;
    private String[] args;

    public Command(String command, String mention, String[] args) {
        this.command = command;
        this.mention = mention;
        this.args = args;
    }

    public Command(String messageText) {
        String[] commandWithArgs = messageText.split(" ", 2);
        // get command from message text, remove "/"
        String command = commandWithArgs[0].replace("/", "");
        // get command args
        this.args = commandWithArgs.length > 1 ? commandWithArgs[1].split(" ") : null;

        // check if it is command with bot mention
        String[] commandWithMention = command.split("@", 2);
        this.command = commandWithMention[0];
        this.mention = commandWithMention.length > 1 ? commandWithMention[1] : null;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
