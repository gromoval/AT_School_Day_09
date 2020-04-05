package ru.lanit.atschool.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ru.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
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

    private boolean isElementPresent(String locator_string) {
        try {
            return WebDriverManager.getDriver().findElement(By.xpath(locator_string)).isDisplayed();
        } catch (NoSuchElementException e){
            return false;
        }
    }

//    замена [blank] на пустую строку в данных (не null, как если бы случилось, если бы мы задали изначально просто пустые ячейки)
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
        firstPage.getCategories.click();
        System.out.println("Нашли и клинкули ссылку 'Категории'");
    }

    @И("переход на страницу Пользователи")
    public void переходНаСтраницуПользователи() {
        firstPage.getUsers.click();
        System.out.println("Нашли и клинкули ссылку 'Пользователи'");
    }

    @И("поиск пользователя из предыстории")
    public void поискПользователяИзПредыстории() {
        firstPage.getSearchIcon.click();
        firstPage.getSearchField.click();
        firstPage.getSearchField.sendKeys("gromovalex");
        firstPage.getSearchUserString.click();
        Assert.assertTrue(driver.findElement(By.xpath("//abbr[@title='Присоединился 26 марта 2020 г., 11:35']")).isDisplayed());
        System.out.println("Нашли пользователя 'gromovalex'");
    }

//    эту штуку с циклами не разбить никак на отдельные
    @Дано("^проверка логинов и паролей пользователей$")
    public void логиныИПаролиПользователей(List<Map<String, String>> table) {
        for (int i=0; i<table.size(); i++) {
            firstPage.btnSignIn.click();
            String username = table.get(i).get("login");
            String password = table.get(i).get("password");
            new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(firstPage.getUsernameField));
            firstPage.getUsernameField.clear();
            if (username.isEmpty()) {
                firstPage.btnEnter.click();
            } else {
                firstPage.getUsernameField.click();
                firstPage.getUsernameField.sendKeys(username);
                firstPage.getPasswordField.clear();
                if (password.isEmpty()) {
                    firstPage.btnEnter.click();
                } else {
                    firstPage.getPasswordField.click();
                    firstPage.getPasswordField.sendKeys(password);
                    firstPage.getPasswordField.sendKeys(Keys.ENTER);
                }
            }
//            Ждем, пока выскочит сообщение в красном блоке или появится кнопка, которая значит, что мы авторизовались
            new WebDriverWait(driver, 30).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOf(firstPage.getRedAlertSign),
                    ExpectedConditions.elementToBeClickable(firstPage.imgUserAvatar)));

            if (isElementPresent("//div[@class='alerts-snackbar in']")) {
                System.out.printf("%s Авторизоваться нет возможности! Проверка пройдена!\n", firstPage.getRedAlertSign.getText());
                Assert.assertTrue(firstPage.getRedAlertSign.isDisplayed());
                new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='alerts-snackbar in']")));
//                new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOf(firstPage.getRedAlertSign)); // не пойму, но это не работает
                firstPage.getCloseSign.click();
            } else {
                System.out.printf("Пользователь %s авторизован! Проверка пройдена!\n", username);
                Assert.assertTrue(firstPage.imgUserAvatar.isDisplayed());
                firstPage.imgUserAvatar.click();
                try {
                    firstPage.btnUserExit.click();
                    driver.switchTo().alert().accept();
                } catch (org.openqa.selenium.UnhandledAlertException e) {
                }
            }
        }
    }

    @Также("нажатие {string} и вызов формы авторизации")
    public void нажатиеКнопкиВойтиИВызовФормыАвторизации(String arg0) {
        firstPage.get(arg0).click();
//        new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(firstPage.getUsernameField));
        Assert.assertTrue(firstPage.getCloseSign.isDisplayed());
        firstPage.getCloseSign.click();
        System.out.println("Проверили, что рефлексия работает нормально");
    }
}

