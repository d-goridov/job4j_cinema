# Проект "Сайт по покупке билетов в кинотеатр"

На главной странице сайта показан список фильмов на которые можно купить билет

<img src="readme_images/main_page.jpg" alt="Главная страница" width="1107" height="863"/>

Делать покупку на сайте могут только зарегистрированные пользователи

Заполните форму регистрации, затем пройдите авторизацию

<img src="readme_images/reg_page.jpg" alt="Главная страница" width="1159" height="628"/>


Далее нажимаете на фильм и выбираете ряд

<img src="readme_images/select_row.jpg" alt="Главная страница" width="925" height="607"/>

Затем выбираете место 

<img src="readme_images/select_cell.jpg" alt="Главная страница" width="857" height="637"/>

Далее страница с информацией о выбранном фильме и месте

<img src="readme_images/pay_info.jpg" alt="Главная страница" width="891" height="783"/>

Если вы выбираете "Купить" то переходите на страницу об успешной покупке, если "Отменить"
то возвращаетесь на главную страницу

<img src="readme_images/success_page.jpg" alt="Главная страница" width="1020" height="724"/>

# Технологии
Java 17, Spring boot, Maven 3.8, Postgres 14, Thymeleaf, Bootstrap, JDBC, Liquibase 4.15, 
Junit5 5.7, Mockito 4, H2 1.4, Lombok 1.18

# Настройка
В Postgres создаете БД cinema: username = postgres,  password = password. 
Обновляете схему БД через liquibase:update

# Запуск
С помощью команды spring-boot:run и переход по ссылке http://localhost:8080/main

# Сборка
Сборка проекта осуществляется с помощью Maven

