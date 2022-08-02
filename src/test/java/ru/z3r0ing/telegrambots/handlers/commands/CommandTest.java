package ru.z3r0ing.telegrambots.handlers.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void fromMessageTextConstructor() {
        Command command = new Command("/command@test_bot bla bla bla");
        assertEquals("command", command.getCommand());
        assertEquals("test_bot", command.getMention());
        assertArrayEquals(new String[] {"bla", "bla", "bla"}, command.getArgs());

        command = new Command("/command@test_bot");
        assertEquals("command", command.getCommand());
        assertEquals("test_bot", command.getMention());
        assertNull(command.getArgs());

        command = new Command("/command bla bla bla");
        assertEquals("command", command.getCommand());
        assertNull(command.getMention());
        assertArrayEquals(new String[] {"bla", "bla", "bla"}, command.getArgs());

        command = new Command("/command");
        assertEquals("command", command.getCommand());
        assertNull(command.getMention());
        assertNull(command.getArgs());
    }

}