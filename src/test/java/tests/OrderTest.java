package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.OrderForm;
import utils.WebDriverFactory;

import java.time.Duration;

import static org.junit.Assert.assertTrue;
import static utils.Constants.*;

public class OrderTest {
    private MainPage mainPage;
    private OrderForm orderForm;
    private WebDriver webDriver;

    //Выбор браузера и запуск главной страницы
    @Before
    public void startUp() {
        webDriver = WebDriverFactory.createWebDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15)); // добавил неявное ожидание 15 секунд
        webDriver.manage().window().maximize();
        webDriver.get(MAIN_URL);
        mainPage = new MainPage(webDriver);
        orderForm = new OrderForm(webDriver);
    }
    //Тест для верхней кнопки Заказать
    @Test
    public void upOrderForm() {
        mainPage.acceptCookiesIfNeeded();
        mainPage.clickUpperOrderButton();
        orderForm.fillFirstStep("Дима", "Иванов", "ул. Потапова д 1", "Кожуховская", "81234567899");
        orderForm.clickNextButton();
        orderForm.fillSecondStep("30.12.25", "трое суток", new String[]{"black"}, "позвонить заранее");
        orderForm.completeOrderButton();
        orderForm.conformOrder();
        assertTrue(orderForm.isCheckOrderCompletePopupDisplayed());
    }
    //Тест для нижней кнопки Заказать
    @Test
    public void DownOrderForm() {
        mainPage.acceptCookiesIfNeeded();
        mainPage.clickDownOrderButton();
        orderForm.fillFirstStep("Вася", "Васильев", "пр-т Анкина д 12", "Аннино", "89876543211");
        orderForm.clickNextButton();
        orderForm.fillSecondStep("25.11.25", "двое суток", new String[]{"grey"}, "скорее");
        orderForm.completeOrderButton();
        orderForm.conformOrder();
        assertTrue("Окно о создании заказа не появилось", orderForm.isCheckOrderCompletePopupDisplayed());
    }
    //Закрытие браузера
    @After
    public void tearDown() {

        if (webDriver != null) {
            webDriver.quit();
        }

    }
}
