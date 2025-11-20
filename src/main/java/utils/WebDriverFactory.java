package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {
    //инициализация драйвера
    public static WebDriver createWebDriver() {
        WebDriver driver;
        String prop = System.getProperty("browser", "chrome");
        if (prop.equals("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new ChromeDriver();
        }
        return driver;

    }

}
