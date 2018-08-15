import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Abc {

    private static final String WEB_HUB_URL = "http://localhost:4444/wd/hub";

    private static final By USERNAME = By.name("#username"); // Noncompliant

    private RemoteWebDriver driver;

    private DesiredCapabilities capabilities = new DesiredCapabilities();

    private Example example = new Example();

    @Test
    public void test() {
        capabilities.setBrowserName("chrome"); // Noncompliant
        driver = new RemoteWebDriver(new URL(WEB_HUB_URL), capabilities);

        Thread.sleep(1000);
        find(USERNAME).click(); // Noncompliant
        find(USERNAME).sendKeys("test"); // Noncompliant

        findCoolStuff(USERNAME);
        driver.findElement(USERNAME); // Noncompliant
    }

    private WebElement find(By locator) {
        return driver.findElement(locator); // Noncompliant
    }

    private class Example {
        void findCoolStuff(By locator) {
            return driver.findElement(locator);
        }
    }
}
