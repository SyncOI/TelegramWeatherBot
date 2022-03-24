package ru.syncoi.telegrambot.command;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.syncoi.telegrambot.dto.CurrentWeather;
import ru.syncoi.telegrambot.exception.IncorrectAnswerWeatherServiceException;
import ru.syncoi.telegrambot.model.ChatStatus;
import ru.syncoi.telegrambot.model.UserSettings;
import ru.syncoi.telegrambot.service.OpenweatherService;
import ru.syncoi.telegrambot.util.SendMessageUtil;

import java.io.IOException;

@Component
public class CurrentWeatherCommand implements Command {

    private OpenweatherService weatherService;

    public CurrentWeatherCommand(OpenweatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public String getName() {
        return "/current";
    }

    @Override
    public SendMessage execute(UserSettings userSettings, ChatStatus chatStatus) {

        if (!StringUtils.hasLength(userSettings.getSettings().get(UserSettings.SETTING_LAT))
        || !StringUtils.hasLength(userSettings.getSettings().get(UserSettings.SETTING_LON))) {
            return SendMessageUtil.createSendMessage(userSettings, "Не заполнены обязательные настройки");
        }

        try {
            CurrentWeather currentWeather = weatherService.getCurrentWeather(userSettings);
            String msg = String.format("Текущая погода в %s - %s: \n градусов: %.1f, ощущается как: %.1f",
                    currentWeather.getName(),
                    currentWeather.getArrayWeather().get(0).getDescription(),
                    currentWeather.getMain().getTemp(),
                    currentWeather.getMain().getFeelsLike());
            return SendMessageUtil.createSendMessage(userSettings, msg);
        } catch (IOException e) {
            return SendMessageUtil.createSendMessage(userSettings, "При выполнении запроса произошла ошибка: " + e.getMessage());
        } catch (IncorrectAnswerWeatherServiceException e) {
            return SendMessageUtil.createSendMessage(userSettings, "Сервис погоды вернул ошибку: " + e.getMessage());
        }
    }
}
