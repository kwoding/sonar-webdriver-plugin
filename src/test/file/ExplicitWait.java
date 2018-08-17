import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;
import org.junit.Test;

public class ExplicitWait {

    private RemoteWebDriver driver;

    public Base(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @Test
    public void testSomething() {
        capabilities.setBrowserName("chrome");
        driver = new RemoteWebDriver(new URL(WEB_HUB_URL), capabilities);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String loginUrl = "http://the-internet.herokuapp.com/login";
        By username = By.cssSelector("#username");
        By password = By.cssSelector("#password");
        By submit = By.cssSelector("button[type=\"submit\"]");

        driver.get(loginUrl);

        find(username).sendKeys("user");
        find(password).sendKeys("password");
        find(submit).click();
        getVisibleText(username);
    }

    public WebElement find(By locator) {
        return driver.findElement(locator);
    }

    public void click(By locator) {
        find(locator).click();
    }

    public void type(By locator, String inputText) {
        find(locator).sendKeys(inputText);
    }

    public void select(String inputText, By locator) {
        Select selectItem = new Select(find(locator));
        selectItem.selectByVisibleText(inputText);
    }

    public void clear(By locator) {
        find(locator).clear();
    }

    public String getText(By locator) {
        return find(locator).getText();
    }

    public boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }

    public boolean isDisplayed(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout); // Noncompliant
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    public boolean isDisplayed(WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout); // Noncompliant
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    public boolean textPresentInElement(By locator, String searchString, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout); // Noncompliant
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, searchString));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    public String getVisibleText(By locator) {
        Wait wait = new FluentWait(driver) // Noncompliant
            .withTimeout(30, SECONDS)
            .pollingEvery(5, SECONDS)
            .ignoring(NoSuchElementException.class);

        WebElement foo = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return find(locator);
            }
        });

        return foo.getText();
    }

    public String getAttributeValue(By locator, String attribute) {
        return find(locator).getAttribute(attribute);
    }
}
