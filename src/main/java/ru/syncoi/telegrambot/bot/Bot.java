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
import ru.syncoi.telegrambot.model.ChatStatus;
import ru.syncoi.telegrambot.model.UserSettings;
import ru.syncoi.telegrambot.service.ChatStatusService;
import ru.syncoi.telegrambot.service.UserSettingsService;
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
        ChatStatus chatStatus = chatStatusService.getById(chatId).orElse(new ChatStatus(chatId));
        UserSettings userSettings = userSettingsService.getById(chatId).orElse(new UserSettings(chatId));

        if (!StringUtils.hasLength(chatStatus.getId())) {
            chatStatusService.create(chatStatus);
        }
        if (!StringUtils.hasLength(userSettings.getId())) {
            userSettingsService.create(userSettings);
        }

        try {
            if (UpdateUtil.isCommand(update)) {
                processCommand(update, userSettings, chatStatus);
            } else {
                processNonCommand(update, userSettings, chatStatus);
            }
        } catch (CommandNotFoundException | MessageTextIncorrectException e) {
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
        String textMessage = UpdateUtil.getTextMessage(update);
        Command command = commands.get(textMessage);
        if (command == null) {
            throw new CommandNotFoundException("Команда '" + textMessage + "' не найдена.");
        }

        execute(command.execute(userSettings, chatStatus));
    }

    private void processNonCommand(Update update, UserSettings userSettings, ChatStatus chatStatus) throws MessageTextIncorrectException {
        String textMessage = UpdateUtil.getTextMessage(update);

        if ("".equals(textMessage)) {
            throw new MessageTextIncorrectException("Сообщение пустое");
        }
    }

    private void register(Command command) {
        commands.put(command.getName(), command);
    }
}
