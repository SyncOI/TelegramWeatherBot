package ru.syncoi.telegrambot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.syncoi.telegrambot.dto.CurrentWeather;
import ru.syncoi.telegrambot.exception.IncorrectAnswerWeatherServiceException;
import ru.syncoi.telegrambot.model.UserSettings;

import java.io.IOException;
import java.util.HashMap;

@Service
public class OpenweatherService {

    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5";
    public static final String LANG = "ru";
    public static final String UNITS = "metric";

    @Value("${openweather.apikey}")
    private String apikey;
    private OkHttpClient client;

    public OpenweatherService(OkHttpClient client) {
        this.client = client;
    }

    private void addApikeyInUrl(HttpUrl.Builder urlBuilder) {
        urlBuilder.addQueryParameter("appid", apikey);
    }

    public CurrentWeather getCurrentWeather(UserSettings userSettings) throws IOException, IncorrectAnswerWeatherServiceException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/weather").newBuilder();
        addApikeyInUrl(urlBuilder);
        urlBuilder.addQueryParameter("lat",
                userSettings.getSettings().get(UserSettings.SETTING_LAT));
        urlBuilder.addQueryParameter("lon",
                userSettings.getSettings().get(UserSettings.SETTING_LON));
        urlBuilder.addQueryParameter("lang", LANG);
        urlBuilder.addQueryParameter("units", UNITS);

        Request request = new Request.Builder().url(urlBuilder.build().toString()).build();
        Call call = client.newCall(request);
        Response response = call.execute();

        if (String.valueOf(response.code()).startsWith("2")) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body().string(), CurrentWeather.class);
        } else {
            throw new IncorrectAnswerWeatherServiceException("Код ответа: " + response.code() + ";Тело ответа:" + response.body());
        }
    }


}
