package it.ding.sonar.util;

import static it.ding.sonar.data.CommonData.APPIUM_PACKAGE_NAME;
import static it.ding.sonar.data.CommonData.FIND_BY_ANNOTATION_NAME;
import static it.ding.sonar.data.CommonData.HOW_PROPERTY;
import static it.ding.sonar.data.CommonData.SELENIUM_PACKAGE_NAME;

import java.util.ArrayList;
import java.util.List;
import org.sonar.java.resolve.JavaSymbol.TypeJavaSymbol;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

public class CommonUtil {

    public static List<String> getLocatorsInAnnotation(AnnotationTree annotationTree) {
        List<String> locators = new ArrayList<>();
        String annotationType = annotationTree.annotationType().toString();

        if (FIND_BY_ANNOTATION_NAME.equals(annotationType)) {
            for (ExpressionTree expressionTree : annotationTree.arguments()) {
                String property = ((AssignmentExpressionTree) expressionTree).variable().toString();

                String locator = HOW_PROPERTY.equals(property)
                    ? ((MemberSelectExpressionTree) ((AssignmentExpressionTree) expressionTree)
                    .expression()).identifier().name()
                    : ((AssignmentExpressionTree) expressionTree).variable().toString();

                locators.add(locator);
            }
        }

        return locators;
    }
    public static boolean isPartOfWebDriverPackage(MethodInvocationTree methodInvocationTree) {
        if (getIdentifier(methodInvocationTree).symbol().isUnknown()) {
            return false;
        }

        String fullyQualifiedName = ((TypeJavaSymbol) getIdentifier(methodInvocationTree).symbol().owner())
            .getFullyQualifiedName();

        return fullyQualifiedName.startsWith(SELENIUM_PACKAGE_NAME) ||
            fullyQualifiedName.startsWith(APPIUM_PACKAGE_NAME);
    }

    public static IdentifierTree getIdentifier(MethodInvocationTree methodInvocationTree) {
        // methodSelect can only be Tree.Kind.IDENTIFIER or Tree.Kind.MEMBER_SELECT
        if (methodInvocationTree.methodSelect().is(Tree.Kind.IDENTIFIER)) {
            return (IdentifierTree) methodInvocationTree.methodSelect();
        }
        return ((MemberSelectExpressionTree) methodInvocationTree.methodSelect()).identifier();
    }

}
