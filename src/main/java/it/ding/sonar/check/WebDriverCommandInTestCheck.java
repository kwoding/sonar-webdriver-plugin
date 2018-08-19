package it.ding.sonar.check;

import static it.ding.sonar.data.CommonData.WEBDRIVER_COMMAND_IN_TEST_CHECK_KEY;
import static it.ding.sonar.util.CommonUtil.methodInvocationIsPartOfWebDriverPackage;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = WEBDRIVER_COMMAND_IN_TEST_CHECK_KEY)
public class WebDriverCommandInTestCheck extends BaseTestCheck {

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (methodInvocationIsPartOfWebDriverPackage(tree)) {
            context.reportIssue(this, tree, "Should not use WebDriver commands in Test classes.");
        }
    }

}
