package ru.syncoi.telegrambot.util;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateUtil {

    public static boolean isCommand(Update update) {
        return getTextMessage(update).startsWith("/");
    }

    public static String getTextMessage(Update update) {
        return update.getMessage().getText();
    }

    public static Long getChatId(Update update) {
        return update.getMessage().getChatId();
    }
}
