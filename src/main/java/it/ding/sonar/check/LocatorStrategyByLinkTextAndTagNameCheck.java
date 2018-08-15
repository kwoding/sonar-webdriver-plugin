package it.ding.sonar.check;

import static it.ding.sonar.data.CommonData.LOCATORS_RECOMMENDED;
import static it.ding.sonar.util.CommonUtil.getIdentifier;
import static it.ding.sonar.util.CommonUtil.getLocatorsInAnnotation;
import static it.ding.sonar.util.CommonUtil.isPartOfWebDriverPackage;
import static java.util.Arrays.asList;

import java.util.List;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = "locator-strategy-check",
    name = "locator-strategy-check",
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
        List<String> locatorsInAnnotation = getLocatorsInAnnotation(tree);

        for (String locator : locatorsInAnnotation) {
            checkLocator(tree, locator);
        }
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (isPartOfWebDriverPackage(tree)) {
            checkLocator(tree, getIdentifier(tree).name());
        }
    }

    private void checkLocator(ExpressionTree expressionTree, String locator) {
        if (LOCATORS_TO_AVOID.contains(locator)) {
            context.reportIssue(this, expressionTree,
                "Avoid using " + locator + " locator, try using " + LOCATORS_RECOMMENDED.toString());
        }
    }


}
