package it.ding.sonar.check.locator;

import static it.ding.sonar.util.CommonUtil.getIdentifier;
import static it.ding.sonar.util.CommonUtil.getLocatorValueMapInAnnotation;
import static it.ding.sonar.util.CommonUtil.methodInvocationIsElementFinder;
import static java.util.Arrays.asList;

import java.util.List;
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

@Rule(key = "locator-css-value-check",
    name = "locator-css-value-check",
    description = "Avoid css locator tied to page layout",
    priority = Priority.MAJOR,
    tags = {"bug"})
public class LocatorCssValueCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    private static final List<String> CSS_LOCATORS = asList("cssSelector", "css");

    private static final String SPACE = " ";

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

        if (CSS_LOCATORS.contains(locatorStrategy.toLowerCase()) && value.contains(SPACE)) {
            context.reportIssue(this, expressionTree,
                "Avoid using " + locatorStrategy + " locator tied to page layout");
        }
    }

}
