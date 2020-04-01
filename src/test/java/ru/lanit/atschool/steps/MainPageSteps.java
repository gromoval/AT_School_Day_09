package ru.lanit.atschool.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ru.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.lanit.atschool.pages.FirstPage;
import ru.lanit.atschool.pages.MainPage;
import ru.lanit.atschool.webdriver.WebDriverManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainPageSteps {
    WebDriver driver = WebDriverManager.getDriver();
    MainPage mainPage = new MainPage();
    FirstPage firstPage = new FirstPage();

    private boolean isElementPresent(String locator_string){
        try {
            return WebDriverManager.getDriver().findElement(By.xpath(locator_string)).isDisplayed();
        } catch (NoSuchElementException e){
            return false;
        }
    }

    //    замена [blank] на пустую строку в данных
    @DataTableType(replaceWithEmptyString = "[blank]")
    public List<Map<String, String>> convert(DataTable dataTable) {
        return dataTable.asMaps();
    }

    @Пусть("открыт браузер и введен адрес \"(.*)\"$")
    public void открытБраузерИВведенАдрес(String url) {
        mainPage.openPage(url);
    }

    @Тогда("тест завершен")
    public void тестЗавершен() {
        driver.quit();
    }

    @И("переход на страницу Категории")
    public void переходНаСтраницуКатегории() {
        firstPage.getCategories().click();
        System.out.println("Нашли и клинкули ссылку 'Категории'");
    }

    @И("переход на страницу Пользователи")
    public void переходНаСтраницуПользователи() {
        firstPage.getUsers().click();
        System.out.println("Нашли и клинкули ссылку 'Пользователи'");
    }

    @И("поиск пользователя из предыстории")
    public void поискПользователяИзПредыстории() {
        firstPage.getSearchIcon().click();
        firstPage.getSearchField().click();
        firstPage.getSearchField().sendKeys("gromovalex");
        firstPage.getSearchUserString().click();
        Assert.assertTrue(driver.findElement(By.xpath("//abbr[@title='Присоединился 26 марта 2020 г., 11:35']")).isDisplayed());
        System.out.println("Нашли пользователя 'gromovalex'");
    }

//    эту штуку с циклами не разбить никак на отдельные
    @Дано("^проверка логинов и паролей пользователей$")
    public void логиныИПаролиПользователей(List<Map<String, String>> table) throws InterruptedException {
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        for (int i=0; i<table.size(); i++) {
            firstPage.btnSignIn().click();
            String username = table.get(i).get("login");
            String password = table.get(i).get("password");
            firstPage.getUsernameField().clear();
            if (username.isEmpty()) {
                firstPage.btnEnter().click();
            } else {
                firstPage.getUsernameField().click();
                firstPage.getUsernameField().sendKeys(username);
                firstPage.getPasswordField().clear();
                if (password.isEmpty()) {
                    firstPage.btnEnter().click();
                } else {
                    firstPage.getPasswordField().click();
                    firstPage.getPasswordField().sendKeys(password);
                    firstPage.getPasswordField().sendKeys(Keys.ENTER);
                }
            }
            Thread.sleep(1000); //без этой штуки здесь никак, т.к страница может перегрузиться, а может и нет. а если не загрузится будет эксепшн
            if (isElementPresent("//div[@class='alerts-snackbar in']")) {
                System.out.println("Неверные параметры. Авторизоваться нет возможности! Проверка пройдена!"); // там 2 вида вывода, или поля пустые или неверный логин пароль. причем это все выведено через 1 элемент, не разделить их
                Assert.assertTrue(firstPage.getRedAlertSign().isDisplayed());
//                new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(firstPage.getCloseSign()));
                Thread.sleep(4000);
                firstPage.getCloseSign().click();
            } else {
                System.out.println("Пользователь "+username+" авторизован! Проверка пройдена!");
                Assert.assertTrue(firstPage.imgUserAvatar().isDisplayed());
                firstPage.imgUserAvatar().click();
                try {
                    firstPage.btnUserExit().click();
                    driver.switchTo().alert().accept();
                } catch (org.openqa.selenium.UnhandledAlertException e) {
                }
            }
        }
    }
}

