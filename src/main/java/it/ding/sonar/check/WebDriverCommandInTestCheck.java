package it.ding.sonar.check;

import static it.ding.sonar.data.CommonData.WEBDRIVER_COMMAND_IN_TEST_CHECK_KEY;
import static it.ding.sonar.util.CommonUtil.methodInvocationIsPartOfWebDriverPackage;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = WEBDRIVER_COMMAND_IN_TEST_CHECK_KEY,
    name = "webdriver-command-in-test-check",
    description = "Avoid WebDriver commands in Test classes",
    priority = Priority.CRITICAL,
    tags = {"bug"})
public class WebDriverCommandInTestCheck extends BaseTestCheck {

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (methodInvocationIsPartOfWebDriverPackage(tree)) {
            context.reportIssue(this, tree, "Should not use WebDriver commands in Test classes.");
        }
    }

    private static IdentifierTree getIdentifier(MethodInvocationTree mit) {
        // methodSelect can only be Tree.Kind.IDENTIFIER or Tree.Kind.MEMBER_SELECT
        if (mit.methodSelect().is(Tree.Kind.IDENTIFIER)) {
            return (IdentifierTree) mit.methodSelect();
        }
        return ((MemberSelectExpressionTree) mit.methodSelect()).identifier();
    }

}
