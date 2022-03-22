package ru.syncoi.telegrambot.command;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.syncoi.telegrambot.model.ChatStatus;
import ru.syncoi.telegrambot.model.UserSettings;
import ru.syncoi.telegrambot.service.ChatStatusService;
import ru.syncoi.telegrambot.util.SendMessageUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class SettingCommand implements Command {

    private ChatStatusService chatStatusService;

    public SettingCommand(ChatStatusService chatStatusService) {
        this.chatStatusService = chatStatusService;
    }

    @Override
    public String getName() {
        return "/setting";
    }

    @Override
    public SendMessage execute(UserSettings userSettings, ChatStatus chatStatus) {

        chatStatus.setState("setting");
        chatStatusService.update(chatStatus);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(InlineKeyboardButton.builder().text("Setting 1").callbackData("asdf").build());
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);

        return SendMessageUtil.createSendMessage(userSettings, "Выбирай:", markupInline);
    }
}
