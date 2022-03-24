package ru.syncoi.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CurrentWeather {
    private Coordinate coord;
    @JsonProperty("weather")
    private List<Weather> arrayWeather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private int dt;
    private Sys sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;


    @JsonAnySetter
    public void add(String key, String value) {
        // nothing
    }

    @Getter
    @Setter
    public static class Sys {
        private int type;
        private int id;
        private double message;
        private String country;
        private int sunrise;
        private int sunset;

        @JsonAnySetter
        public void add(String key, String value) {
            // nothing
        }
    }

    @Getter
    @Setter
    public static class Clouds {
        private int all;

        @JsonAnySetter
        public void add(String key, String value) {
            // nothing
        }
    }

    @Getter
    @Setter
    public static class Wind {
        private double speed;
        private double deg;

        @JsonAnySetter
        public void add(String key, String value) {
            // nothing
        }
    }

    @Getter
    @Setter
    public static class Main {
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        @JsonProperty("temp_min")
        private double tempMin;
        @JsonProperty("temp_max")
        private double tempMax;
        private double pressure;
        private double humidity;

        @JsonAnySetter
        public void add(String key, String value) {
            // nothing
        }
    }


    @Getter
    @Setter
    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        @JsonAnySetter
        public void add(String key, String value) {
            // nothing
        }
    }

    @Getter
    @Setter
    public static class Coordinate {
        private double lon;
        private double lat;

        @JsonAnySetter
        public void add(String key, String value) {
            // nothing
        }
    }
}
