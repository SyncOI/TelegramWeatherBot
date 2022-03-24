package ru.syncoi.telegrambot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@Getter
@Setter
@Document(collection = "chat_status")
public class ChatStatus {
    @Id
    private String id;
    private final long chatId;
    private String state = "";

    private String settingForEditing;
}
