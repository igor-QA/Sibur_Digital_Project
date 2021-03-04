package tests;

import annotations.JiraIssue;
import annotations.Layer;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

@Layer("web")
@Owner("Pavlov Igor")
public class MainUiPageTests extends TestBase {

    @AllureId("#1547")
    @Test
    @Tag("ideas")
    @Feature("Test for Sibur.Digital")
    @Story("Проверить успешность отправки сообщения в разделе Идея")
    @Epic("Заполнение формы")
    @DisplayName("Заполнение и отправка формы в разделе Идея")
    public void shouldCheckIdeasPageBlockIdea() {
        step("Открыть страницу и перейти в раздел Ideas", () -> {
            open("https://sibur.digital/ideas");
            $(".page-ideas__type_ideas").click();
        });
        step("Ввести данные в форму обратной связи и нажать отправить", () -> {
            $(name).setValue("Test").pressTab();
            $(email).setValue("test@test.com").pressTab();
            $(sub_message).setValue("Attention").pressTab();
            $(text_message).setValue("I have a Lot of Ideas").pressTab();
            $(submit).pressEnter();
        });
        step("Проверить, что сообщение успешно отправилось", () -> {
            $("div.ideas-form__form-container").shouldHave(text(SUCCESSFUL_TEXT));
        });
    }
    
    @AllureId("#1546")
    @Test
    @Tag("cooperation")
    @JiraIssue("QC3-16")
    @Feature("Negative test for Sibur.Digital")
    @Story("Проверить успешность отправки сообщения в разделе Сотрудничество, где имя < 3 символов")
    @Epic("Заполнение формы")
    @DisplayName("Заполнение и отправка формы в разделе Сотрудниество")
    public void shouldCheckIdeasPageBlockCooperation() {
        step("Открыть сайт и перейти в раздел Сотрудниество", () -> {
            open("https://sibur.digital/ideas");
            $(".page-ideas__type_coop").click();
        });
        step("Ввести данные в форму обратной связи и нажать отправить", () -> {
            $(name).setValue("T").pressTab();  /* Некорректное имя */
            $(email).setValue("test@hotmail.com").pressTab();
            $(sub_message).setValue("Attention").pressTab();
            $(text_message).setValue("I can make the digital world better").pressTab();
            $(submit).pressEnter();
        });
        step("Проверить, что письмо о Сотрудничестве успешно отправилось", () -> {
            $("div.ideas-form__form-container").shouldHave(text(SUCCESSFUL_TEXT));
        });
    }
    
    @AllureId("#1545")
    @Test
    @Tag("distribution")
    @Feature("Test for Sibur.Digital")
    @Story("Проверить, что пользователь успешно подписался на рассылку")
    @Epic("Заполнение раздела - подписка")
    @DisplayName("Подписка на рассылку")
    public void shouldCheckSuccessfulSubscription() {
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
    
    @AllureId("#1548")
    @Test
    @Tag("search")
    @Feature("Test for Sibur.Digital")
    @Story("Проверить успешность поиска по сайту")
    @Epic("Выполнение поиска по сайту")
    @DisplayName("Поиск по сайту")
    public void shouldCheckSuccessfulSearchInfo() {
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
}