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
    //Кнопка куки
    private final By cookieAcceptButtonLocator = By.id("rcc-confirm-button");
    //Верхняя кнопка "Заказать"
    private final By buttonUpLocator = By.cssSelector("button.Button_Button__ra12g");
    //Нижняя кнопка "Заказать"
    private final By buttonDownLocator = By.xpath("(//button[text()='Заказать'])[2]");

    public MainPage(WebDriver webDriver) {

        this.webDriver = webDriver;
        // явное ожидание клика/видимости до 5 секунд
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
    }
        // Раскрытие вопросы в всплывающем списке
    public void clickQuestion(int id) {
        //Принятие куки
        acceptCookiesIfNeeded();
        By questionId = By.id(QUESTION_TEMPLATE + id);
        //Ожидание пояления элемента Вопрос
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(questionId));
        //Скролл до элемента и клик
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
        if (element.isDisplayed() && element.isEnabled()) {
            element.click();
        } else {
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element);

        }
    }
    //Принятие куки
    public void acceptCookiesIfNeeded() {
        var buttons = webDriver.findElements(cookieAcceptButtonLocator); // поиск кнопки принятия куки
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            buttons.get(0).click();
        }
    }
    // получение ответа в спывающем списке
    public String getAnswer(int id) {
        By answer = By.id(String.format(ANSWER_TEMPLATE + id));
        wait.until(ExpectedConditions.visibilityOfElementLocated(answer)); // добавил ожидание текста ответа
        return webDriver.findElement(answer).getText();
    }
    //Комьинирование раскрытия вопроса и получения ответа
    public String clickAndGetAnswer(int id) {
        clickQuestion(id);
        return getAnswer(id);
    }
    //нажатие на верхнюю кнопку "Заказать"
    public void clickUpperOrderButton() {
        var buttonUp = webDriver.findElement(buttonUpLocator);
        buttonUp.click();
    }
    //Нажатие на нижнюю кнопку "Заказать"
    public void clickDownOrderButton() {
        var buttonDown = webDriver.findElement(buttonDownLocator);
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", buttonDown);
        buttonDown.click();
    }

}


