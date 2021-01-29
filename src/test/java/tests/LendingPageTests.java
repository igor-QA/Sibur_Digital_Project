package tests;

import com.codeborne.selenide.Configuration;
import helpers.ConfigHelper;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;

public class LendingPageTests {
    final String SUCCESSFUL_TEXT = "СПАСИБО ЗА ВАШУ ИДЕЮ, ОНА ПОПАДЁТ В СООТВЕТСТВУЮЩЕЕ НАПРАВЛЕНИЕ И МЫ ОБЯЗАТЕЛЬНО РАССМОТРИМ ЕЁ!";

    @BeforeAll
    static void setup() {
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        Configuration.startMaximized = true;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.remote = ConfigHelper.getURL();
        Configuration.browserCapabilities = capabilities;


    }
    @DisplayName("Проверить успешную отправку сообщения в разделе Идея")
    @Test
    void ShouldCheckIdeasPageBlockIdea() {
        open("https://sibur.digital/ideas");
        $(".page-ideas__type_ideas").click();
        $("[placeholder='Имя']").setValue("Test").pressTab();
        $("[placeholder='E-mail']").setValue("test@test.com").pressTab();
        $("[placeholder='Тема сообщения']").setValue("Attention").pressTab();
        $("[placeholder='Текст сообщения']").setValue("I have a Lot of Ideas").pressTab();
        $("[type='submit']").pressEnter();
        $("div.ideas-form__form-container").shouldHave(text(SUCCESSFUL_TEXT));
    }
    @DisplayName("Проверить успешную отправку сообщения в разделе Соотрудничество, где имя < 3 символов")
    @Test
    void ShouldCheckIdeasPageBlockCooperation() {
        open("https://sibur.digital/ideas");
        $(".page-ideas__type_coop").click();
        $("[placeholder='Имя']").setValue("T").pressTab();  /* Некорректное имя */
        $("[placeholder='E-mail']").setValue("test@hotmail.com").pressTab();
        $("[placeholder='Тема сообщения']").setValue("Attention").pressTab();
        $("[placeholder='Текст сообщения']").setValue("I can make the digital world better").pressTab();
        $("[type='submit']").pressEnter();
        $("div.ideas-form__form-container").shouldHave(text(SUCCESSFUL_TEXT));
    }
    @Test
    void ShouldCheckSuccessfulSubscription() {
        open("https://sibur.digital/about");
        $(".subscribe__text").scrollIntoView(true).click();
        $("[placeholder='e-mail']").setValue("test@test.com");
        $(".subscribe-popup__agreement-checkbox").click();
        $(byText("подписаться")).click();
        $(".subscribe-popup__success-text").shouldHave(text("ВЫ УСПЕШНО ПОДПИСАЛИСЬ"));
    }
    @Test
    void ShouldCheckSuccessfulSearchInfo() {
        open("https://sibur.digital/");
        $(".nav__circles").click();
        $("[placeholder='поиск']").setValue("Agile");
        $(".search__result-item-category").click();
        $(".article-header__title-text").should(visible);
    }

    @AfterEach
    public void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        attachVideo();

        closeWebDriver();
    }
}

