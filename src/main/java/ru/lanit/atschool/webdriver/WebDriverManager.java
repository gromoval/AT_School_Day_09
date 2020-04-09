/**
 * Класс для работы с вэбдрайвером
 * Автор Васильев И.Н. atcc@mail.ru
 * 02.12.2018
 */
package ru.lanit.atschool.webdriver;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.io.IOException;


public class WebDriverManager {

    public static WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(WebDriverManager.class);
    private WebDriverManager() {

    }

    public static WebDriver getDriver() throws IOException {
        if (driver == null) {
            System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
            System.setProperty("webdriver.chrome.driver", System.getProperty("chrome.driver.path"));
            try {
                ChromeOptions option = new ChromeOptions();
                option.addArguments(System.getProperty("window.size"));
                driver = new ChromeDriver(option);
            } catch(UnreachableBrowserException e) {
               logger.error("Невозможно инциализировать драйвер!", e);
            }
        }
        return driver;
    }

    public static void quit() {
        try {
            driver.quit();
            driver = null;
        } catch (UnreachableBrowserException e) {
            logger.error("Невозможно закрыть браузер!");
        }
    }
}
