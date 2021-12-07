package ru.pasvitas.teaching.botui.service;

import java.util.List;
import java.util.Optional;
import ru.pasvitas.teaching.botui.exception.CommandChangeException;
import ru.pasvitas.teaching.botui.model.Command;

public interface CommandService {
    List<Command> getAllCommands();
    Optional<Command> getCommand(long id);
    void createNewCommand(Command command) throws CommandChangeException;
    void updateCommand(Command command) throws CommandChangeException;
    void deleteCommand(long id) throws CommandChangeException;
}
