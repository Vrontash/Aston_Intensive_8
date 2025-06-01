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