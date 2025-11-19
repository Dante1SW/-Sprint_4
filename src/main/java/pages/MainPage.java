package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.Constants.*;

public class MainPage {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private final By cookieAcceptButtonLocator = By.id("rcc-confirm-button");
    private final By buttonUpLocator = By.cssSelector("button.Button_Button__ra12g");
    private final By buttonDownLocator = By.xpath("(//button[text()='Заказать'])[2]");

    public MainPage(WebDriver webDriver) {

        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));  // добавил явное ожидание клика/видимости до 5 секунд
    }

    public void clickQuestion(int id) {

        acceptCookiesIfNeeded(); // добавил автопринятие куки
        By questionId = By.id(QUESTION_TEMPLATE + id);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(questionId)); // добавил ожидание кликабельности вопроса
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element); // добавил прокрутку к вопросу в центр экрана
        if (element.isDisplayed() && element.isEnabled()) {
            element.click(); // попытка обычного клика
        } else {
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element); // добавил запасной клик через JS, если элемент перекрыт/недоступен

        }
    }

    private void acceptCookiesIfNeeded() {
        var buttons = webDriver.findElements(cookieAcceptButtonLocator); // поиск кнопки принятия куки
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            buttons.get(0).click();
        }
    }

    public String getAnswer(int id) {
        By answer = By.id(String.format(ANSWER_TEMPLATE + id));
        wait.until(ExpectedConditions.visibilityOfElementLocated(answer)); // добавил ожидание текста ответа
        return webDriver.findElement(answer).getText();
    }

    public String clickAndGetAnswer(int id) {
        clickQuestion(id);
        return getAnswer(id);
    }

    public void clickUpperOrderButton() {
        var buttonUp = webDriver.findElement(buttonUpLocator);
        buttonUp.click();
    }

    public void clickDownOrderButton() {
        var buttonDown = webDriver.findElement(buttonDownLocator);
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", buttonDown);
        buttonDown.click();
    }

}


