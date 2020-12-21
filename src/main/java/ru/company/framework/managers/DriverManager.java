package ru.company.framework.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static ru.company.framework.utils.Const.PATH_DRIVER;

public class DriverManager {


    /**
     * Переменная для хранения объекта веб дравера
     * @see WebDriver
     */
    private static WebDriver driver = null;

    /**
     * Менеджер пропертей
     * @see TestPropManager#getTestPropManager()
     */
    private static TestPropManager props = TestPropManager.getTestPropManager();

    /**
     * Конструктор специально запривейтили (синглтон)
     * @see DriverManager#getDriver()
     */
    private DriverManager() {
    }


    /**
     * Метод инициализирующий веб драйвер
     */
    private static void initDriver() {
        System.setProperty("webdriver.chrome.driver", props.getProperty(PATH_DRIVER));
        driver = new ChromeDriver();
    }


    /**
     * Метод ленивой инициализации веб драйвера
     * @return WebDriver - возвращает веб драйвер
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }


    /**
     * Метод для закрытия сессии драйвера и браузера
     * @see WebDriver#quit()
     */
    public static void quitDriver() {
        driver.quit();
        driver = null;
    }
}
