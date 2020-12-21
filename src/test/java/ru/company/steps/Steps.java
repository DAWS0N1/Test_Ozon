package ru.company.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import ru.company.framework.managers.PageManager;

public class Steps {
    /**
     * Менеджер страничек
     * @see PageManager#getPageManager()
     */
    private PageManager app = PageManager.getPageManager();

    @Когда("^Загружена стартовая страница$")
    public void getInitialPage(){
        app.getStartPage();
    }

    @Когда("^Ищем товар '(.*)'$")
    public void searchingItem(String nameItem){
        app.getStartPage().searchingItem(nameItem);
    }

    @Тогда("^Выбираем фильтр по форме поле/значение$")
    public void inputSFilter(DataTable dataTable) {
        dataTable.cells().forEach(
                raw -> {
                    app.getSearchPage().inputFilter(raw.get(0), raw.get(1));
        });
    }

    @Когда("^Добавляем все чётные товары в корзину$")
    public void selectItems() {
        app.getSearchPage().selectItems();
    }

    @Когда("^Добавляем '(.*)' чётных товаров в корзину$")
    public void selectItems(int val) {
        app.getSearchPage().selectItems(val);
    }

    @Тогда("^Переходим в корзину$")
    public void openBucket(){
        app.getSearchPage().openBucket();
    }

    @Тогда("^Проверяем корзину$")
    public void assertBucket(){
        app.getBucketPage().assertBucket();
    }

    @Тогда("^Очищаем корзину$")
    public void deletingAll(){
        app.getBucketPage().deletedAll();
    }

    @Тогда("^Выводим в файл все товары$")
    public void outputFile(){
        app.getBucketPage().outputFile();
    }
}
