Для того, чтобы запустить проект, потребуются:
	0. Java 8
	1. PostgreSQL 10 версии и выше
	2. RabbitMQ 3.7.6 и выше
	3. Redis 3.0.504 https://github.com/MicrosoftArchive/redis/releases, либо аналог любой такой же или более высокой версии под Linux

Перед запуском необходимо в постгресе создать базу bacs и выполнить на ней скрипт schema.sql (лежит в db/resources/sql-scripts), который сощдаст наобходимые таблицы.

Проект разделён на несколько модулей:
	db - отвечает за работу бд
	rabbit - отвечает за работу рэббита
	redis - отвечает за работу редиса
	external-api - отвечает за работу с внешними апи
	external-api-sybon - отвечает за работу с сайбоном
	external-api-fake - добавляет фейковый апи, которым можно легко мокать сайбон
	external-api-aggregator - собирает все апи в одно, благодаря чему снаружи кажется, что апи всего один
	standings - сервис, обновляющий мониторы контестов
	background-combined - сервис, отсылающий посылки сайбону и обновляющий их результаты в локальном хранилище + включает в себя обязанности standings
	web - сервис, отвечающий за rest-часть
	web-security - отвечает за безопасность реста
	web-model - содержит модели реста

Запускаемых проекта три:
	1. background-combined (включает standings)
	2. standings (отдельно-запускаемый)
	3. web

Для того, чтобы собрать проект, необходимо:
	1. Установить системную переменную JAVA_HOME в путь, соответствующий рути до jdk, например "C:\Program Files\Java\jdk1.8.0_172"
	2. В cmd или в bash из коня проекта запустить команду "./mvnw clean package", либо просто открыть проект в идее и собрать оттуда maven-проект parent

Запуск производится либо через идею (она сама подхватит все запускаемые проекты), либо с помощью команд:
	web: "java -Dspring.profiles.active=cloud -jar web/target/web-0.0.1-SNAPSHOT.jar
	background-combined: java -Dspring.profiles.active=cloud -jar background-combined/target/background-combined-0.0.1-SNAPSHOT.jar