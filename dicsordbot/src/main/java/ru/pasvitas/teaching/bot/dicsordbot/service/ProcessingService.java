package ru.pasvitas.teaching.bot.dicsordbot.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.pasvitas.teaching.bot.dicsordbot.model.ChatMessage;

@Service
@RequiredArgsConstructor
public class ProcessingService extends ListenerAdapter {

    private final JDA jda;

    private final KafkaTemplate<String, ChatMessage> template;

    @EventListener(ApplicationReadyEvent.class)
    public void setUp() {
        jda.addEventListener(this);
    }

    @KafkaListener(topics = "sendMessage")
    public void sendMessage(String message) {
        ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);
        jda.retrieveUserById(chatMessage.getChatId()).queue(
                user -> user.openPrivateChannel().queue(
                        channel -> channel.sendMessage(chatMessage.getMessage()).queue()
                )
        );
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        super.onPrivateMessageReceived(event);
        if (event.getAuthor().getIdLong() == event.getJDA().getSelfUser().getIdLong()) {
            return;
        }
        template.send("receiveMessage", new ChatMessage("discord", event.getAuthor().getIdLong(), event.getMessage().getContentRaw()));
    }
}
