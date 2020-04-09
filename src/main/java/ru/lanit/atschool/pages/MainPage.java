package ru.lanit.atschool.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import java.io.IOException;
import java.util.List;
public class MainPage extends BasePage {

    public MainPage() throws IOException {
    }

    /**
     * Метод открывает браузер на заданной странице
     * страница задается в файле config.properties.
     */
    public void openPage(String url) {
        driver.get(url);
        logger.info("Выполнен вход на страницу: " + url);
    }

    /**
     * Кнопка "Search".
     */

    @FindBy(xpath = "//input[@value='Search']")
    public WebElement btnSearch;

    /**
     * Кнопка "Посмотреть все запросы".
     */

    @FindBy(xpath = "//button[text()='Посмотреть все запросы']")
    public WebElement btnShowAllRequests;

    /**
     * Выпадающий список "Количество".
     */

    @FindBy(css = "#page_number")
    public WebElement dropdownListCount;

    /**
     * Опции выпадающего списка "Количество".
     */

    @FindBy(css = ".custom-select option")
    public List<WebElement> dropdownListCountCollection;

    /**
     * Поле ввода "Введите ключевое слово".
     */

    @FindBy(xpath = "//input[@placeholder='Введите ключевое слово']")
    public WebElement inputField;
}

