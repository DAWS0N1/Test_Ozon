package ru.company.framework.managers;

import ru.company.framework.pages.BucketPage;
import ru.company.framework.pages.SearchPage;
import ru.company.framework.pages.StartPage;

public class PageManager {
    /**
     * Менеджер страничек
     */
    private static PageManager pageManager;

    /**
     * Стартовая страничка
     */
    private static StartPage startPage;

    /**
     * Страница поиска
     */
    private static SearchPage searchPage;

    /**
     * Страница корзины
     */
    private static BucketPage bucketPage;

    /**
     * Конструктор специально запривейтили (синглтон)
     */
    private PageManager() {
    }

    /**
     * Ленивая инициализация PageManage
     *
     * @return PageManage
     */
    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    /**
     * Ленивая инициализация {@link StartPage}
     *
     * @return StartPage
     */
    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    /**
     * Ленивая инициализация {@link SearchPage}
     *
     * @return SearchPage
     */
    public SearchPage getSearchPage() {
        if (searchPage == null) {
            searchPage = new SearchPage();
        }
        return searchPage;
    }

    /**
     * Ленивая инициализация {@link BucketPage}
     *
     * @return BucketPage
     */
    public BucketPage getBucketPage(){
        if (bucketPage == null) {
            bucketPage = new BucketPage();
        }
        return bucketPage;
    }

    public static void quitPages() {
        if (startPage != null) {
            startPage = null;
        }
        if (searchPage != null) {
            searchPage = null;
        }
        if (bucketPage != null) {
            bucketPage = null;
        }
    }
}
