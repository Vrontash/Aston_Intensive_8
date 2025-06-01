### [EN](#EN) | [RU](#RU)
# <a name="RU"></a>Требования к запуску проекта
- Для работы отправки сообщений по почте в notification-service необходимо указать в файле .env:
  -  Адрес используемой почты ```EMAIL_LOGIN```
  -  Пароль к приложению (не обычный пароль почты, см. App passwords в Gmail)```EMAIL_PASSWORD```
- Для запуска программы выполнить следующую команду в терминале из корневой папки проекта
  - ```docker compose up```
- Чтобы увидеть eureka перейти после запуска проекта по ссылке:  http://localhost:8080/eureka/
- Чтобы работать с user-service перейти после запуска проекта по ссылке:  http://localhost:8080/api/users 
- Чтобы работать с notification-service перейти после запуска проекта по ссылке:  http://localhost:8080/api/notification/email \
Работа программы была протестирована через Postman

# Требования к проекту
Создать docker-compose.yml, который развернет всю микросервисную систему, включая Kafka, PostgreSQL, API Gateway, 
Service Discovery, External Configuration и 2 микросервиса(user-service и notification-service(он использует кафку), 
созданные ранее). Проверить, что сервисы корректно взаимодействуют друг с другом в контейнерной среде.

# <a name="EN"></a>Requirements for project to run
- To send messages by mail via notification-service, you must specify in the file .env:
  - The email address to use ``EMAIL_LOGIN``
  - The password to the application (not the usual mail password, see App passwords in Gmail) ``EMAIL_PASSWORD``
- To run the program, run the following command in the terminal from the root folder of the project
  - ```docker compose up```
- To see eureka, click on the link after the launch of the project: http://localhost:8080/eureka/
- To work with user-service, click on the link after launching the project: http://localhost:8080/api/users
- To work with notification-service, click on the link after launching the project: http://localhost:8080/api/notification/email \
  The program's API was checked via Postman

# Project requirements
Create a docker-compose.yml, which will deploy the entire microservice system, including Kafka, PostgreSQL, API Gateway,
Service Discovery, ExternalConfiguration, and 2 microservices (user-service and notification-service (it uses kafka),
created earlier). Check that the services interact correctly with each other in the container environment.