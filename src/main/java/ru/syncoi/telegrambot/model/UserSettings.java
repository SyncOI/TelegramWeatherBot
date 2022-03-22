package ru.syncoi.telegrambot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "user_setting")
public class UserSettings {

    @Id
    private String id;
    private final long chatId;
    private String setting1;
    private String setting2;
    private String setting3;

}
