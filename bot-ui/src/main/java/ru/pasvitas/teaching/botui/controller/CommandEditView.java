package ru.pasvitas.teaching.botui.controller;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pasvitas.teaching.botui.exception.CommandChangeException;
import ru.pasvitas.teaching.botui.model.Command;
import ru.pasvitas.teaching.botui.service.CommandService;

//@SpringComponent
//@Route("/commands/:commandId")
public class CommandEditView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {

    private final CommandService commandService;

    private boolean isEdited = false;

    private final Button buttonSend;
    private final Button buttonDelete;

    private final Map<FormFields, TextField> textFieldMap = new HashMap<>();

    @Autowired
    public CommandEditView(CommandService commandService) {
        this.commandService = commandService;
        makeTextFields();
        for (TextField textField : textFieldMap.values()) {
            this.add(textField);
        }
        buttonSend = new Button("Отправить", event -> sendFields());
        buttonDelete = new Button("Удалить", event -> deleteCommand());
        add(buttonSend);
        add(buttonDelete);
        disableFields();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Optional<String> commandIdOptional = beforeEnterEvent.getRouteParameters().get("commandId");
        if (commandIdOptional.isPresent() && !commandIdOptional.get().equals("new")) {
            long commandId = Long.parseLong(commandIdOptional.get());
            commandService.getCommand(commandId).ifPresentOrElse(
                    (command) -> {
                        populateFieldsWithCommand(command);
                        isEdited = true;
                    },
                    () -> {
                        textFieldMap.get(FormFields.COMMAND_ID).setEnabled(false);
                        isEdited = false;
                    });
        }
        else {
            isEdited = false;
            textFieldMap.get(FormFields.COMMAND_ID).setEnabled(false);
        }
        enableFields();
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
        disableFields();
    }

    private void populateFieldsWithCommand(Command command) {
        textFieldMap.get(FormFields.COMMAND_ID).setValue(command.getId().toString());
        textFieldMap.get(FormFields.COMMAND_NAME).setValue(command.getCommandName());
        textFieldMap.get(FormFields.COMMAND_ANSWER).setValue(command.getCommandAnswer());
        textFieldMap.get(FormFields.COMMAND_SOURCE).setValue(command.getSourceNames());
    }

    private void makeTextFields() {
        textFieldMap.put(FormFields.COMMAND_ID, makeTextField(
                "ИД команды"
        ));
        textFieldMap.put(FormFields.COMMAND_NAME, makeTextField(
                "Название команды"
        ));
        textFieldMap.put(FormFields.COMMAND_SOURCE, makeTextField(
                "Источники команды"
        ));
        textFieldMap.put(FormFields.COMMAND_ANSWER, makeTextField(
                "Ответ команды"
        ));
    }

    private void disableFields() {
        textFieldMap.forEach((formFields, textField) -> {
            textField.setEnabled(false);
            textField.setValue("");
        });
        buttonSend.setEnabled(false);
        buttonDelete.setEnabled(false);
    }

    private void enableFields() {
        textFieldMap.forEach((formFields, textField) -> {
            if (isEdited || formFields != FormFields.COMMAND_ID) {
                textField.setEnabled(true);
            }
        });
        buttonSend.setEnabled(true);
        if (isEdited) {
            buttonDelete.setEnabled(true);
        }
    }


    private TextField makeTextField(String title) {
        TextField textField = new TextField();
        textField.setPlaceholder(title);
        textField.setValue("");
        textField.setEnabled(true);
        return textField;
    }

    private void sendFields() {
        Command command = Command.builder()
                .id(isEdited ? Long.parseLong(textFieldMap.get(FormFields.COMMAND_ID).getValue()) : null)
                .commandName(textFieldMap.get(FormFields.COMMAND_NAME).getValue())
                .commandAnswer(textFieldMap.get(FormFields.COMMAND_ANSWER).getValue())
                .sourceNames(textFieldMap.get(FormFields.COMMAND_SOURCE).getValue())
                .build();
        try {
            if (isEdited) {
                commandService.updateCommand(command);
            }
            else {
                commandService.createNewCommand(command);
            }
            UI.getCurrent().navigate("");
        } catch (CommandChangeException e) {
            Notification.show("Ошибка команды: " + e.getMessage());
        }
    }

    private void deleteCommand() {
        try {
            commandService.deleteCommand(Long.parseLong(textFieldMap.get(FormFields.COMMAND_ID).getValue()));
            UI.getCurrent().navigate("");
        }
        catch (CommandChangeException e) {
            Notification.show("Ошибка удаления команды: " + e.getMessage());
        }
    }

    private enum FormFields {
        COMMAND_ID,
        COMMAND_NAME,
        COMMAND_SOURCE,
        COMMAND_ANSWER,
        IS_SCRIPT,
        SCRIPT_NAME
    }

}
