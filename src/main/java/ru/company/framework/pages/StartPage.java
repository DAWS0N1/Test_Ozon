package ru.company.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartPage extends BasePage {
    @FindBy(name = "search")
    WebElement searchElement;

    static String searchingItemName = "";

    /**
     * Функция поиска товара
     *
     * @param itemName - название товара
     * @return SearchPage - т.е. переходим на страницу поиска
     */
    @Step("Поиск товара '{itemName}'")
    public SearchPage searchingItem(String itemName) {
        searchingCall(searchElement, itemName);
        searchingItemName = itemName;
        return app.getSearchPage();
    }
}
