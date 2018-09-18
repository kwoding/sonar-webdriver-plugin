package it.ding.sonar.check.locator;

import static it.ding.sonar.data.CommonData.CLASS_NAME_LOCATOR_VALUE_CHECK;
import static it.ding.sonar.util.CommonUtil.getIdentifier;
import static it.ding.sonar.util.CommonUtil.getLocatorValueMapInAnnotation;
import static it.ding.sonar.util.CommonUtil.methodInvocationIsElementFinder;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.SPACE;

import java.util.List;
import java.util.Map;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = CLASS_NAME_LOCATOR_VALUE_CHECK)
public class ClassNameLocatorValueCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    private static final List<String> CLASS_NAME_LOCATORS = asList("classname", "class_name");

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

        if (CLASS_NAME_LOCATORS.contains(locatorStrategy.toLowerCase()) && value.contains(SPACE)) {
            context.reportIssue(this, expressionTree,
                "Avoid compound class names with by class name locator strategy");
        }
    }

}
