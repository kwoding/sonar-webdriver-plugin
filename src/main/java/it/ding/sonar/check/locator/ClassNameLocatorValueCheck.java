package it.ding.sonar.check.locator;

import static it.ding.sonar.data.CommonData.CLASS_NAME_LOCATOR_VALUE_CHECK;
import static it.ding.sonar.data.LocatorStrategyCheckType.CLASS_NAME_VALUE;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = CLASS_NAME_LOCATOR_VALUE_CHECK)
public class ClassNameLocatorValueCheck extends BaseLocatorValueCheck {

    @Override
    public void visitAnnotation(AnnotationTree tree) {
        checkAnnotationLocators(tree, CLASS_NAME_VALUE);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        checkMethodInvocationLocators(tree, CLASS_NAME_VALUE);
    }

}
