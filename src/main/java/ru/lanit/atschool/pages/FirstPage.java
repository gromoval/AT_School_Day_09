package ru.lanit.atschool.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FirstPage extends BasePage {

    public WebElement getCategories() {
        WebElement webElement = driver.findElement(By.linkText("Категории"));
        return webElement;
    }

    public WebElement getUsers() {
        WebElement webElement = driver.findElement(By.linkText("Пользователи"));
        return webElement;
    }

    public WebElement getSearchIcon() {
        WebElement webElement = driver.findElement(By.xpath("//div[@class='navbar-search dropdown']"));
        return webElement;
    }

    public WebElement getSearchField() {
        WebElement webElement = driver.findElement(By.xpath("//*[@aria-controls='dropdown-menu dropdown-search-results']"));
        return webElement;
    }

    public WebElement getSearchUserString() {
        WebElement webElement = driver.findElement(By.xpath("//*[@class='dropdown-search-user']"));
        return webElement;
    }

    public WebElement btnSignIn() {
        WebElement webElement = driver.findElement(By.xpath("//button[@class='btn navbar-btn btn-default btn-sign-in']"));
        return webElement;
    }

    public WebElement getUsernameField() {
        WebElement webElement = driver.findElement(By.xpath("//input[@id='id_username']"));
        return webElement;
    }

    public WebElement getPasswordField() {
        WebElement webElement = driver.findElement(By.xpath("//input[@id='id_password']"));
        return webElement;
    }

    public WebElement btnEnter() {
        WebElement webElement = driver.findElement(By.xpath("//button[@class='btn btn-primary btn-block' and @type='submit']"));
        return webElement;
    }

    public WebElement getCloseSign() {
        WebElement webElement = driver.findElement(By.xpath("//button[@class='close' and @aria-label='Закрыть']"));
        return webElement;
    }

    public WebElement imgUserAvatar() {
        WebElement webElement = driver.findElement(By.xpath("//img[@class='user-avatar' and @width='64']"));
        return webElement;
    }

    public WebElement btnUserExit() {
        WebElement webElement = driver.findElement(By.xpath("//button[@class='btn btn-default btn-block']"));
        return webElement;
    }

    public WebElement getRedAlertSign() {
        WebElement webElement = driver.findElement(By.xpath("//div[@class='alerts-snackbar in']"));
        return webElement;
    }

//                    public WebElement getCategories() {
//        WebElement webElement = driver.findElement(By.xpath("//button[@class='btn navbar-btn btn-default btn-sign-in']"));
//        return webElement;
//    }

}
