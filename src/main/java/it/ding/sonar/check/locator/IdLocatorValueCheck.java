package it.ding.sonar.check.locator;

import static it.ding.sonar.data.CommonData.ID_LOCATOR_VALUE_CHECK;
import static it.ding.sonar.util.CommonUtil.getIdentifier;
import static it.ding.sonar.util.CommonUtil.getLocatorValueMapInAnnotation;
import static it.ding.sonar.util.CommonUtil.methodInvocationIsElementFinder;

import java.util.Map;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = ID_LOCATOR_VALUE_CHECK)
public class IdLocatorValueCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    private static final String ID_LOCATOR = "id";

    private static final String VALID_ID_LOCATOR_REGEX = "^[A-Za-z]+[\\w\\-:.]*$";

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
        if (methodInvocationIsElementFinder(tree) && !tree.arguments().isEmpty()) {
            String locatorStrategy = getIdentifier(tree).name();
            String locatorValue = ((LiteralTree) tree.arguments().get(0)).value();

            checkLocator(tree, locatorStrategy, locatorValue);
        }
    }

    private void checkLocator(ExpressionTree expressionTree, String locatorStrategy, String locatorValue) {
        String value = locatorValue.replace("\"", "");

        if (ID_LOCATOR.equalsIgnoreCase(locatorStrategy) && !value.matches(VALID_ID_LOCATOR_REGEX)) {
            context.reportIssue(this, expressionTree,
                "Invalid " + ID_LOCATOR + " locator");
        }
    }

}
