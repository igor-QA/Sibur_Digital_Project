package tests;

import com.codeborne.selenide.Configuration;
import helpers.ConfigHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;


public class TestBase {
    protected final String SUCCESSFUL_TEXT = "СПАСИБО ЗА ВАШУ ИДЕЮ, ОНА ПОПАДЁТ В СООТВЕТСТВУЮЩЕЕ НАПРАВЛЕНИЕ И МЫ ОБЯЗАТЕЛЬНО РАССМОТРИМ ЕЁ!";

    @BeforeAll
    static void setup() {
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Configuration.startMaximized = true;
        Configuration.remote = ConfigHelper.getURL();
        Configuration.browserCapabilities = capabilities;
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        /*if (isEmpty(System.getProperty("selenide.browser"))) {
            Configuration.browser = "chrome";
        }
        if (isRemote()) {
            Configuration.remote = String.valueOf(getRemoteUrl());
        }*/
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