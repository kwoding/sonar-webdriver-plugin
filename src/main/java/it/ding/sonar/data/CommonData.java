package it.ding.sonar.data;

import static java.util.Arrays.asList;

import java.util.List;

public class CommonData {

    private CommonData() {
    }

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
