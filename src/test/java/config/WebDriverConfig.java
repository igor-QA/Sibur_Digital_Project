package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:local.properties"
})
public interface WebDriverConfig extends Config {

    @Key("remote.driver.url")
    String remoteDriverUrl();

    @Key("remote.driver.user")
    String remoteDriverUser();

    @Key("remote.driver.password")
    String remoteDriverPassword();

    @Key("remote.browser.name")
    String browserName();

    @Key("video")
    Boolean isVideo();

}
