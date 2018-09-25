package it.ding.sonar.check.locator;

import static it.ding.sonar.data.CommonData.LOCATOR_STRATEGY_BY_LINK_TEXT_AND_TAG_NAME_CHECK_KEY;
import static it.ding.sonar.data.LocatorStrategyCheckType.LINK_TEXT_AND_TAG_NAME_LOCATOR_STRATEGY;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = LOCATOR_STRATEGY_BY_LINK_TEXT_AND_TAG_NAME_CHECK_KEY)
public class LocatorStrategyByLinkTextAndTagNameCheck extends BaseLocatorValueCheck {

    @Override
    public void visitAnnotation(AnnotationTree tree) {
        checkAnnotationLocators(tree, LINK_TEXT_AND_TAG_NAME_LOCATOR_STRATEGY);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        checkMethodInvocationLocators(tree, LINK_TEXT_AND_TAG_NAME_LOCATOR_STRATEGY);
    }

}
