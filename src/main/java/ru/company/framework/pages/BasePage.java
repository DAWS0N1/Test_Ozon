package ru.company.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.company.framework.managers.DriverManager;
import ru.company.framework.managers.PageManager;

public class BasePage {


    /**
     * Инициализация менеджера страничек
     *
     * @see PageManager
     */
    protected PageManager app = PageManager.getPageManager();

    /**
     * Объект для имитации реального поведения мыши или клавиатуры
     *
     * @see Actions
     */
    protected Actions action = new Actions(DriverManager.getDriver());

    /**
     * Объект для выполнения любого js кода
     *
     * @see JavascriptExecutor
     */
    protected JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

    /**
     * Объект явного ожидания
     * При применении будет ожидать задонного состояния 15 секунд с интервалом в 1 секунду
     *
     * @see WebDriverWait
     */
    protected WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), 15, 1000);


    public BasePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    /**
     * Функция позволяющая скролить до любого элемента с помощью js
     *
     * @param element - веб-элемент странички
     * @see JavascriptExecutor
     */
    protected void scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollElementInCenter(WebElement webElement, int y) {
        String code = "scrollTo({top: " + (webElement.getLocation().y - y) + "})";
        ((JavascriptExecutor)DriverManager.getDriver()).executeScript(code);
    }


    /**
     * Явное ожидание состояния кликабельности элемента
     *
     * @param element - веб-элемент который требует проверки на кликабельность
     * @return WebElement - возвращаем тот же веб элемент что был передан в функцию
     * @see WebDriverWait
     * @see org.openqa.selenium.support.ui.FluentWait
     * @see org.openqa.selenium.support.ui.Wait
     * @see ExpectedConditions
     */
    protected WebElement elementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Общий метод по заполнения поля поиска
     *
     * @param field - веб-елемент поле поиска
     * @param value - значение вводимое в поиска
     */
    public void searchingCall(WebElement field, String value) {
        field.click();
        field.sendKeys(value);
        field.sendKeys(Keys.ENTER);
    }

    /**
     * Общий метод по заполнения полей ввода
     *
     * @param field - веб-елемент поле ввода
     * @param value - значение вводимое в поле
     */
    public void fillInput(WebElement field, String value) {
        scrollElementInCenter(field, 200);
        elementToBeClickable(field);
        action.moveToElement(field).click().build().perform();
        js.executeScript("arguments[0].value = '';", field);
        field.sendKeys(value);
        Assert.assertEquals("Поле было заполнено некорректно", value, parseInt(field.getAttribute("value")));
        field.sendKeys(Keys.ENTER);
    }

    /**
     * Общий метод по заполнению полей с датой
     *
     * @param field - веб-елемент поле с датой
     * @param value - значение вводимое в поле с датой
     */
    public void fillField(WebElement field, String value) {
        elementToBeClickable(field);
        action.moveToElement(field).click().build().perform();
        js.executeScript("arguments[0].value = '';", field);
        field.sendKeys(value);
        Assert.assertEquals("Поле было заполнено некорректно", value, field.getAttribute("value"));
        field.sendKeys(Keys.ENTER);
    }

    /**
     * Метод для парсинга полученных значений
     *
     * @param s - полученное из эелемента значение
     */

    protected String parseInt(String s) {
        return s.replaceAll("\\D", "");
    }
}
