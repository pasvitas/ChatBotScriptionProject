package ru.pasvitas.teaching.botui.controller;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pasvitas.teaching.botui.model.Command;
import ru.pasvitas.teaching.botui.service.CommandService;

@SpringComponent
@Route("")
public class AllCommandsView extends VerticalLayout {

    @Autowired
    public AllCommandsView(CommandService commandService) {
        Grid<Command> commandGrid = new Grid<>(Command.class);

        DataProvider<Command, Void> dataProvider =
                DataProvider.fromCallbacks(
                        query -> {

                            List<Command> commands = commandService.getAllCommands();

                            int offset = query.getOffset();
                            int limit = query.getLimit();

                            return commands.stream();
                        },
                        query -> commandService.getAllCommands().size()
                );
        commandGrid.setDataProvider(dataProvider);
        commandGrid.addColumn(Command::getId).setHeader("ID");
        commandGrid.addColumn(Command::getCommandName).setHeader("Название");
        commandGrid.addColumn(Command::getCommandAnswer).setHeader("Ответ");
        commandGrid.addColumn(Command::getSourceNames).setHeader("Источники");
        commandGrid.addItemClickListener(event -> UI.getCurrent().navigate("/commands/" + event.getItem().getId()));
        add(commandGrid);
        add(new Button("Новая команда", event -> UI.getCurrent().navigate("/commands/")));
    }
}
