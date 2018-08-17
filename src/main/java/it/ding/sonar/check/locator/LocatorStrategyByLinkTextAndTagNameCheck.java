package it.ding.sonar.check.locator;

import static it.ding.sonar.data.CommonData.LOCATORS_RECOMMENDED;
import static it.ding.sonar.data.CommonData.LOCATOR_STRATEGY_BY_LINK_TEXT_AND_TAG_NAME_CHECK_KEY;
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
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = LOCATOR_STRATEGY_BY_LINK_TEXT_AND_TAG_NAME_CHECK_KEY,
    name = "locator-strategy-by-link-text-and-tag-name-check",
    description = "Avoid locators based on link text, partial link text and tag name",
    priority = Priority.CRITICAL,
    tags = {"bug"})
public class LocatorStrategyByLinkTextAndTagNameCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    public static final List<String> LOCATORS_TO_AVOID = asList(
        "linkText",
        "partialLinkText",
        "tagName",
        "LINK_TEXT",
        "PARTIAL_LINK_TEXT",
        "TAG_NAME"
    );

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;

        scan(context.getTree());
    }

    @Override
    public void visitAnnotation(AnnotationTree tree) {
        Map<String, String> locatorsInAnnotation = getLocatorValueMapInAnnotation(tree);

        for (Map.Entry<String,String> locator : locatorsInAnnotation.entrySet()) {
            String locatorStrategy = locator.getKey();
            checkLocator(tree, locatorStrategy);
        }
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (methodInvocationIsElementFinder(tree)) {
            checkLocator(tree, getIdentifier(tree).name());
        }
    }

    private void checkLocator(ExpressionTree expressionTree, String locatorStrategy) {
        if (LOCATORS_TO_AVOID.contains(locatorStrategy)) {
            context.reportIssue(this, expressionTree,
                "Avoid using " + locatorStrategy + " locator, try using " + LOCATORS_RECOMMENDED.toString());
        }
    }

}
