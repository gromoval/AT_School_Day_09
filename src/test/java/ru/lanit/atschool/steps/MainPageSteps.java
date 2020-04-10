/**
 * Класс, имплементирующий геркин-стиль описания шагов
 * Автор: Громов А.C. <gromov-rabota@yandex.ru>
 *     09/04/2020
 */
package ru.lanit.atschool.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ru.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.lanit.atschool.pages.FirstPage;
import ru.lanit.atschool.pages.MainPage;
import ru.lanit.atschool.webdriver.WebDriverManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainPageSteps {
    WebDriver driver = WebDriverManager.getDriver();
    MainPage mainPage = new MainPage();
    FirstPage firstPage = new FirstPage();

    public MainPageSteps() throws IOException {
    }

    /**
     * Метод проверки наличия вебэлемента на странице. В случае отсутсвия элемента не вызывает exception, а возвращает false
     * @param locator_string передаем xpath и ищем элемент по нему
     * @return true, если есть элемент, false, если нет
     */
    private boolean isElementPresent(String locator_string) {
        try {
            return WebDriverManager.getDriver().findElement(By.xpath(locator_string)).isDisplayed();
        } catch (NoSuchElementException | IOException e){
            return false;
        }
    }

    /**
     * Метод для заполнения формы ввода. Очистка поля (если осталось предыдущее значение), выделение его кликом и передача в него новых данных
     * заполняется одно поле, для заполнения нескольких полей вызвать метод соответсвующее количество раз
     * @param webElement в какой вебэлемент будем передавать данные
     * @param filler чем будем заполнять поле вебэлемента (String)
     */
    private void fillingTextField(WebElement webElement, String filler) {
        webElement.clear();
        webElement.click();
        webElement.sendKeys(filler);
    }

    /**
     * Метод для снятия скриншотов средствами Selenium
     * @return массив байтов, который можно приаттачить к allure отчету в виде скриншота
     */
    @Attachment(value = "Скриншот", type = "image/png")
    public byte[] saveScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Метод, заменяющий паттерн (в данном случае [blank]) в передаваемой DataTable на пустую строку
     * по умолчанию кукумбер пустые строки передает как null, а не "". Примененная "фича" кукумбера только для версии 5.0.0 и выше
     * @param dataTable
     * @return DataTable c пустыми строками вида ""
     */
    @DataTableType(replaceWithEmptyString = "[blank]")
    public List<Map<String, String>> convert(DataTable dataTable) {
        return dataTable.asMaps();
    }

    @Пусть("открыт браузер и введен адрес \"(.*)\"$")
    public void открытБраузерИВведенАдрес(String url) {
        mainPage.openPage(url);
        Allure.addAttachment("скрин", new ByteArrayInputStream(saveScreenshot()));
        Allure.addAttachment("Console log:", "Открыли браузер и зашли на страницу "+url);
    }

    @Тогда("тест завершен")
    public void тестЗавершен() {
        driver.quit();
    }

    @И("переход на страницу Категории")
    public void переходНаСтраницуКатегории() {
        firstPage.getCategories.click();
        Allure.addAttachment("скрин", new ByteArrayInputStream(saveScreenshot()));
        Allure.addAttachment("Console log:", "Нашли и клинкули ссылку 'Категории'");
    }

    @И("переход на страницу Пользователи")
    public void переходНаСтраницуПользователи() {
        firstPage.getUsers.click();
        Allure.addAttachment("скрин", new ByteArrayInputStream(saveScreenshot()));
        Allure.addAttachment("Console log:", "Нашли и клинкули ссылку 'Пользователи'");
    }

    @И("поиск пользователя из предыстории")
    public void поискПользователяИзПредыстории() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        firstPage.getSearchIcon.click();
        firstPage.getSearchField.click();
        firstPage.getSearchField.sendKeys(System.getProperty("username"));
        new WebDriverWait(driver, Integer.parseInt(System.getProperty("explicit.wait"))).until(ExpectedConditions.visibilityOf(firstPage.getSearchUserString));
        firstPage.getSearchUserString.click();
        Assert.assertTrue(driver.findElement(By.xpath("//abbr[@title='Присоединился 26 марта 2020 г., 11:35']")).isDisplayed());
        Allure.addAttachment("скрин", new ByteArrayInputStream(saveScreenshot()));
        Allure.addAttachment("Console log:", "Нашли пользователя '"+System.getProperty("username")+"'");
    }

    @Дано("^проверка логинов и паролей пользователей$")
    public void логиныИПаролиПользователей(List<Map<String, String>> table) {
        for (int i=0; i<table.size(); i++) {
            new WebDriverWait(driver, Integer.parseInt(System.getProperty("explicit.wait"))).until(ExpectedConditions.elementToBeClickable(firstPage.btnSignIn));
            new WebDriverWait(driver, Integer.parseInt(System.getProperty("explicit.wait"))).until(ExpectedConditions.visibilityOf(firstPage.btnSignIn));
            firstPage.btnSignIn.click();
            String username = table.get(i).get("login");
            String password = table.get(i).get("password");
            new WebDriverWait(driver, Integer.parseInt(System.getProperty("explicit.wait"))).until(ExpectedConditions.elementToBeClickable(firstPage.getUsernameField));
            fillingTextField(firstPage.getUsernameField, username);
            fillingTextField(firstPage.getPasswordField, password);
            firstPage.btnEnter.click();
            new WebDriverWait(driver, Integer.parseInt(System.getProperty("explicit.wait"))).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOf(firstPage.getRedAlertSign),
                    ExpectedConditions.elementToBeClickable(firstPage.imgUserAvatar)));
            if (isElementPresent("//div[@class='alerts-snackbar in']")) {
                Allure.addAttachment("Console log:", firstPage.getRedAlertSign.getText() + "Авторизоваться нет возможности! Проверка пройдена!");
                Assert.assertTrue(firstPage.getRedAlertSign.isDisplayed());
                Allure.addAttachment("скрин", new ByteArrayInputStream(saveScreenshot()));
                new WebDriverWait(driver, Integer.parseInt(System.getProperty("explicit.wait"))).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='alerts-snackbar in']")));
                firstPage.getCloseSign.click();
            } else {
                Allure.addAttachment("Console log:", "Пользователь " + username + " авторизован! Проверка пройдена!");
                Allure.addAttachment("скрин", new ByteArrayInputStream(saveScreenshot()));
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
        new WebDriverWait(driver, Integer.parseInt(System.getProperty("explicit.wait"))).until(ExpectedConditions.elementToBeClickable(firstPage.getCloseSign));
        Allure.addAttachment("скрин", new ByteArrayInputStream(saveScreenshot()));
        Assert.assertTrue(firstPage.getCloseSign.isDisplayed());
        firstPage.getCloseSign.click();
        Allure.addAttachment("Console log:", "Проверили, что рефлексия работает нормально");
    }

}

