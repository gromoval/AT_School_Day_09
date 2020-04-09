
package ru.lanit.atschool.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ru.lanit.atschool.Intefaces.NameOfElement;
import ru.lanit.atschool.webdriver.WebDriverManager;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;


public abstract class BasePage {
    public final Logger logger = LogManager.getLogger(getClass());
    protected WebDriver driver;

    public BasePage() throws IOException {
        driver = WebDriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public WebElement get(String cucumberElementName)
    {
        Class<?> clazz = this.getClass();
        for (Field field : clazz.getDeclaredFields())
        {
            if (field.isAnnotationPresent(NameOfElement.class))
            {
                NameOfElement nameOfElementAnnotation = field.getAnnotation(NameOfElement.class);
                if (nameOfElementAnnotation.value().equals(cucumberElementName))
                {
                    try
                    {
                        return (WebElement) field.get(this);

                    } catch (IllegalAccessException e)
                    {
                        logger.error("ERROR: element with name " + cucumberElementName + " at page " + this.getClass().getName() + " is not public");
                    }
                }
            }
        }
        throw new IllegalArgumentException("ERROR: there is no such element with name " + cucumberElementName + " at page " + this.getClass().getName());
    }

    public List<WebElement> getCollection(String cucumberElementName)
    {
        Class<?> clazz = this.getClass();
        for (Field field : clazz.getDeclaredFields())
        {
            if (field.isAnnotationPresent(NameOfElement.class))
            {
                NameOfElement nameOfElementAnnotation = field.getAnnotation(NameOfElement.class);
                if (nameOfElementAnnotation.value().equals(cucumberElementName))
                {
                    try
                    {
                        return (List<WebElement>) field.get(this);

                    } catch (IllegalAccessException e)
                    {
                        logger.error("ERROR: element with name " + cucumberElementName + " at page " + this.getClass().getName() + " is not public");
                    }
                }
            }
        }
        throw new IllegalArgumentException("ERROR: there is no such element with name " + cucumberElementName + " at page " + this.getClass().getName());
    }
}