package ru.syncoi.telegrambot.util;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateUtil {

    public static Long getChatId(Update update) {
        return getMessage(update).getChatId();
    }

    public static Message getMessage(Update update) {
        MessageType messageType = getMessageType(update);

        if (messageType.equals(MessageType.MESSAGE)
                || messageType.equals(MessageType.COMMAND)) {
            return update.getMessage();
        } else if (messageType.equals(MessageType.CALLBACK)) {
            return update.getCallbackQuery().getMessage();
        }

        throw new IllegalStateException("Неподдерживаемый тип сообщений");
    }

    public static MessageType getMessageType(Update update) {
        if (update.getCallbackQuery() != null) {
            return MessageType.CALLBACK;
        } else if (update.getMessage() != null) {
            if (MessageUtil.getTextMessage(update.getMessage()).startsWith("/")) {
                return MessageType.COMMAND;
            } else {
                return MessageType.MESSAGE;
            }
        }

        throw new IllegalStateException("Неподдерживаемый тип сообщений");
    }

    public enum MessageType {
        MESSAGE, CALLBACK, COMMAND
    }
}
