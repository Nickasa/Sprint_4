package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;

public class OrderPage {
    private WebDriver driver;

    public OrderPage(WebDriver driver){
        this.driver = driver;
    }

    //Верхняя кнопка "Заказать"
    private By orderTopButton = By.xpath(".//div[@class='Header_Nav__AGCXC']/button[text()='Заказать']");

    //Нижняя кнопка "Заказать"
    private By orderBottomButton = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button[text()='Заказать']");

    //Метод для получения XPath полей, на входе получает аргумент с куском xpath, который будет указан в качестве параметра в параметризованном тесте
    private By inputField (String fieldName){
        return By.xpath(".//input[@placeholder='"+ fieldName +"']");
    }

    //Метод для ввода значений полей, использует метод inputField, в качестве аргументов использует часть XPath искомого поля (для inputField) и значения для ввода в поле
    public void setFieldText (String fieldName, String fieldText){
        driver.findElement(inputField(fieldName)).sendKeys(fieldText);
    }

    //Выбор станции метро из выпадающего списка
    public void selectMetroStationFromDropDownList (String stationName) {
        driver.findElement(By.xpath(".//div[text()='" + stationName + "']")).click();
    }

    //Локатор для кнопки "Далее"
    private By buttonProceed = By.xpath(".//button[text()='Далее']");

    //Клик по кнопке "Далее"
    public void clickProceedButton(){
        driver.findElement(buttonProceed).click();
    }

    //Клик по верхней кнопке "Заказать"
    public void clickTopOrderButton() {
        driver.findElement(orderTopButton).click();
    }

    //Клик по нижней кнопке "Заказать"
    public void clickBottomOrderButton() {
        driver.findElement(orderBottomButton).click();
    }

    //Метод для выбор цвета самоката, в качестве аргумента получает цвет в параметризованном тесте, на основе которого происходит выбор
    public void selectScooterColor (String color){
        driver.findElement(By.id(color)).click();
    }

    //Клик в поле "* Срок аренды"
    public void clickRentDurationField(){
        driver.findElement(By.xpath(".//div[text()='* Срок аренды']")).click();
    }

    //Выбор срока аренды из выпадающего списка, в качестве аргумента параметр из теста ("сутки", "двое суток")
    public void selectRentDurationFromDropDownList(String duration){
        driver.findElement(By.xpath(".//div[text()='"+ duration +"']")).click();
    }

    //Нажатие кнопки "Enter" в поле выбора даты аренды, потому что иначе календарь не закроется и не дает кликнуть в следующее поле, другого решения не нашел
    public void clickEnterInOrderDateField(){
        driver.findElement(By.xpath(".//input[@placeholder='* Когда привезти самокат']")).sendKeys(Keys.ENTER);
    }

    //Клик на кнопку "Заказать"
    public void clickOrderConfirmationButton(){
        driver.findElement(By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']")).click();
    }

    //Клик на кнопку "Да" в модальном окне подтверждения заказа
    public void clickConfirmationButtonYes(){
        driver.findElement(By.xpath(".//button[text()='Да']")).click();
    }
    //Метод проверки, видно ли модальное окно "Заказ оформлен". Сперва пробовал сравнивать параметр "Заказ офформлен" в параметризованном тесте с получаемым через getText(), но в результате
    //выводится все текстовое содержимое окна "Заказ оформлен", и матчинг не происходит. Так же пробовал через матчер MatcherAssert.assertThat(actual, containsString()), но он почему-то не импортируется, хотя есть импорт hamcrest, пожтому
    //сделал через isDisplayed()
    public boolean isConfirmedOrderModalHeaderDisplayed(){
        return driver.findElement(By.xpath(".//div[@class='Order_ModalHeader__3FDaJ']")).isDisplayed();
    }

}
