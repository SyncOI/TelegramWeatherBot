package ru.syncoi.telegrambot.command;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.syncoi.telegrambot.model.ChatStatus;
import ru.syncoi.telegrambot.model.UserSettings;
import ru.syncoi.telegrambot.util.SendMessageUtil;

@Component
public class StartCommand implements Command {

    @Getter
    private final String name = "/start";

    public SendMessage execute(UserSettings userSettings, ChatStatus chatStatus) {
        return SendMessageUtil.createSendMessage(userSettings,
                "Добро пожаловать! Перед получением погоды заполните настройки");
    }
}
