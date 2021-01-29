package tests;

import com.codeborne.selenide.Configuration;
import helpers.ConfigHelper;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;
import static io.qameta.allure.Allure.step;

public class LendingPageTests {
    private final String SUCCESSFUL_TEXT = "СПАСИБО ЗА ВАШУ ИДЕЮ, ОНА ПОПАДЁТ В СООТВЕТСТВУЮЩЕЕ НАПРАВЛЕНИЕ И МЫ ОБЯЗАТЕЛЬНО РАССМОТРИМ ЕЁ!";

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

    @Feature("Test for Sibur Digital")
    @Owner("Pavlov Igor")
    @Test
    @DisplayName("Проверить успешность отправки сообщения в разделе Идея")
    public void ShouldCheckIdeasPageBlockIdea() {
        step("Открыть страницу и перейти в раздел Ideas", () -> {
            open("https://sibur.digital/ideas");
            $(".page-ideas__type_ideas").click();
        });
        step("Ввести данные в форму обратной связи и нажать отправить", () -> {
            $("[placeholder='Имя']").setValue("Test").pressTab();
            $("[placeholder='E-mail']").setValue("test@test.com").pressTab();
            $("[placeholder='Тема сообщения']").setValue("Attention").pressTab();
            $("[placeholder='Текст сообщения']").setValue("I have a Lot of Ideas").pressTab();
            $("[type='submit']").pressEnter();
        });
        step("Проверить, что сообщение успешно отправилось", () -> {
            $("div.ideas-form__form-container").shouldHave(text(SUCCESSFUL_TEXT));
        });
    }

    @Test
    @DisplayName("Проверить успешность отправки сообщения в разделе Сотрудничество, где имя < 3 символов")
    public void ShouldCheckIdeasPageBlockCooperation() {
        step("Открыть сайт и перейти в рздел Сотрудниество", () -> {
            open("https://sibur.digital/ideas");
            $(".page-ideas__type_coop").click();
        });
        step("Ввести данные в форму обратной связи и нажать отправить", () -> {
            $("[placeholder='Имя']").setValue("T").pressTab();  /* Некорректное имя */
            $("[placeholder='E-mail']").setValue("test@hotmail.com").pressTab();
            $("[placeholder='Тема сообщения']").setValue("Attention").pressTab();
            $("[placeholder='Текст сообщения']").setValue("I can make the digital world better").pressTab();
            $("[type='submit']").pressEnter();
        });
        step("Проверить, что письмо о Сотрудничестве успешно отправилось", () -> {
            $("div.ideas-form__form-container").shouldHave(text(SUCCESSFUL_TEXT));
        });
    }

    @Test
    @DisplayName("Проверить, что пользователь успешно подписался на рассылку")
    public void ShouldCheckSuccessfulSubscription() {
        step("Открыть сайт проскролить вниз и перейти в раздел 'Подписаться на рассылку'", () -> {
            open("https://sibur.digital/about");
            $(".subscribe__text").scrollIntoView(true).click();
        });
        step("Ввести почту, согласиться с правилами и подписаться", () -> {
            $("[placeholder='e-mail']").setValue("test@test.com");
            $(".subscribe-popup__agreement-checkbox").click();
            $(byText("подписаться")).click();
        });
            step("Проверить, что пользователь успешно подписался", () -> {
            $(".subscribe-popup__success-text").shouldHave(text("ВЫ УСПЕШНО ПОДПИСАЛИСЬ"));
        });
    }
    @Test
    @DisplayName("Проверить успешность поиска по сайту")
    public void ShouldCheckSuccessfulSearchInfo() {
        step("Открыть сайт и перейти в раздел поиска по сайту ", () -> {
            open("https://sibur.digital/");
            $(".nav__circles").click();
        });
        step("Ввести данные для поиска, например 'Agile' и выполнить поиск", () -> {
            $("[placeholder='поиск']").setValue("Agile");
            $(".search__result-item-category").click();
        });
        step("Проверить, что поиск выполнился успешно", () -> {
        $(".article-header__title-text").should(visible);
        });
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