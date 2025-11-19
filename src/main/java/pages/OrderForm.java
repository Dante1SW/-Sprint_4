package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderForm {
    private WebDriver webDriver;
    private By firstNameLocator = By.xpath("//input[@placeholder='* Имя']");
    private By lastNameLocator = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressLocator = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroStationLocator = By.xpath("//input[@placeholder='* Станция метро']");
    private By phoneLocator = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By dateLocator = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By commentLocator = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private WebDriverWait wait;


    public OrderForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }


    public void fillFirstStep(String name, String surname, String addr, String metro, String phoneNum) {

        webDriver.findElement(firstNameLocator).sendKeys(name);
        webDriver.findElement(lastNameLocator).sendKeys(surname);
        webDriver.findElement(addressLocator).sendKeys(addr);
        webDriver.findElement(metroStationLocator).sendKeys(metro);

        WebElement stationOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='select-search__select']//button[contains(., '" + metro + "')]")
                )
        );
        stationOption.click();
        webDriver.findElement(phoneLocator).sendKeys(phoneNum);
    }

    public void clickNextButton() {
        var nextButton = webDriver.findElement(By.cssSelector("button.Button_Button__ra12g.Button_Middle__1CSJM"));
        nextButton.click();
    }

    public void completeOrderButton() {
        webDriver.findElement(By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Заказать']")).click();
    }

    public void conformOrder() {
        webDriver.findElement(By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Да']")).click();
    }

    public boolean isCheckOrderCompletePopupDisplayed() {
        var finalPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Order_Modal__YZ-d3")));
        return finalPopup.isDisplayed();
    }

    public void clickCheckBoxIfExists(String checkBoxId) {
        var checkBox = webDriver.findElement(By.id(checkBoxId));
        if (!checkBox.isSelected()) {
            checkBox.click();
        }
    }

    public void selectColors(String[] colors) {
        for (String color : colors) {
            clickCheckBoxIfExists(color);
        }
    }


    public void fillSecondStep(String date, String rendPeriod, String[] colorIds, String comment) {
        var inputDate = webDriver.findElement(dateLocator);
        inputDate.sendKeys(date);
        webDriver.findElement(By.className("Order_Header__BZXOb")).click();
        selectRentalPeriod(rendPeriod);
        selectColors(colorIds);
        webDriver.findElement(commentLocator).sendKeys(comment);


    }


    // Основной метод выбора опции из dropdown
    public void selectRentalPeriod(String periodText) {
        // Открываем dropdown
        openDropdown();

        // Выбираем нужную опцию
        selectOption(periodText);
    }

    // Открытие dropdown
    private void openDropdown() {
        WebElement dropdownControl = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.className("Dropdown-control")
                )
        );
        dropdownControl.click();

        // Ждем открытия меню
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Dropdown-menu")
        ));
    }

    // Выбор опции по тексту
    private void selectOption(String optionText) {
        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class, 'Dropdown-option') and text()='" + optionText + "']")
                )
        );
        option.click();
        // Ждем закрытия dropdown
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.className("Dropdown-menu")
        ));
    }


}