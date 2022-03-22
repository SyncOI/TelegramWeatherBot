package ru.syncoi.telegrambot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.syncoi.telegrambot.model.ChatStatus;
import ru.syncoi.telegrambot.model.UserSettings;

public interface Command {
    String getName();
    SendMessage execute(UserSettings userSettings, ChatStatus chatStatus);
}
