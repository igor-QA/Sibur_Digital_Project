package helpers;

import com.codeborne.selenide.Configuration;
import driver.CustomWebDriver;
import io.qameta.allure.selenide.AllureSelenide;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

public class DriverHelper {

    public static void configureSelenide() {
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));

        Configuration.browser = CustomWebDriver.class.getName();
        Configuration.baseUrl = "https://sibur.digital/";
        Configuration.startMaximized = true;
        Configuration.timeout = 10000;
        //Configuration.browserSize = "1600x1200";

    }
}

/*
        if (isEmpty(System.getProperty("selenide.browser"))) {
         Configuration.browser = "chrome";
         }

         if (isRemote()) {
         Configuration.remote = String.valueOf(getRemoteUrl());
         }
}

 */
