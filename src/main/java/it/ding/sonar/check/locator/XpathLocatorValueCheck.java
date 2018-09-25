package it.ding.sonar.check.locator;

import static it.ding.sonar.data.CommonData.XPATH_LOCATOR_VALUE_CHECK;
import static it.ding.sonar.data.LocatorStrategyCheckType.XPATH_VALUE;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = XPATH_LOCATOR_VALUE_CHECK)
public class XpathLocatorValueCheck extends BaseLocatorValueCheck {

    @Override
    public void visitAnnotation(AnnotationTree tree) {
        checkAnnotationLocators(tree, XPATH_VALUE);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        checkMethodInvocationLocators(tree, XPATH_VALUE);
    }

}
