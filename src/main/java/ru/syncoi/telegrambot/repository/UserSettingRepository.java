package ru.syncoi.telegrambot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.syncoi.telegrambot.model.ChatStatus;
import ru.syncoi.telegrambot.model.UserSettings;

import java.util.Optional;

public interface UserSettingRepository extends MongoRepository<UserSettings, String> {
    Optional<UserSettings> findByChatId(long chatId);
}
