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
import java.util.Optional;

@Component
public class SettingCommand implements Command {

    public static final String CALLBACK_DATA = "set_setting_";

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
        rowInline.add(InlineKeyboardButton.builder()
                .text("Lat: " + Optional.ofNullable(userSettings.getSettings().get(UserSettings.SETTING_LAT)).orElse(""))
                .callbackData(CALLBACK_DATA + UserSettings.SETTING_LAT)
                .build());
        rowInline.add(InlineKeyboardButton.builder()
                .text("Lon: " + Optional.ofNullable(userSettings.getSettings().get(UserSettings.SETTING_LON)).orElse(""))
                .callbackData(CALLBACK_DATA + UserSettings.SETTING_LON)
                .build());
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);

        return SendMessageUtil.createSendMessage(userSettings, "Выбирай:", markupInline);
    }
}
