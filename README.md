# DevelopersMemes
Latest             | Top         |  Hot
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/dyoma-veronika/DevelopersLife/blob/master/screenshots/device-2021-01-31-222038.png)   |![](https://github.com/dyoma-veronika/DevelopersLife/blob/master/screenshots/device-2021-01-31-222117.png) |![](https://github.com/dyoma-veronika/DevelopersLife/blob/master/screenshots/device-2021-01-31-222150.png)
На главном экране приложения отображается gif-изображение вместе с его описанием, полученным с сайта developerslife.ru.
Под изображением располагаются две кнопки:
Одна (кнопка «следующий») загружает следующий рандомный пост (вызывая метод API). 
Вторая — позволяет вернуться к предыдущему посту, который мы сохранили в кэш после загрузки. 
Получается такая структура: (изначально кнопка назад не активна, но уже есть какой-то пост, загруженный при старте) нажимаем на «следующий» → появляется пост и кнопка назад становится активна → нажимаем на «следующий» ещё раз → появляется новый пост и обе кнопки активны → нажимаем назад и попадаем на один пост назад (обе кнопки активны) → нажимаем ещё раз и попадаем на первую картинку (кнопка назад стала не активна снова). Теперь если кликнуть на «следующий» мы сначала должны пройти те посты, которые у нас были загружены, а потом как они кончатся — загружать новые. 
Ответы от API закешированы для реализации переходов «назад». Предусмотрены различные состояния загрузки данных: ошибка загрузки, загрузка и успешная загрузка. Использованы JavaRx, Retrofit, Glide.
