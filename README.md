# Telegram Weather Bot
Telegram bot to show weather by coordinates

[Bot link](t.me/syncoiweather_bot), hosted on heroku + Mongo Atlas
## Installation
Fill in the configuration data before installation (see "Configuration").

If you use make, then use:
```
#docker-compose version
make run-docker

#without docker-compose
make run
```
if not use:
```
#docker-compose version
mvn clean install
docker-compose build
docker-compose up

#without docker-compose
mvn clean install
java -jar target/app.jar
```
## Configuration
Before working, you need to fill out the `application.properties` file:
```properties
#telegram
bot.username=<bot's telegram name, you can get it from @BotFather>
bot.token=<bot's telegram token, you can get it from @BotFather>

#mongo
spring.data.mongodb.uri=<MongoDB uri, not needed when running through docker compose, for example: mongodb://localhost:27017/test?retryWrites=true&w=majority>

#openweather
openweather.apikey=<ApiKey OpenWeather, you can get it from https://openweathermap.org/>
```