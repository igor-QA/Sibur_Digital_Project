package tests;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static config.WebDriverConfigHelper.isVideoOn;
import static helpers.AttachmentsHelper.*;
import static helpers.DriverHelper.configureSelenide;

public class TestBase {

    protected final SelenideElement name = $("[placeholder='Имя']"),
            email = $("[placeholder='E-mail']"),
            sub_message = $("[placeholder='Тема сообщения']"),
            text_message = $("[placeholder='Текст сообщения']"),
            submit = $("[type='submit']");
    protected final String SUCCESSFUL_TEXT = "СПАСИБО ЗА ВАШУ ИДЕЮ, ОНА ПОПАДЁТ В СООТВЕТСТВУЮЩЕЕ НАПРАВЛЕНИЕ И МЫ ОБЯЗАТЕЛЬНО РАССМОТРИМ ЕЁ!";

    @ExtendWith(AllureJunit5.class)
    @BeforeEach
    void setup() {
        configureSelenide(); }

    @AfterEach
    public void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        if (isVideoOn()) attachVideo(getSessionId());
        closeWebDriver();
    }
}
