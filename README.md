JDK 21
Spring Boot 3.3.1
MySQL 8.3.0

Основное:

Веб приложение для взаимодействия с оптовыми клиентами, получение заявки от клиентов, управление складом, управление
поставками, отчет по обороту, чистой прибыли, автомотическое формирование заявки для закупки товар.

Доп: 

Взаимодействие API с банком, для получение данных по оплаченным документам 

# Чек-лист Проекта

### Общие задачи

- [x] Создание структуры проекта
- [x] Настройка окружения разработки
- [ ] Написание README.md
- [ ] Установить необходимые зависимости

### Backend общее

- [x] Создать основные сущности (Entity)
- [x] Настроить базу данных (Добавить таблицу для логина и хэш)
- [ ] Реализовать контроллеры
- [ ] Написать сервисы
- [ ] Написать тесты

### Тестирование

- [ ] unit-тесты
- [ ] интеграционные тесты

### Документация

- [ ] Написать документацию для API

___

## Разбивка задач

### Backend

(возможно переработать user и company, сделать возможность добавления нескольких юзеров в 1 компанию (манагер, отдел
закупок))

#### для админа

- [ ]  сервисы
    - [x] заявок клиентов
    - [x] закупка товара(переделать под такую же модель , как заявка клиентов)
    - [x] товары ( доделать, сделать логику на инвентаризацию )
    - [x] категории
    - [x] компании клиенты (дописать обновление данных компании)
    - [x] компании поставщики (дописать обновление данных компании)
    - [x] юзеры
- [ ] контроллеры (изменить адрес /admin/api )
    - [x] категории
    - [x] заявка клиентов
    - [x] товары
    - [x] комания поставщика
    - [x] заяка на закупку
    - [x] юзер
    - [x] комания клиенты 
- [ ] OAuth 2
- [ ] логирование
- [ ] обработка исключений
- [ ] мониторинг
- [ ] добавить в бд таблицу с логином и хэш для входа

попробовать добавить кэширования  
разделить на микросервисы

#### для клиента








