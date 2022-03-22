package ru.syncoi.telegrambot.service;

import org.springframework.stereotype.Service;
import ru.syncoi.telegrambot.model.UserSettings;
import ru.syncoi.telegrambot.repository.UserSettingRepository;

import java.util.Optional;

@Service
public class UserSettingsService {

    private final UserSettingRepository repository;

    public UserSettingsService(UserSettingRepository repository) {
        this.repository = repository;
    }

    public UserSettings create(UserSettings userSettings) {
        return repository.insert(userSettings);
    }

    public Optional<UserSettings> getById(long id) {
        return repository.findByChatId(id);
    }

    public void update(UserSettings userSettings) {
        repository.save(userSettings);
    }

    public void delete(UserSettings userSettings) {
        repository.delete(userSettings);
    }
}
