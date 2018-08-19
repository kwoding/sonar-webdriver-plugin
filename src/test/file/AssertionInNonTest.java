import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TheInternetLoginPage extends Base {

    private static final String LOGIN_URL = "http://the-internet.herokuapp.com/login";
    private static final By USERNAME = By.name("#username");
    private static final By PASSWORD = By.cssSelector("#password");
    private static final By SUBMIT = By.name("submit");
    private static final By CONFIRMATION_TEXT =  By.cssSelector(".confirmationText");

    public TheInternetLoginPage(RemoteWebDriver driver) {
        super(driver);
    }

    public void openLoginScreen() throws InterruptedException {
        visit(LOGIN_URL);
    }

    public void login(String username, String password) {
        type(USERNAME, username);
        type(PASSWORD, password);
        click(SUBMIT);
        assertThat("abc", is("def")); // Noncompliant
    }

    public String getConfirmationText() {
        return getText(CONFIRMATION_TEXT);
    }
}
