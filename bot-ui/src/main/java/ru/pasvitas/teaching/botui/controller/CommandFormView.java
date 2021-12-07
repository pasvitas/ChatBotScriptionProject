package ru.pasvitas.teaching.botui.controller;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pasvitas.teaching.botui.controller.model.CommandFormModel;
import ru.pasvitas.teaching.botui.exception.CommandChangeException;
import ru.pasvitas.teaching.botui.service.CommandService;

@SpringComponent
@Route("/commands/")
public class CommandFormView extends FormLayout implements HasUrlParameter<String> {

    private final Button buttonSend;
    private final Button buttonDelete;

    private final TextField id = new TextField("ИД команды");
    private final TextField commandName = new TextField("Имя команды");
    private final TextField sourceNames = new TextField("Источники команды");
    private final TextField commandAnswer = new TextField("Ответ команды");


    private final CommandService commandService;

    private final Binder<CommandFormModel> binder = new Binder<>(CommandFormModel.class);

    @Autowired
    public CommandFormView(CommandService commandService) {
        buttonSend = new Button("Отправить", event -> sendFields());
        buttonDelete = new Button("Удалить", event -> deleteCommand());
        this.commandService = commandService;

        HorizontalLayout horizontalLayout = new HorizontalLayout(buttonSend, buttonDelete);
        buttonSend.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(id, commandName, sourceNames, commandAnswer, horizontalLayout);

        binder.bindInstanceFields(this);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        if (parameter == null) {
            setCommand(null);
        } else {
                long commandId = Long.parseLong(parameter);
                commandService.getCommand(commandId).ifPresentOrElse(
                        (command) -> setCommand(CommandFormModel.fromBackendModel(command)),
                        () -> setCommand(null));
        }
    }

    private void setCommand(CommandFormModel command) {
        binder.setBean(command);
        if (command != null) {
            commandName.focus();
            buttonDelete.setEnabled(true);
        }
        else {
            id.setEnabled(false);
            commandName.focus();
            buttonDelete.setEnabled(false);
        }
    }

    private void sendFields() {
        try {
            CommandFormModel model = new CommandFormModel();
            binder.writeBean(model);
            if (!model.getId().isEmpty()) {
                commandService.updateCommand(model.toBackendModel());
            }
            else {
                commandService.createNewCommand(model.toBackendModel());
            }
            UI.getCurrent().navigate("");
        } catch (CommandChangeException | ValidationException e) {
            Notification.show("Ошибка команды: " + e.getMessage());
        }
    }

    private void deleteCommand() {
        try {
            commandService.deleteCommand(binder.getBean().toBackendModel().getId());
            UI.getCurrent().navigate("");
        }
        catch (CommandChangeException e) {
            Notification.show("Ошибка удаления команды: " + e.getMessage());
        }
    }
}
