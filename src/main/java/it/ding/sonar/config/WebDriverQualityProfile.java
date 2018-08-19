package it.ding.sonar.config;

import static it.ding.sonar.data.CommonData.ASSERTIONS_IN_NON_TEST_CHECK_KEY;
import static it.ding.sonar.data.CommonData.EXPLICIT_WAIT_IN_TEST_CHECK_KEY;
import static it.ding.sonar.data.CommonData.HARD_CODED_SLEEP_CHECK_KEY;
import static it.ding.sonar.data.CommonData.IMPLICIT_WAIT_CHECK_KEY;
import static it.ding.sonar.data.CommonData.CSS_LOCATOR_VALUE_CHECK;
import static it.ding.sonar.data.CommonData.LOCATOR_STRATEGY_BY_LINK_TEXT_AND_TAG_NAME_CHECK_KEY;
import static it.ding.sonar.data.CommonData.LOCATOR_STRATEGY_BY_XPATH_CHECK_KEY;
import static it.ding.sonar.data.CommonData.XPATH_LOCATOR_VALUE_CHECK;
import static it.ding.sonar.data.CommonData.REPOSITORY_KEY;
import static it.ding.sonar.data.CommonData.WEBDRIVER_COMMAND_IN_TEST_CHECK_KEY;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

public class WebDriverQualityProfile implements BuiltInQualityProfilesDefinition {

    private static final String QUALITY_PROFILE_NAME = "WebDriver";
    private static final String LANGUAGE_JAVA = "java";

    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(QUALITY_PROFILE_NAME, LANGUAGE_JAVA);
        profile.setDefault(true);

        profile.activateRule(REPOSITORY_KEY, CSS_LOCATOR_VALUE_CHECK);
        profile.activateRule(REPOSITORY_KEY, LOCATOR_STRATEGY_BY_LINK_TEXT_AND_TAG_NAME_CHECK_KEY);
        profile.activateRule(REPOSITORY_KEY, LOCATOR_STRATEGY_BY_XPATH_CHECK_KEY);
        profile.activateRule(REPOSITORY_KEY, XPATH_LOCATOR_VALUE_CHECK);
        profile.activateRule(REPOSITORY_KEY, EXPLICIT_WAIT_IN_TEST_CHECK_KEY);
        profile.activateRule(REPOSITORY_KEY, HARD_CODED_SLEEP_CHECK_KEY);
        profile.activateRule(REPOSITORY_KEY, IMPLICIT_WAIT_CHECK_KEY);
        profile.activateRule(REPOSITORY_KEY, WEBDRIVER_COMMAND_IN_TEST_CHECK_KEY);
        profile.activateRule(REPOSITORY_KEY, ASSERTIONS_IN_NON_TEST_CHECK_KEY);

        profile.done();
    }
}
