class Abc {

    private static final String WEB_HUB_URL = "http://localhost:4444/wd/hub";

    private static final By USERNAME = By.name("#username");

    private RemoteWebDriver driver;

    private DesiredCapabilities capabilities = new DesiredCapabilities();

    public void test() {
        capabilities.setBrowserName("chrome");
        driver = new RemoteWebDriver(new URL(WEB_HUB_URL), capabilities);

        Thread.sleep(1000);
        find(USERNAME).click();
        find(USERNAME).sendKeys("test");
    }

    private WebElement find(By locator) {
        return driver.findElement(locator);
    }
}
