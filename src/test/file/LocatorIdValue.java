import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.junit.Test;

public class TheInternetLoginPage extends Base {

    private static final String LOGIN_URL = "http://the-internet.herokuapp.com/login";
    private static final By INPUT_FIELD_ONE = By.id("#id"); // Noncompliant
    private static final By USERNAME = By.id("1startwithnumber"); // Noncompliant
    private static final By PASSWORD = By.id("_"); // Noncompliant
    private static final By SUBMIT = By.id("#"); // Noncompliant
    private static final By INPUT_FIELD_FIVE = By.id("invalid*id"); // Noncompliant
    private static final By INPUT_FIELD_SIX = By.id("id with spaces"); // Noncompliant
    private static final By INPUT_FIELD_SEVEN = By.id("validID");
    private static final By INPUT_FIELD_EIGHT = By.id("simpleid");
    private static final By INPUT_FIELD_NINE = By.id("foo-bar");
    private static final By INPUT_FIELD_TEN = By.id("foo:bar");
    private static final By INPUT_FIELD_ELEVEN = By.id("foo_bar");
    private static final By INPUT_FIELD_TWELVE = By.id("foo.bar");
    private static final By INPUT_FIELD_THIRTEEN = By.id("a123456789");
    private static final By CONFIRMATION_TEXT = By.id("a1-_:.r2D2");

    @FindBy(id = "id with spaces") // Noncompliant
    private WebElement searchBox;

    @FindBy(how = How.ID, using = "invalid*id") // Noncompliant
    private WebElement searchSubmitButton;

    @FindBy(id = "#") // Noncompliant
    private WebElement cancelButton;

    @FindBy(xpath = "//*[@id=\"element_id\"]")
    private WebElement cancelText;

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
    }

    public String getConfirmationText() {
        return getText(CONFIRMATION_TEXT);
    }
}
