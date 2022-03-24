package ru.syncoi.telegrambot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "user_setting")
public class UserSettings {

    public static final String SETTING_LAT = "lat";
    public static final String SETTING_LON = "lon";

    @Id
    private String id;
    private final long chatId;
    private HashMap<String, String> settings = new HashMap();

}
