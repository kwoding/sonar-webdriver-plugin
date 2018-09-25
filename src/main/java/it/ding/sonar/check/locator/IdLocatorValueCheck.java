package it.ding.sonar.check.locator;

import static it.ding.sonar.data.CommonData.ID_LOCATOR_VALUE_CHECK;
import static it.ding.sonar.data.LocatorStrategyCheckType.ID_VALUE;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = ID_LOCATOR_VALUE_CHECK)
public class IdLocatorValueCheck extends BaseLocatorValueCheck {

    @Override
    public void visitAnnotation(AnnotationTree tree) {
        checkAnnotationLocators(tree, ID_VALUE);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        checkMethodInvocationLocators(tree, ID_VALUE);
    }

}
