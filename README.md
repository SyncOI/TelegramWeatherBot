# Telegram Weather Bot
Telegram bot to show weather by coordinates
## Configuration
Before working, you need to fill out the `application.properties` file:
```properties
#telegram
bot.username=<bot's telegram name, you can get it from @BotFather>
bot.token=<bot's telegram token, you can get it from @BotFather>

#mongo
spring.data.mongodb.host=<MongoDB host, not needed when running through docker compose>
spring.data.mongodb.port=<MongoDB port, not needed when running through docker compose>
spring.data.mongodb.database=<MongoDB database, not needed when running through docker compose>

#openweather
openweather.apikey=<ApiKey OpenWeather, you can get it from https://openweathermap.org/>
```