package ru.pasvitas.teaching.botui.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pasvitas.teaching.botui.model.Command;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommandFormModel {

    private String id;

    private String commandName;

    private String sourceNames;

    private String commandAnswer;

    public static CommandFormModel fromBackendModel(Command command) {
        return CommandFormModel.builder()
                .id(command.getId().toString())
                .commandAnswer(command.getCommandAnswer())
                .commandName(command.getCommandName())
                .sourceNames(command.getSourceNames())
                .build();
    }

    public Command toBackendModel() {
        return Command.builder()
                .id(id.isEmpty() ? null : Long.parseLong(this.id))
                .commandName(this.commandName)
                .commandAnswer(this.commandAnswer)
                .sourceNames(this.sourceNames)
                .scriptName("")
                .isScript(false)
                .build();
    }
}
