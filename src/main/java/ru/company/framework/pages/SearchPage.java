package ru.company.framework.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.company.framework.utils.Item;

import java.util.List;

public class SearchPage extends BasePage {
    @FindBy(xpath = "//div[@data-widget='fulltextResultsHeader']/div/strong")
    WebElement searchingName;

    @FindBy(xpath = "//div[contains(@class, 'filter-block')]")
    List<WebElement> filtersTable;

    @FindBy(xpath = "//div[contains(@class, 'filter-block')]/div[@value]")
    List<WebElement> checkboxFilter;

    @FindBy(xpath = "//div[@data-widget='searchResultsFiltersActive']//button//span")
    List<WebElement> filterResults;

    @FindBy(xpath = "//div[@data-widget='megaPaginator']")
    WebElement paginator;

    @FindBy(xpath = "//div[@style='grid-column-start: span 12;']/div/div")
    List<WebElement> itemsTable;

    @FindBy(xpath = "//a[contains(@data-widget, 'cart')]")
    WebElement bucketBut;

    /**
     *
     * Метод проверки поиска
     *
     */
    @Step("Проверка на соответствие")
    public void assertSearch(){
        Assert.assertEquals("Найден неправильный товар", StartPage.searchingItemName, searchingName.getText());
    }

    /**
     *
     * Метод для выбора фильтров поиска
     *
     * @param filterName - название блока фильров
     * @param value - значение фильтра или название подфильтра
     * @return SearchPage т.е.  остаемся на этой же странице
     */
    @Step("Фильтр одиночный '{filterName}' на: '{value}")
    public SearchPage inputFilter(String filterName, String value) {
        switch (filterName){
            case "Цена":
                for (WebElement filter: filtersTable) {
                    WebElement title = filter.findElement(By.xpath("./div[1]"));
                    scrollElementInCenter(title, 200);
                    int curFilt = filterResults.size();
                    if (title.getText().trim().equalsIgnoreCase(filterName)){
                        WebElement input = filter.findElement(By.xpath("//input[@qa-id='range-to']"));
                        fillInput(input, value);
                        boolean isResult = true;
                        while (isResult) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (filterResults.size() != curFilt)
                                isResult = false;
                        }
                        break;
                    }
                }
                break;
            case "Бренды":
                for (WebElement filter : filtersTable) {
                    scrollElementInCenter(filter, 300);
                    WebElement title = filter.findElement(By.xpath("./div[1]"));
                    if (title.getText().trim().equalsIgnoreCase(filterName)) {
                        WebElement button = filter.findElement(By.xpath(".//span[@class='show']/span"));
                        if (button.getText().trim().equalsIgnoreCase("Посмотреть все")) {
                            filter.findElement(By.xpath(".//span[@class='show']/span")).click();
                        }
                        fillField(filter.findElement(By.xpath(".//input[@type and @name and @maxlength]")), value);
                        WebElement brand = filter.findElement(By.xpath(".//div[@class='cx8']/a/label"));
                        if (brand.findElement(By.xpath(".//span")).getText().trim().equalsIgnoreCase(value)) {
                                brand.findElement(By.xpath("./div[1]")).click();
                        }
                        waitFilter(filterName, value);
                        break;
                    }
                }
                break;
            case "Беспроводные интерфейсы":
                for (WebElement filter: filtersTable) {
                    WebElement title = filter.findElement(By.xpath("./div[1]"));
                    scrollElementInCenter(title, 200);
                    if (title.getText().trim().equalsIgnoreCase(filterName)){
                        List<WebElement> parameters = filter.findElements(By.xpath(".//span/label"));
                        for (WebElement paramName: parameters) {
                            WebElement par = paramName.findElement(By.xpath(".//span"));
                            if (par.getText().trim().equalsIgnoreCase(value)) {
                                WebElement checkbox = paramName.findElement(By.xpath(".//input"));
                                action.moveToElement(checkbox).click().build().perform();
                                waitFilter(filterName, value);
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            case "Высокий рейтинг":
                for (WebElement filter: checkboxFilter) {
                    WebElement title = filter.findElement(By.xpath(".//span"));
                    if (title.getText().trim().equalsIgnoreCase(filterName)){
                        WebElement checkbox = filter.findElement(By.xpath(".//input"));
                        scrollElementInCenter(checkbox, 200);
                        if (value.equalsIgnoreCase("true"))
                            action.moveToElement(checkbox).click().build().perform();
                        waitFilter(filterName);
                        break;
                    }
                }
                break;
            default:
                Assert.fail("Элемент " + filterName + " не найден!");
        }
        return this;
    }


    /**
     *
     * Метод добавления четных(на сайте) элементов в корзину
     *
     * @return SearchPage
     */
    @Step("Добавление в корзину всех четных элементов")
    public SearchPage selectItems() {
        List<WebElement> pages = paginator.findElements(By.xpath("./div[2]//a"));
        if (pages.get(pages.size()-1).getAttribute("innerText").equalsIgnoreCase("Дальше")) {
            while (pages.get(pages.size()-1).getAttribute("innerText").equalsIgnoreCase("Дальше")) {
                addBucket();
                scrollElementInCenter(pages.get(pages.size()-1), 400);
                pages.get(pages.size()-1).click();
            }

            int pageNumber = Integer.parseInt(paginator.findElement(By.xpath("./div[2]//a[contains(@class, 'b9g2')]")).getAttribute("innerText"));
            int pageCur = Integer.parseInt(pages.get(pages.size() - 1).getAttribute("innerText"));

            for (int p=pageNumber; p <= pageCur; p++) {
                scrollElementInCenter(paginator.findElement(By.xpath("./div[2]//a[text()="+p+"]")), 500);
                paginator.findElement(By.xpath("./div[2]//a[text()="+p+"]")).click();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addBucket();
            }
        } else {
            int pageCur = Integer.parseInt(pages.get(pages.size() - 1).getText());

            for (int p = 1; p <= pageCur; p++) {

                scrollElementInCenter(paginator.findElement(By.xpath("./div[2]//a[text()="+p+"]")), 500);
                paginator.findElement(By.xpath("./div[2]//a[text()="+p+"]")).click();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addBucket();
            }
        }
        return this;
    }

    /**
     *
     * Метод добавления четных(на сайте) элементов в корзину
     * @param val - количество элементов которых надо добавить в корзину
     * @return SearchPage
     */
    @Step("Добавление в корзину {val} четных элементов ")
    public SearchPage selectItems(int val) {
        List<WebElement> pages = paginator.findElements(By.xpath("./div[2]//a"));
        if (pages.get(pages.size()-1).getAttribute("innerText").equalsIgnoreCase("Дальше")) {
            while (pages.get(pages.size()-1).getAttribute("innerText").equalsIgnoreCase("Дальше")) {
                addBucket(val);
                scrollElementInCenter(pages.get(pages.size()-1), 400);
                pages.get(pages.size()-1).click();
            }
            if (Item.getItems().size() < val) {
                int pageNumber = Integer.parseInt(paginator.findElement(By.xpath("./div[2]//a[contains(@class, 'b9g2')]")).getAttribute("innerText"));
                int pageCur = Integer.parseInt(pages.get(pages.size() - 1).getAttribute("innerText"));

                for (int p = pageNumber; p <= pageCur; p++) {
                    scrollElementInCenter(paginator.findElement(By.xpath("./div[2]//a[text()=" + p + "]")), 500);
                    paginator.findElement(By.xpath("./div[2]//a[text()=" + p + "]")).click();
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    addBucket(val);
                }
            }
        } else {
            int pageCur = Integer.parseInt(pages.get(pages.size() - 1).getText());

            for (int p = 1; p <= pageCur; p++) {

                scrollElementInCenter(paginator.findElement(By.xpath("./div[2]//a[text()="+p+"]")), 500);
                paginator.findElement(By.xpath("./div[2]//a[text()="+p+"]")).click();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (Item.getItems().size() >= val) {
                    break;
                }
                addBucket(val);
            }
        }
        return this;
    }

    public BucketPage openBucket() {
        scrollToElementJs(bucketBut);
        action.moveToElement(bucketBut).click().build().perform();
        return app.getBucketPage();
    }



    private void waitFilter(String filterName, String value) {
        boolean isTrue = true;

        while (isTrue) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (WebElement filt: filterResults) {
                scrollElementInCenter(filt, 200);
                String s = filterName+": "+ value;
                if (filt.getText().trim().equalsIgnoreCase(s)){
                    isTrue = false;
                    break;
                }
            }
        }
    }

    private void waitFilter(String filterName) {
        boolean isTrue = true;

        while (isTrue) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (WebElement filt : filterResults) {
                scrollElementInCenter(filt, 200);
                if (filt.getText().trim().equalsIgnoreCase(filterName)) {
                    isTrue = false;
                    break;
                }
            }
        }
    }

    private void addBucket(){
        for (int i = 1; i < itemsTable.size(); i += 2) {
            WebElement item = itemsTable.get(i);
            scrollElementInCenter(item, 100);
            WebElement bucketButton = item.findElement(By.xpath(".//button//div[text()]"));
            if (bucketButton.getText().trim().equalsIgnoreCase("В корзину")) {
                String name = item.findElement(By.xpath(".//a[contains(@class, 'tile-hover-target') and text()]")).getText();
                int current = Integer.parseInt(parseInt(item.findElement(By.xpath(".//div[contains(@class, 'itemasdasda')]/span[1]")).getText()));
                Item.addItems(new Item(name, current));
                action.moveToElement(bucketButton).click().build().perform();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addBucket(int val) {
        for (int i = 1; i < itemsTable.size(); i += 2) {
            WebElement item = itemsTable.get(i);
            scrollElementInCenter(item, 100);
            WebElement bucketButton = item.findElement(By.xpath(".//button//div[text()]"));
            if (bucketButton.getText().trim().equalsIgnoreCase("В корзину")) {
                String name = item.findElement(By.xpath(".//a[contains(@class, 'tile-hover-target') and text()]")).getText();
                int current = Integer.parseInt(parseInt(item.findElement(By.xpath(".//div[contains(@class, 'itemasdasda')]/span[1]")).getText()));
                Item.addItems(new Item(name, current));
                action.moveToElement(bucketButton).click().build().perform();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (Item.getItems().size() >= val) {
                break;
            }
        }
    }
}
