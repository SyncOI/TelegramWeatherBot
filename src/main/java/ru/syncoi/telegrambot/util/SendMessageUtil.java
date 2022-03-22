package ru.syncoi.telegrambot.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.syncoi.telegrambot.model.UserSettings;


public class SendMessageUtil {

    public static SendMessage createSendMessage(UserSettings settings, String text, ReplyKeyboard replyKeyboard) {
        return SendMessage
                .builder()
                .chatId(Long.toString(settings.getChatId()))
                .text(text)
                .replyMarkup(replyKeyboard)
                .build();
    }

    public static SendMessage createSendMessage(UserSettings settings, String text) {
        return createSendMessage(settings, text, null);
    }



}
