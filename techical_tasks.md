TaskEntity ->
    время создания,время завершения можно оставить -> timeCreated timeCompleted
    цель задачи - goal
**-    описание к задаче(e.x не забыть на встречу документы и тд) - NEW!! desc ++++++++**
-    на какую дата задача - NEW!! DATE +++++++++++
    владелец задачи -> user_entity_id
-    важность задачи -> NEW!!! (PRIORITY, SECONDARY, UNIMPORTANT) ++++++++++
    статус задачи -> COMPLETED ACTIVE

UserEntity ->
    пароль - password
    username - username
    роль - ADMIN USER ANONIM
    email
    количество задач, которые пользователь хочет выполнять в день NEW!!! ++++++++

ROLE, TASKSTATUS, TASKPRIORITY ++++++

Должна быть страница с настройками пользователя, а именно:
    смена пароля
    смена почты
    maybe смена аватарки

Приложение делится на две основные страницы:

    1)задачи на сегодня 
        -> отображаются задачи, дата которых совпадает с настоящей датой.
        -> задачи выводятся в виде упорядоченного по важности задачи и по времени задачи
        -> можно добавлять задачи, скорее всего стоит сделать отдельную вкладку с формой для вставки задачи,
            но есть неудобства, что не видны все остальные задачи. Если выдавать новую вкладку, то реализовывать
            можно  с помощью JAVA, а если сделать шо-то модное, то скорее всего JAVASCRIPT
        -> можно добавить небольшую табличку, в которой будет отображаться погода на несколько часов
        -> так же должны быть иконки-переходов в настройки и на след. страницу

    2) задачи на все остальные дни
        -> существует список дней кликнув на один из дней открывается страница с "задачи на сегодня",
        но только задачи отображаются на конкретного дня(скорее всего можно создать специальную ссылку
        /task/{DAY}


Сегодня нужно:
    разделить страницы на "задачи сегодня" и "все задачи". если задача на сегодняшнюю дату, то она остается
        на странице today, если нет, то уходит на страницу all-tasks
    создать страницу с настройками
    создать сортировки задач - по времени и приоритету



