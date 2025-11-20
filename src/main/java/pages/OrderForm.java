package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderForm {
    private final WebDriver webDriver;
    //Поле Имя
    private final By firstNameLocator = By.xpath("//input[@placeholder='* Имя']");
    //Поле Фамилия
    private final By lastNameLocator = By.xpath("//input[@placeholder='* Фамилия']");
    //После Адресс
    private final By addressLocator = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    //Поле Станця метро
    private final By metroStationLocator = By.xpath("//input[@placeholder='* Станция метро']");
    //Поле телефон
    private final By phoneLocator = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    //Поле Дата
    private final By dateLocator = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    //Поле Комментарий
    private final By commentLocator = By.xpath("//input[@placeholder='Комментарий для курьера']");
    //Кнопка продолжения заказа
    private final By nextButtonLocator = By.cssSelector("button.Button_Button__ra12g.Button_Middle__1CSJM");
    //Кнопка завершения заказа
    private final By completeNextButtonLocator = By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Заказать']");
    //Кнопка подвтерждения заказа
    private final By conformOrderButtonLocator = By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Да']");
    //Попап оформленного заказа
    private final By popupLocator = By.xpath("//div[contains(@class, 'Order_Modal__YZ-d3')]//div[contains(text(), 'Заказ оформлен')]");
    //Текст Про Аренду
    private final By textLocator = By.className("Order_Header__BZXOb");
    //Область Срока аренды
    private final By dropdownControlLocator = By.className("Dropdown-control");
    //Выпадающий список Сроков аренды
    private final By dropdownMenuLocator = By.className("Dropdown-menu");
    private final WebDriverWait wait;


    public OrderForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        //Инициализация ожидания
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
    }

    //Заполнение первой формы  заказа
    public void fillFirstStep(String name, String surname, String addr, String metro, String phoneNum) {

        webDriver.findElement(firstNameLocator).sendKeys(name);
        webDriver.findElement(lastNameLocator).sendKeys(surname);
        webDriver.findElement(addressLocator).sendKeys(addr);
        webDriver.findElement(metroStationLocator).sendKeys(metro);
        //Выбор нужной станции из выпадающего списка
        WebElement stationOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='select-search__select']//button[contains(., '" + metro + "')]")
                )
        );
        stationOption.click();
        webDriver.findElement(phoneLocator).sendKeys(phoneNum);
    }

    //Кнопка для продолжения офолрмления заказа
    public void clickNextButton() {
        var nextButton = webDriver.findElement(nextButtonLocator);
        nextButton.click();
    }

    //Кнопка завершения заказа
    public void completeOrderButton() {
        webDriver.findElement(completeNextButtonLocator).click();
    }

    //Кнопка подтверждения оформления заказа
    public void conformOrder() {
        webDriver.findElement(conformOrderButtonLocator).click();
    }

    //Проверка появаления Попапа с заказом
    public boolean isCheckOrderCompletePopupDisplayed() {
        var finalPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(popupLocator));
        return finalPopup.isDisplayed();
    }

    //Выбор чекбокса цвета
    public void clickCheckBoxIfExists(String checkBoxId) {
        var checkBox = webDriver.findElement(By.id(checkBoxId));
        if (!checkBox.isSelected()) {
            checkBox.click();
        }
    }

    //Массив цвета
    public void selectColors(String[] colors) {
        for (String color : colors) {
            clickCheckBoxIfExists(color);
        }
    }

    //Запослнение второй формы заказа
    public void fillSecondStep(String date, String rendPeriod, String[] colorIds, String comment) {
        var inputDate = webDriver.findElement(dateLocator);
        inputDate.sendKeys(date);
        webDriver.findElement(textLocator).click();
        selectRentalPeriod(rendPeriod);
        selectColors(colorIds);
        webDriver.findElement(commentLocator).sendKeys(comment);


    }


    //Выбор в выпадающем списке срока
    public void selectRentalPeriod(String periodText) {
        //Открытие списока
        openDropdown();

        //Выбор нужного элемента
        selectOption(periodText);
    }

    // Открытие списка
    public void openDropdown() {
        WebElement dropdownControl = wait.until(ExpectedConditions.elementToBeClickable(dropdownControlLocator));
        dropdownControl.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                dropdownMenuLocator
        ));
    }

    //Выбор пункта по тексту
    public void selectOption(String optionText) {
        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class, 'Dropdown-option') and text()='" + optionText + "']")
                )
        );
        option.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                dropdownMenuLocator
        ));
    }
}
