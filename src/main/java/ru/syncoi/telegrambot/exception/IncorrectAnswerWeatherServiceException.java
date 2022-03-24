package ru.syncoi.telegrambot.exception;

public class IncorrectAnswerWeatherServiceException extends Exception {
    public IncorrectAnswerWeatherServiceException() {
    }

    public IncorrectAnswerWeatherServiceException(String message) {
        super(message);
    }
}
