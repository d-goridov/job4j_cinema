# Проект "Сайт по покупке билетов в кинотеатр"

## Описание проекта

На главной странице сайта показан список фильмов на которые можно купить билет. Делать покупку на сайте могут только 
зарегистрированные пользователи - заполните форму регистрации, затем пройдите авторизацию. Далее нажимаете на фильм и 
выбираете ряд, затем выбираете место. Далее страница с информацией о выбранном фильме и месте. Если вы выбираете 
"Купить" то переходите на страницу об успешной покупке, если "Отменить" - возвращаетесь на главную страницу. 

<img src="readme_images/main_page.jpg" alt="Главная страница" width="925" height="721"/>



<img src="readme_images/reg_page.jpg" alt="Главная страница" width="925" height="450"/>




<img src="readme_images/select_row.jpg" alt="Главная страница" width="925" height="607"/>




<img src="readme_images/select_cell.jpg" alt="Главная страница" width="857" height="637"/>




<img src="readme_images/pay_info.jpg" alt="Главная страница" width="891" height="783"/>




<img src="readme_images/success_page.jpg" alt="Главная страница" width="924" height="656"/>

## Технологии
 - Java 17 
 - Spring boot 
 - Maven 3.8, 
 - Postgres 14
 - Thymeleaf 
 - Bootstrap 
 - JDBC 
 - Liquibase 4.15 
 - Junit5 5.7 
 - Mockito 4
 - H2 1.4
 - Lombok 1.18

## Запуск проекта
1. В Postgres создаете БД cinema: username = postgres,  password = password. 
2. Обновляете схему БД через ```liquibase:update```
3. Запускаете проект:
 ```shell
  mvn spring-boot run
 ```
4. Переходите по ссылке http://localhost:8080/main

## Контакты
- Telegramm: https://t.me/ShayPatr1ckCormak
- Email: **d.goridov@mail.ru**

