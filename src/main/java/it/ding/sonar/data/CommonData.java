package it.ding.sonar.data;

import static java.util.Arrays.asList;

import java.util.List;

public class CommonData {

    private CommonData() {
    }

    public static final String REPOSITORY_KEY = "sonar-webdriver-plugin";

    public static final String METHOD_KIND = "METHOD";
    public static final List<String> TEST_ANNOTATION_NAMES = asList("Test", "Given", "When", "Then");
    public static final String CSS_LOCATOR_VALUE_CHECK = "css-locator-value-check";
    public static final String LOCATOR_STRATEGY_BY_LINK_TEXT_AND_TAG_NAME_CHECK_KEY = "locator-strategy-by-link-text-and-tag-name-check";
    public static final String LOCATOR_STRATEGY_BY_XPATH_CHECK_KEY = "locator-strategy-by-xpath-check";
    public static final String XPATH_LOCATOR_VALUE_CHECK = "xpath-locator-value-check";
    public static final String ID_LOCATOR_VALUE_CHECK = "id-locator-value-check";
    public static final String CLASS_NAME_LOCATOR_VALUE_CHECK = "class-name-locator-value-check";
    public static final String EXPLICIT_WAIT_IN_TEST_CHECK_KEY = "explicit-wait-in-test-check";
    public static final String HARD_CODED_SLEEP_CHECK_KEY = "hard-coded-sleep-check";
    public static final String IMPLICIT_WAIT_CHECK_KEY = "implicit-wait-check";
    public static final String WEBDRIVER_COMMAND_IN_TEST_CHECK_KEY = "webdriver-command-in-test-check";
    public static final String ASSERTIONS_IN_NON_TEST_CHECK_KEY = "assertions-in-non-test-check";

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

    public static final String[] WEBDRIVER_PACKAGE_NAMES = {
        "org.openqa.selenium",
        "io.appium"
    };

    public static final String[] TEST_PACKAGE_NAMES = {
        "org.junit",
        "org.testng",
        "cucumber.api"
    };


    public static final String[] ASSERTION_METHOD_NAMES = {
        "verify",
        "assert",
        "fail",
        "expect"
    };

    public static final String[] ASSERTION_PACKAGE_NAMES = {
        "org.junit",
        "org.hamcrest",
        "org.assertj",
        "org.fest",
        "com.google.common.truth",
        "io.vertx"
    };
}
