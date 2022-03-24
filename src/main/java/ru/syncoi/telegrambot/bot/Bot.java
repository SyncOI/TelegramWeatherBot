package ru.syncoi.telegrambot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.syncoi.telegrambot.command.Command;
import ru.syncoi.telegrambot.command.SettingCommand;
import ru.syncoi.telegrambot.command.StartCommand;
import ru.syncoi.telegrambot.exception.CommandNotFoundException;
import ru.syncoi.telegrambot.exception.MessageTextIncorrectException;
import ru.syncoi.telegrambot.exception.UnknownCallbackException;
import ru.syncoi.telegrambot.model.ChatStatus;
import ru.syncoi.telegrambot.model.UserSettings;
import ru.syncoi.telegrambot.service.ChatStatusService;
import ru.syncoi.telegrambot.service.UserSettingsService;
import ru.syncoi.telegrambot.util.MessageUtil;
import ru.syncoi.telegrambot.util.SendMessageUtil;
import ru.syncoi.telegrambot.util.UpdateUtil;

import java.util.HashMap;

@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    private String token;
    @Value("${bot.username}")
    private String username;
    private HashMap<String, Command> commands = new HashMap();
    private UserSettingsService userSettingsService;
    private ChatStatusService chatStatusService;
    private ApplicationContext context;

    public Bot(UserSettingsService userSettingsService, ChatStatusService chatStatusService, ApplicationContext context) {
        this.userSettingsService = userSettingsService;
        this.chatStatusService = chatStatusService;
        this.context = context;

        register(context.getBean(StartCommand.class));
        register(context.getBean(SettingCommand.class));
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = UpdateUtil.getChatId(update);
        ChatStatus chatStatus = chatStatusService
                .getById(chatId)
                .orElseGet(() -> chatStatusService.create(new ChatStatus(chatId)));
        UserSettings userSettings = userSettingsService
                .getById(chatId)
                .orElseGet(() -> userSettingsService.create(new UserSettings(chatId)));
        UpdateUtil.MessageType messageType = UpdateUtil.getMessageType(update);

        try {
            switch (messageType) {
                case CALLBACK:
                    processCallback(update, userSettings, chatStatus);
                    break;
                case COMMAND:
                    processCommand(update, userSettings, chatStatus);
                    break;
                case MESSAGE:
                    processNonCommand(update, userSettings, chatStatus);
                    break;
            }
        } catch (CommandNotFoundException | MessageTextIncorrectException | UnknownCallbackException e) {
            try {
                execute(SendMessageUtil.createSendMessage(userSettings, e.getMessage()));
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void processCommand(Update update, UserSettings userSettings, ChatStatus chatStatus) throws CommandNotFoundException, TelegramApiException {
        String textMessage = MessageUtil.getTextMessage(
                UpdateUtil.getMessage(update)
        );
        Command command = commands.get(textMessage);
        if (command == null) {
            throw new CommandNotFoundException("Команда '" + textMessage + "' не найдена.");
        }

        execute(command.execute(userSettings, chatStatus));
    }

    private void processNonCommand(Update update, UserSettings userSettings, ChatStatus chatStatus) throws MessageTextIncorrectException, TelegramApiException {
        String textMessage = MessageUtil.getTextMessage(
                UpdateUtil.getMessage(update)
        );

        if (chatStatus.getState().equals("edit_setting")) {
            userSettings.getSettings().put(chatStatus.getSettingForEditing(), textMessage);
            chatStatus.setState("");
            chatStatus.setSettingForEditing("");
            userSettingsService.update(userSettings);
            chatStatusService.update(chatStatus);
            execute(SendMessageUtil.createSendMessage(userSettings, "Значение установлено."));
        } else if ("".equals(textMessage)) {
            throw new MessageTextIncorrectException("Сообщение пустое");
        }
    }

    private void processCallback(Update update, UserSettings userSettings, ChatStatus chatStatus) throws TelegramApiException, UnknownCallbackException {
        String data = update.getCallbackQuery().getData();

        if (data.startsWith(SettingCommand.CALLBACK_DATA)) {
            chatStatus.setState("edit_setting");
            chatStatus.setSettingForEditing(data.substring(SettingCommand.CALLBACK_DATA.length()));
            chatStatusService.update(chatStatus);

            execute(SendMessageUtil.createSendMessage(userSettings, "Введите значение настройки"));
        } else {
            throw new UnknownCallbackException();
        }

    }

    private void register(Command command) {
        commands.put(command.getName(), command);
    }
}
