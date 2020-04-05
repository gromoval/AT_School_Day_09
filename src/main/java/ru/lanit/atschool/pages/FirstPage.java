package ru.lanit.atschool.pages;

import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.lanit.atschool.Intefaces.NameOfElement;

public class FirstPage extends BasePage {

    @FindBy(xpath = "//div[@class='navbar-search dropdown']")
    public WebElement getSearchIcon;

    @FindBy(xpath = "//*[@aria-controls='dropdown-menu dropdown-search-results']")
    public WebElement getSearchField;

    @FindBy(xpath = "//*[@class='dropdown-search-user']")
    public WebElement getSearchUserString;

    @NameOfElement("Кнопка_Войти_Формы_Авторизации")
    @FindBy(xpath = "//button[@class='btn navbar-btn btn-default btn-sign-in']")
    public WebElement btnSignIn;

    @FindBy(linkText = "Категории")
    public WebElement getCategories;

    @FindBy(linkText = "Пользователи")
    public WebElement getUsers;

    @FindBy(xpath = "//input[@id='id_username']")
    public WebElement getUsernameField;

    @FindBy(xpath = "//input[@id='id_password']")
    public WebElement getPasswordField;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-block' and @type='submit']")
    public WebElement btnEnter;

    @FindBy(xpath = "//button[@class='close' and @aria-label='Закрыть']")
    public WebElement getCloseSign;

    @FindBy(xpath = "//img[@class='user-avatar' and @width='64']")
    public WebElement imgUserAvatar;

    @FindBy(xpath = "//button[@class='btn btn-default btn-block']")
    public WebElement btnUserExit;

    @FindBy(xpath = "//div[@class='alerts-snackbar in']")
    public WebElement getRedAlertSign;

//    public WebElement getCategories() {
//        WebElement webElement = driver.findElement(By.xpath("//button[@class='btn navbar-btn btn-default btn-sign-in']"));
//        return webElement;
//    }

}
