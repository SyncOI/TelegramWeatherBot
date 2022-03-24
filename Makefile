run-docker:
	mvn clean install
	docker-compose build
	docker-compose up

run:
	mvn clean install
	java -jar target/app.jar
