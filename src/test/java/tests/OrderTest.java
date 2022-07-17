package tests;

import PageObject.OrderPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;

    String nameField;

    String lastNameField;

    String addressField;

    String metroStationField;

    String phoneNumber;

    String orderDateField;

    String orderDurationField;

    String scooterColor;

    String courierCommentField;

    boolean orderCreatedModalIsVisible;

    public OrderTest(String nameField, String lastNameField, String addressField, String metroStationField, String phoneNumber, String orderDateField, String orderDurationField, String scooterColor, String courierCommentField, boolean orderCreatedModalIsVisible) {
        this.nameField = nameField;
        this.lastNameField = lastNameField;
        this.addressField = addressField;
        this.metroStationField = metroStationField;
        this.phoneNumber = phoneNumber;
        this.orderDateField = orderDateField;
        this.orderDurationField = orderDurationField;
        this.scooterColor = scooterColor;
        this.courierCommentField =courierCommentField;
        this.orderCreatedModalIsVisible = orderCreatedModalIsVisible;
    }

    @Parameterized.Parameters
    public static Object[][] getFaqNumberAndAnswer() {
        return new Object[][]{
                {"Иван", "Иванов", "Первая", "Бульвар Рокоссовского", "88005553535", "17.07.2022", "сутки", "black", "Позвонить за 15 минут до доставки", true},
                {"Петр", "Петров", "Ул. Вторая", "Черкизовская", "31231231233", "19.07.2022", "двое суток", "grey", "Привезите побыстрее плз", true},
        };
    }

    @Before
    public void setDriver() {
        WebDriver driver = new FirefoxDriver();
        this.driver = driver;
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    //Тест падает с chromedriver на шаге подтверждения заказа (клик на кнопку "Да", в модальном окне "Хотите оформить заказ?"), с geckodriver (Mozilla Firefox) тест выполняется, через верхнюю кнопку "Заказать", здесь так же не
    //стал дублировать тесты отдельно на Chrome, поскольку в условии требуются рабочие тесты хоят бы на одном браузере, и смысла дублировать тесты из-за одной строки не вижу
    @Test
    public void orderScooterTestViaTopOrderButton() {
        OrderPage objOrderPage = new OrderPage(driver);
        objOrderPage.clickTopOrderButton();

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='Order_Header__BZXOb']")));

        objOrderPage.setFieldText("* Имя", nameField);
        objOrderPage.setFieldText("* Фамилия", lastNameField);
        objOrderPage.setFieldText("* Адрес: куда привезти заказ", addressField);
        objOrderPage.setFieldText("* Станция метро", metroStationField);
        objOrderPage.selectMetroStationFromDropDownList(metroStationField);
        objOrderPage.setFieldText("* Телефон: на него позвонит курьер", phoneNumber);
        objOrderPage.clickProceedButton();
        objOrderPage.setFieldText("* Когда привезти самокат", orderDateField);
        objOrderPage.clickEnterInOrderDateField();
        objOrderPage.clickRentDurationField();
        objOrderPage.selectRentDurationFromDropDownList("сутки");
        objOrderPage.selectScooterColor(scooterColor);
        objOrderPage.setFieldText("Комментарий для курьера", courierCommentField);
        objOrderPage.clickOrderConfirmationButton();
        objOrderPage.clickConfirmationButtonYes();
        boolean actualConfirmedModalHeader = objOrderPage.isConfirmedOrderModalHeaderDisplayed();
        assertEquals(actualConfirmedModalHeader, orderCreatedModalIsVisible);
    }

    //Тот же тест, но с точкой входа через нижнюю кнопку "Заказать". Не получилось параметризовать кнопки,
    //пробовал в качестве параметров передавать различающиеся части xpath кнопок и целый xpath в качестве строки, и потом подставлять в метод, элемент не находился
    @Test
    public void orderScooterTestViaBottomOrderButton() {
        OrderPage objOrderPage = new OrderPage(driver);

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("rcc-confirm-button")));

        WebElement element = driver.findElement(By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button[text()='Заказать']"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        objOrderPage.clickBottomOrderButton();

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='Order_Header__BZXOb']")));

        objOrderPage.setFieldText("* Имя", nameField);
        objOrderPage.setFieldText("* Фамилия", lastNameField);
        objOrderPage.setFieldText("* Адрес: куда привезти заказ", addressField);
        objOrderPage.setFieldText("* Станция метро", metroStationField);
        objOrderPage.selectMetroStationFromDropDownList(metroStationField);
        objOrderPage.setFieldText("* Телефон: на него позвонит курьер", phoneNumber);
        objOrderPage.clickProceedButton();
        objOrderPage.setFieldText("* Когда привезти самокат", orderDateField);
        objOrderPage.clickEnterInOrderDateField();
        objOrderPage.clickRentDurationField();
        objOrderPage.selectRentDurationFromDropDownList("сутки");
        objOrderPage.selectScooterColor(scooterColor);
        objOrderPage.setFieldText("Комментарий для курьера", courierCommentField);
        objOrderPage.clickOrderConfirmationButton();
        objOrderPage.clickConfirmationButtonYes();
        boolean actualConfirmedModalHeader = objOrderPage.isConfirmedOrderModalHeaderDisplayed();
        assertEquals(actualConfirmedModalHeader, orderCreatedModalIsVisible);
    }

    @After
    public void teardown() {
        driver.quit();
    }

}
