package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import utils.WebDriverFactory;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static utils.Constants.*;

@RunWith(Parameterized.class)
public class QuestionTest {
    private WebDriver webDriver;
    private final int id;
    private final String expectedValue;

    public QuestionTest(int id, String expectedValue) {
        this.id = id;
        this.expectedValue = expectedValue;
    }
    //Массив данных для теста
    @Parameterized.Parameters
    public static Object[][] testData() {
        return new Object[][]{
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        };
    }
    //Выбор браузера и запуск главной страницы
    @Before
    public void startUp() {
        webDriver = WebDriverFactory.createWebDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15)); // добавил неявное ожидание 15 секунд
        webDriver.manage().window().maximize();
        webDriver.get(MAIN_URL);

    }
    //Тест выпадающего списка
    @Test
    public void answerTest() {
        MainPage mainPage = new MainPage(webDriver);
        String actual = mainPage.clickAndGetAnswer(id);
        assertEquals("Текст не соотвествет ожиданию", expectedValue, actual);
    }
    //Хакрытие браузера
    @After
    public void tearDown() {

        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
