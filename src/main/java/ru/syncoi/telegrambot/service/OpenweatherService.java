package ru.syncoi.telegrambot.service;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.syncoi.telegrambot.model.UserSettings;

import java.util.HashMap;

@Service
public class OpenweatherService {

    @Value("${openweather.apikey}")
    private String apikey;
    private OkHttpClient client;

    public OpenweatherService(OkHttpClient client) {
        this.client = client;
    }

//    public HashMap<String, String> getCurrentWeather(UserSettings userSettings) {
//        return null;
//    }
//
//

}
