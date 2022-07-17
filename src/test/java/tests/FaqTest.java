package tests;

import PageObject.HomePageScooter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FaqTest {
    private WebDriver driver;
    int number;
    String expected;

    public FaqTest(int number, String expected) {
        this.number = number;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] getFaqNumberAndAnswer() {
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
    //В методе setDriver() можно поменять ChromeDriver на FirefoxDriver, чтобы запустить тест в Mozilla Firefox вместо Chrome, как передавать значение driver в качестве параметра я не знаю, если это возможно, то прошу оставить фидбек
    @Before
    public void setDriver() {
        WebDriver driver = new ChromeDriver();
        this.driver = driver;
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    //Тест на соответсвие вопросам показываемых ответов, запускается как в Chrome, так и Mozilla Firefox
    @Test
    public void faqMatchingQuestionWithAnswersTest() {
        HomePageScooter objHomePageScooter = new HomePageScooter(driver);

        new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("rcc-confirm-button")));

        objHomePageScooter.clickAcceptCookiesButton();
        objHomePageScooter.faqButtonClick(number);

        new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id(String.format("accordion__panel-%s", number))));

        String actual = objHomePageScooter.getAnswerText(number);

        assertEquals(expected, actual);

    }

    @After
    public void teardown() {
        driver.quit();
    }

}



