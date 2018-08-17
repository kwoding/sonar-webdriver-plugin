package it.ding.sonar.check;

import static it.ding.sonar.util.CommonUtil.isPartOfWebDriverPackage;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.resolve.JavaSymbol.TypeJavaSymbol;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = "webdriver-method-invocation-in-test-check",
    name = "webdriver-method-invocation-in-test-check",
    description = "Avoid WebDriver method invocations in Test classes",
    priority = Priority.CRITICAL,
    tags = {"bug"})
public class WebDriverMethodInvocationInTestCheck extends BaseTestCheck {

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (!getIdentifier(tree).symbol().isUnknown()) {
            String fullyQualifiedName = ((TypeJavaSymbol) getIdentifier(tree).symbol().owner()).getFullyQualifiedName();

            if (isPartOfWebDriverPackage(fullyQualifiedName)) {
                context.reportIssue(this, tree, "Should not use WebDriver method invocations in Test classes.");
            }
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
