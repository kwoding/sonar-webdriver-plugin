package it.ding.sonar.check.locator;

import static it.ding.sonar.data.CommonData.CSS_LOCATOR_VALUE_CHECK;
import static it.ding.sonar.data.LocatorStrategyCheckType.CSS_VALUE;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = CSS_LOCATOR_VALUE_CHECK)
public class CssLocatorValueCheck extends BaseLocatorValueCheck {

    @Override
    public void visitAnnotation(AnnotationTree tree) {
        checkAnnotationLocators(tree, CSS_VALUE);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        checkMethodInvocationLocators(tree, CSS_VALUE);
    }

}
