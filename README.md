# Telegram Weather Bot
Telegram bot to show weather by coordinates
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
spring.data.mongodb.host=<MongoDB host, not needed when running through docker compose>
spring.data.mongodb.port=<MongoDB port, not needed when running through docker compose>
spring.data.mongodb.database=<MongoDB database, not needed when running through docker compose>

#openweather
openweather.apikey=<ApiKey OpenWeather, you can get it from https://openweathermap.org/>
```