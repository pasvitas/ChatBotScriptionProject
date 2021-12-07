package ru.pasvitas.teaching.botui.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Command {

    @JsonProperty("commandId")
    private Long id;

    @JsonProperty("commandName")
    private String commandName;

    @JsonProperty("sourceNames")
    private String sourceNames;

    @JsonProperty("isScript")
    private boolean isScript;

    @JsonProperty("commandAnswer")
    private String commandAnswer;

    @JsonProperty("scriptName")
    private String scriptName;
}
