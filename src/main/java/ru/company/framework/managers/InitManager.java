package ru.company.framework.managers;

import java.util.concurrent.TimeUnit;

import static ru.company.framework.utils.Const.*;

public class InitManager {

    /**
     * Менеджер пропертей
     * @see TestPropManager#getTestPropManager()
     */
    public static TestPropManager props = TestPropManager.getTestPropManager();

    /**
     * Инициализация фреймворка и запуск браузера со страницей приложения
     * @see DriverManager#getDriver()
     * @see TestPropManager#getProperty(String)
     * @see ru.company.framework.utils.Const
     */
    public static void initFramework() {
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        DriverManager.getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(props.getProperty(PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
        DriverManager.getDriver().get(props.getProperty(APP_URL));
    }

    /**
     * Завершения работы фреймворка - гасит драйвер и закрывает сессию с браузером
     * @see DriverManager#quitDriver()
     */
    public static void quitFramework() {
        DriverManager.quitDriver();
    }
}
