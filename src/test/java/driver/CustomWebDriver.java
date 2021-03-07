package driver;

import com.codeborne.selenide.WebDriverProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static config.WebDriverConfigHelper.*;

public class CustomWebDriver implements WebDriverProvider {

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull DesiredCapabilities capabilities) {
        addListener("AllureSelenide", new AllureSelenide().screenshots(false).savePageSource(false));

        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVNC", true);

        if (isVideoOn()) {
            capabilities.setCapability("enableVideo", true);
            capabilities.setCapability("videoFrameRate", 24);
        }

        if (isRemoteWebDriver()) {
            switch (getBrowserName()) {
                case "chrome":
                    capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                    WebDriverManager.chromedriver().setup();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    // todo
                    break;
            }
            return getRemoteWebDriver(capabilities);
        } else {
            return getLocalChromeDriver();
        }
    }

    private ChromeDriver getLocalChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(getChromeOptions());
    }

    private WebDriver getRemoteWebDriver(DesiredCapabilities capabilities) {
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(getRemoteWebDriverUrl(), capabilities);
        remoteWebDriver.setFileDetector(new LocalFileDetector());

        return remoteWebDriver;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--disable-gpu");
//        chromeOptions.addArguments("--headless");

        return chromeOptions;
    }

    private URL getRemoteWebDriverUrl() {
        try {
            return new URL(getWebRemoteDriver());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

/*if (isEmpty(System.getProperty("selenide.browser"))) {
            Configuration.browser = "chrome";
        }
        if (isRemote()) {
            Configuration.remote = String.valueOf(getURL());
}*/