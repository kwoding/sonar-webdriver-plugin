package it.ding.sonar.check.locator;

import static it.ding.sonar.data.CommonData.LOCATOR_STRATEGY_BY_XPATH_CHECK_KEY;
import static it.ding.sonar.data.LocatorStrategyCheckType.XPATH_LOCATOR_STRATEGY;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = LOCATOR_STRATEGY_BY_XPATH_CHECK_KEY)
public class LocatorStrategyByXpathCheck extends BaseLocatorValueCheck {

    @Override
    public void visitAnnotation(AnnotationTree tree) {
        checkAnnotationLocators(tree, XPATH_LOCATOR_STRATEGY);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        checkMethodInvocationLocators(tree, XPATH_LOCATOR_STRATEGY);
    }

}
