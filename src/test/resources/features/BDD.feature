#language: ru
Функционал: тестируем dev.n7lanit.ru

  Предыстория: зарегистрироваться на сайте

  Структура сценария: проверка, что тесты работают корректно
    Пусть открыт браузер и введен адрес "<адрес>"
    И переход на страницу Категории
    И переход на страницу Пользователи
    И поиск пользователя из предыстории
    Также нажатие "Кнопка_Войти_Формы_Авторизации" и вызов формы авторизации

    Примеры:
      | адрес                       |
      | https://dev.n7lanit.ru/     |


  Сценарий: выполнить тесты авторизации ( сущ. пользователем, несущ. пользователем , с пустыми полями и т.д.)
    Дано проверка логинов и паролей пользователей
      | login                   | password                      |
      | sdfkjhyfejrhsdfksdfh    | sdjfhksdjfhksdjfhyrgberjhbdf  |
      | admin                   | простокакойтопароль           |
      | gromovalex              | 1234qwerasdf                  |
      | abyrvalg                | [blank]                       |
      | [blank]                 | [blank]                       |
      | [blank]                 | abyvalg                       |

    Тогда тест завершен



#@gmail
#Функционал: Вход в gmail
#
#  Предыстория:
#    Завести в системе пользователя см. Тест "Заведение нового пользователя в Gmail"
#    Сочинить письмо.
#    Дано заведен новый пользователь
#
#@mail
#  Структура сценария: авторизация на почте работает корректно
#    Пусть открыт браузер и введен адрес "<адрес>"
#    Пусть <шаг>
#
#    Тогда пользователь вводит учетные данные
#    |login|<login>|
#    |password|<пароль>|
#	И вот
##    И вводит пароль "<пароль>"
##    Если появилось окно "Подтвердите, что это именно вы", то пользователь вводит "<резервный email>"
##    Когда пользователь "<login>" авторизован
##    То пользователь создает новое письмо
##    И пользователь заполняет поле адресат 'i.vasilev@pflb.ru'
##    И пользователь заполняет поле 'Тема'
##    И пользователь вводит 'текст письма'
##    И закрывает письмо
##    И переходит в раздел 'Черновики'
##    И проверяет, что письмо сохранено в черновиках
##    И открывает черновик
##    И проверяет, что адресат письма соответствует введенному
##    И проверяет, что тема письма соответствует введенной
##    И проверяет, что текст письма соответствует введенному
##    И отправляет письмо
##    И проверяет, что письмо исчезло из черновиков
##    И переходит в раздел Отправленные
##    И проверяет, что письмо появилось в Отправленных
##    И закрывает почту
##    И попадает на страницу логина
##    Тогда тест завершен
#
#    Примеры:
#      | login                 | пароль          | резервный email |адрес|шаг|
#      | kotionb3505@gmail.com | KotionEachB3505 | sai0nara@ya.ru  |  http://gmail.com   |открыт браузер и введен адрес "<адрес>"|
#      | kotionb3505@gmail.com | KotionEachB3505 | sai0nara@ya.ru  |  http://mail.ru  | пользователь вводит "<login>"  |
