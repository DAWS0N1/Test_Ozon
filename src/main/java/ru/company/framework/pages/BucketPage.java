package ru.company.framework.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.company.framework.utils.Item;

import java.io.*;

import static ru.company.framework.utils.AllureListener.addReport;

public class BucketPage extends BasePage {
    @FindBy(xpath = "//section[@data-widget='total']//span[contains(text(),'Товары')]")
    WebElement bucketTotal;

    @FindBy(xpath = "//div[@data-widget='controls']")
    WebElement controlPanel;

    @FindBy(xpath = "//div[@class='modal-content']//div[text()='Удалить']")
    WebElement deleting;

    @FindBy(xpath = "//div[@data-widget='emptyCart']//h1")
    WebElement title;

    /**
     *
     * Метод проверки корзины
     *
     * @return BucketPage
     */
    @Step("Проверка корзины")
    public BucketPage assertBucket() {
        Assert.assertEquals("Количество элементов в корзине неверное", "Товары ("+Item.getItems().size()+")", bucketTotal.getAttribute("innerText"));
        return this;
    }

    /**
     *
     * Метод удаления всех элементов из корзины
     *
     * @return BucketPage
     */
    @Step("Очистка корзины")
    public BucketPage deletedAll(){
        if (controlPanel.findElement(By.xpath(".//input")).getAttribute("checked").equalsIgnoreCase("false")){
            controlPanel.findElement(By.xpath("./label")).click();
        }
        controlPanel.findElement(By.xpath("./span")).click();

        deleting.click();
        boolean isResult = true;
        while (isResult) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (title.getText().trim().equalsIgnoreCase("Корзина пуста"))
                isResult = false;
        }
        Assert.assertEquals("Корзина не пуста", "Корзина пуста", title.getText().trim());
        return app.getBucketPage();
    }

    /**
     *
     * Метод сохранения всех элементов в отдельный файл
     *
     */
    @Step("Вывод файла")
    public void outputFile() {
        try(FileWriter out = new FileWriter("src/main/resources/items.txt", false)){
            for (Item item : Item.getItems()) {
                try {
                    out.write(item.getName() + " - " + item.getCurrent() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String maxItem = "";
            int max = 0;
            for (Item item : Item.getItems()) {
                if (item.getCurrent() > max){
                    maxItem = item.getName();
                    max = item.getCurrent();
                }
            }
            try {
                out.write("\n\nMax:\n"+maxItem+" - "+max);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Allure.addAttachment("Items", new ByteArrayInputStream(addReport("items.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
