#language: ru

@all
Функционал: Страхование

  @positive
  @checkIphoneItem
  Сценарий: Проверка айфонов
    * Загружена стартовая страница
    * Ищем товар 'iphone'
    * Выбираем фильтр по форме поле/значение
      |          Цена           | 100000 |
      | Беспроводные интерфейсы |   NFC  |
      |     Высокий рейтинг     |  true  |
    * Добавляем '8' чётных товаров в корзину
    * Переходим в корзину
    * Проверяем корзину
    * Очищаем корзину
    * Выводим в файл все товары

  @pozitive
  @checkBudsItems
  Сценарий: Проверка Наушников
    * Загружена стартовая страница
    * Ищем товар 'беспроводные наушники'
    * Выбираем фильтр по форме поле/значение
      |         Бренды          |  Beats  |
      |         Бренды          | Samsung |
      |         Бренды          |  Xiaomi |
      |          Цена           |  10000  |
      |     Высокий рейтинг     |   true  |
    * Добавляем все чётные товары в корзину
    * Переходим в корзину
    * Проверяем корзину
    * Очищаем корзину



#  @parameterized
#  Структура сценария: Заявка на ипотеку на готовое жилье <номер>
#    * Загружена стартовая страница
#    * Переход в главное меню 'Ипотека'
#    * Выбираем подменю 'Ипотека на готовое жильё'
#    * Нажимаем кнопку 'Подать заявку'
#    * Заполняем форму поле/значение
#      | Стоимость недвижимости    | 5 180 000 |
#      | Первоначальный взнос      | 3 058 000 |
#      | Срок кредита              |     30    |
#    * Проверяем чекбокс по форме поле/значение
#      | Скидка 0,3% при покупке квартиры на ДомКлик    | true  |
#      | Страхование жизни                              | false |
#      | Молодая семья                                  | true  |
#      | Электронная регистрация сделки                 | true  |
#    * Проверяем что на странице значения совпадают по форме поле/значение
#      | Ежемесячный платеж    |   <Доход>   |
#      | Сумма кредита         | 2 122 000 ₽ |
#      | Необходимый доход     |  20 618 ₽   |
#      | Процентная ставка     |  <Процент>  |
#
#    Примеры:
#      | номер | Доход    | Процент |
#      | 1     | 16 017 ₽ | 10%     |
#      | 2     | 19 070 ₽ | 8,1%    |