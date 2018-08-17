package it.ding.sonar.data;

import static java.util.Arrays.asList;

import java.util.List;

public class CommonData {

    private CommonData() {
    }

    public static final String REPOSITORY_KEY = "sonar-webdriver-plugin";

    public static final String LOCATOR_CSS_VALUE_CHECK_KEY = "locator-css-value-check";
    public static final String LOCATOR_STRATEGY_BY_LINK_TEXT_AND_TAG_NAME_CHECK_KEY = "locator-strategy-by-link-text-and-tag-name-check";
    public static final String LOCATOR_STRATEGY_BY_XPATH_CHECK_KEY = "locator-strategy-xpath-check";
    public static final String LOCATOR_XPATH_VALUE_CHECK_KEY = "locator-xpath-value-check";
    public static final String EXPLICIT_WAIT_IN_TEST_CHECK_KEY = "explicit-wait-in-test-check";
    public static final String HARD_CODED_SLEEP_CHECK_KEY = "hard-coded-sleep-check";
    public static final String IMPLICIT_WAIT_CHECK_KEY = "implicit-wait-check";
    public static final String WEBDRIVER_COMMAND_IN_TEST_CHECK_KEY = "webdriver-command-in-test-check";

    public static final String SELENIUM_PACKAGE_NAME = "org.openqa.selenium";
    public static final String APPIUM_PACKAGE_NAME = "io.appium";
    public static final String JUNIT_PACKAGE_NAME = "org.junit";
    public static final String TESTNG_PACKAGE_NAME = "org.testng";
    public static final String CUCUMBER_PACKAGE_NAME = "cucumber.api";
    public static final String BY_OBJECT_NAME = "By";
    public static final String FIND_ELEMENT_METHOD_REGEX = "findElement[s]*By";
    public static final String FIND_BY_ANNOTATION_NAME = "FindBy";
    public static final String HOW_PROPERTY = "how";
    public static final String USING_PROPERTY = "using";
    public static final List<String> LOCATORS_RECOMMENDED = asList(
        "cssSelector",
        "className",
        "id",
        "name"
    );
}
