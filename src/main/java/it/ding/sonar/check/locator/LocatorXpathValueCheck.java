package it.ding.sonar.check.locator;

import static it.ding.sonar.data.CommonData.LOCATOR_XPATH_VALUE_CHECK_KEY;
import static it.ding.sonar.util.CommonUtil.getIdentifier;
import static it.ding.sonar.util.CommonUtil.getLocatorValueMapInAnnotation;
import static it.ding.sonar.util.CommonUtil.methodInvocationIsElementFinder;

import java.util.Map;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = LOCATOR_XPATH_VALUE_CHECK_KEY,
    name = "locator-xpath-value-check",
    description = "Avoid xpath locator tied to page layout",
    priority = Priority.MAJOR,
    tags = {"bug"})
public class LocatorXpathValueCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    private static final String XPATH_LOCATOR = "xpath";

    private static final String RECOMMENDED_XPATH_LOCATOR_REGEX = "^//((?!/).)*";

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;

        scan(context.getTree());
    }

    @Override
    public void visitAnnotation(AnnotationTree tree) {
        Map<String, String> locatorsInAnnotation = getLocatorValueMapInAnnotation(tree);

        for (Map.Entry<String, String> locator : locatorsInAnnotation.entrySet()) {
            String locatorStrategy = locator.getKey();
            String locatorValue = locator.getValue();

            checkLocator(tree, locatorStrategy, locatorValue);
        }
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (methodInvocationIsElementFinder(tree)) {
            String locatorStrategy = getIdentifier(tree).name();

            String locatorValue = !tree.arguments().isEmpty()
                ? ((LiteralTree) tree.arguments().get(0)).value()
                : null;

            checkLocator(tree, locatorStrategy, locatorValue);
        }
    }

    private void checkLocator(ExpressionTree expressionTree, String locatorStrategy, String locatorValue) {
        String value = locatorValue.replace("\"", "");

        if (XPATH_LOCATOR.equalsIgnoreCase(locatorStrategy) && !value.matches(RECOMMENDED_XPATH_LOCATOR_REGEX)) {
            context.reportIssue(this, expressionTree,
                "Avoid using " + XPATH_LOCATOR + " locator tied to page layout");
        }
    }

}
