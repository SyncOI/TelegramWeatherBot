package ru.syncoi.telegrambot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.syncoi.telegrambot.model.ChatStatus;

import java.util.Optional;

public interface ChatStatusRepository extends MongoRepository<ChatStatus, String> {
    Optional<ChatStatus> findByChatId(long chatId);
}
