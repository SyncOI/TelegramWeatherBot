package ru.syncoi.telegrambot.service;

import org.springframework.stereotype.Service;
import ru.syncoi.telegrambot.model.ChatStatus;
import ru.syncoi.telegrambot.repository.ChatStatusRepository;

import java.util.Optional;

@Service
public class ChatStatusService {

    private final ChatStatusRepository repository;

    public ChatStatusService(ChatStatusRepository repository) {
        this.repository = repository;
    }

    public ChatStatus create(ChatStatus chatStatus) {
        return repository.insert(chatStatus);
    }

    public Optional<ChatStatus> getById(long id) {
        return repository.findByChatId(id);
    }

    public void update(ChatStatus chatStatus) {
        repository.save(chatStatus);
    }

    public void delete(ChatStatus chatStatus) {
        repository.delete(chatStatus);
    }

}
