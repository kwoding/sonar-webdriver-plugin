import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.Test;

public class TheInternetLoginPage extends Base {

    private static final String LOGIN_URL = "http://the-internet.herokuapp.com/login";
    private static final By USERNAME = By.name("#username");
    private static final By PASSWORD = By.cssSelector("#password");
    private static final By SUBMIT = By.linkText("submit"); // Noncompliant
    private static final By CONFIRMATION_TEXT = By.xpath("/confirmation/book/message[text()]");

    @FindBy(tagName = "search") // Noncompliant
    private WebElement searchBox;

    @FindBy(how = How.PARTIAL_LINK_TEXT, using = ".search") // Noncompliant
    private WebElement searchSubmitButton;

    public TheInternetLoginPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Test
    public void openLoginScreen() throws InterruptedException {
        visit(LOGIN_URL);
    }

    public void login(String username, String password) {
        type(USERNAME, username);
        type(PASSWORD, password);
        click(SUBMIT);
    }

    public String getConfirmationText() {
        return getText(CONFIRMATION_TEXT);
    }
}
