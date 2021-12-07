package ru.pasvitas.teaching.botui.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.pasvitas.teaching.botui.api.CommandApiClient;
import ru.pasvitas.teaching.botui.exception.CommandChangeException;
import ru.pasvitas.teaching.botui.model.Command;

@RequiredArgsConstructor
@Service
public class CommandServiceImpl implements CommandService {

    public final CommandApiClient commandApiClient;

    @Override
    public List<Command> getAllCommands() {
        ResponseEntity<List<Command>> commands = commandApiClient.getCommandList();
        if (commands.getStatusCode().equals(HttpStatus.OK) && commands.hasBody()) {
            return commands.getBody();
        }
        else {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Command> getCommand(long id) {
        ResponseEntity<Command> command = commandApiClient.getCommand(id);
        if (command.getStatusCode().equals(HttpStatus.OK) && command.hasBody()) {
            return Optional.ofNullable(command.getBody());
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public void createNewCommand(Command command) throws CommandChangeException {
        ResponseEntity<Void> commandResponse = commandApiClient.createNewCommand(command);
        if (!commandResponse.getStatusCode().equals(HttpStatus.OK)) {
            throw new CommandChangeException(CommandChangeException.ChangeType.CREATE, command.getCommandName());
        }
    }

    @Override
    public void updateCommand(Command command) throws CommandChangeException {
        ResponseEntity<Void> commandResponse = commandApiClient.updateCommand(command.getId(), command);
        if (!commandResponse.getStatusCode().equals(HttpStatus.OK)) {
            throw new CommandChangeException(CommandChangeException.ChangeType.UPDATE, command.getCommandName());
        }
    }

    @Override
    public void deleteCommand(long commandId) throws CommandChangeException {
        ResponseEntity<Void> commandResponse = commandApiClient.deleteCommand(commandId);
        if (!commandResponse.getStatusCode().equals(HttpStatus.OK)) {
            throw new CommandChangeException(CommandChangeException.ChangeType.DELETE, commandId + "");
        }
    }
}
