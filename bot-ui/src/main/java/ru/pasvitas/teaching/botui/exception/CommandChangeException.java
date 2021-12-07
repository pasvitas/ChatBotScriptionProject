package ru.pasvitas.teaching.botui.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommandChangeException extends Exception {

    private final ChangeType changeType;
    private final String commandName;

    public enum ChangeType {
        CREATE,
        UPDATE,
        DELETE;
    }
}
