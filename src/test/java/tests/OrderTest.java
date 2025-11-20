package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.OrderForm;
import utils.WebDriverFactory;

import java.time.Duration;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static utils.Constants.*;

@RunWith(Parameterized.class)
public class OrderTest {
    private MainPage mainPage;
    private OrderForm orderForm;
    private WebDriver webDriver;
    private final String orderButtonType;
    private final String name;
    private final String surname;
    private final String address;
    private final String metroStation;
    private final String phoneNumber;
    private final String deliveryDate;
    private final String rentalPeriod;
    private final String[] colors;
    private final String comment;

    public OrderTest(String orderButtonType, String name, String surname, String address,
                     String metroStation, String phoneNumber, String deliveryDate,
                     String rentalPeriod, String[] colors, String comment) {
        this.orderButtonType = orderButtonType;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.colors = colors;
        this.comment = comment;
    }

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
    //Параметризация наборов тестовых данных
    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {
                        "upper",  //Параметр для выбора кнопки
                        "Дима", "Иванов", "ул. Потапова д 1", "Кожуховская", "81234567899",
                        "30.12.25", "трое суток", new String[]{"black"}, "позвонить заранее"
                },
                {
                        "down",  //Параметр для выбора кнопки
                        "Вася", "Васильев", "пр-т Анкина д 12", "Аннино", "89876543211",
                        "25.11.25", "двое суток", new String[]{"grey"}, "скорее"
                }
        };
    }

    //Тест с выбором кнопки и тестовыми данными
    @Test
    public void orderFormTest() {
        mainPage.acceptCookiesIfNeeded();
        if ("upper".equals(orderButtonType)) {
            mainPage.clickUpperOrderButton();
        } else {
            mainPage.clickDownOrderButton();
        }

        orderForm.fillFirstStep(name, surname, address, metroStation, phoneNumber);
        orderForm.clickNextButton();
        orderForm.fillSecondStep(deliveryDate, rentalPeriod, colors, comment);
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
