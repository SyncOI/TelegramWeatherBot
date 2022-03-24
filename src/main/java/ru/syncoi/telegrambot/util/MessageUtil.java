package ru.syncoi.telegrambot.util;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageUtil {

    public static String getTextMessage(Message message) {
        return message.getText();
    }

}
