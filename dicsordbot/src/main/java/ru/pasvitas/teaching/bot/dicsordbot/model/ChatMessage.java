package ru.pasvitas.teaching.bot.dicsordbot.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessage {
    @SerializedName("Source")
    private String source;
    @SerializedName("ChatId")
    private long chatId;
    @SerializedName("Message")
    private String message;
}
