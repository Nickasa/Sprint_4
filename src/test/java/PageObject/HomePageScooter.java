package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePageScooter {
    private WebDriver driver;

    //Локатор для клика по кнопке "да все привыкли"
    private By acceptCookiesButton = By.id("rcc-confirm-button");

    //Клик на кнопку "да все привыкли", чтобы поле не мешало выбору кнопок с вопросами
    public void clickAcceptCookiesButton() {
        driver.findElement(acceptCookiesButton).click();
    }

    //Конструктор для инициализации драйвера в тесте
    public HomePageScooter(WebDriver driver){
        this.driver = driver;
    }

    //Клик по кнопке с вопросом, в качестве аргумента передается номер кнопки
    public void faqButtonClick (int questionNumber) {
        driver.findElement(By.id(String.format("accordion__heading-%s", questionNumber))).click();
    }

    //Получение текста ответа, в качестве аргумента передается номер кнопки
    public String getAnswerText (int answerNumber) {
        return driver.findElement(By.id(String.format("accordion__panel-%s", answerNumber ))).getText();

    }

}
