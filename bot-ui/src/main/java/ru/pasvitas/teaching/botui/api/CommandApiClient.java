package ru.pasvitas.teaching.botui.api;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.pasvitas.teaching.botui.model.Command;

@FeignClient(name = "commands", url = "${api.url}")
public interface CommandApiClient {

    @GetMapping("/commands")
    ResponseEntity<List<Command>> getCommandList();

    @GetMapping("/commands/{id}")
    ResponseEntity<Command> getCommand(@PathVariable("id") long id);

    @PostMapping("/commands")
    ResponseEntity<Void> createNewCommand(@RequestBody Command command);

    @PutMapping("/commands/{id}")
    ResponseEntity<Void> updateCommand(@PathVariable("id") long id, @RequestBody Command command);

    @DeleteMapping("/commands/{id}")
    ResponseEntity<Void> deleteCommand(@PathVariable("id") long id);
}
