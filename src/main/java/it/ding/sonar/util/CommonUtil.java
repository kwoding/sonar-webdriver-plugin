package it.ding.sonar.util;

import static it.ding.sonar.data.CommonData.APPIUM_PACKAGE_NAME;
import static it.ding.sonar.data.CommonData.ASSERT;
import static it.ding.sonar.data.CommonData.ASSERTJ_PACKAGE_NAME;
import static it.ding.sonar.data.CommonData.BY_OBJECT_NAME;
import static it.ding.sonar.data.CommonData.CUCUMBER_PACKAGE_NAME;
import static it.ding.sonar.data.CommonData.EXPECT;
import static it.ding.sonar.data.CommonData.FAIL;
import static it.ding.sonar.data.CommonData.FEST_PACKAGE_NAME;
import static it.ding.sonar.data.CommonData.FIND_BY_ANNOTATION_NAME;
import static it.ding.sonar.data.CommonData.FIND_ELEMENT_METHOD_REGEX;
import static it.ding.sonar.data.CommonData.HAMCREST_PACKAGE_NAME;
import static it.ding.sonar.data.CommonData.HOW_PROPERTY;
import static it.ding.sonar.data.CommonData.JUNIT_PACKAGE_NAME;
import static it.ding.sonar.data.CommonData.SELENIUM_PACKAGE_NAME;
import static it.ding.sonar.data.CommonData.TESTNG_PACKAGE_NAME;
import static it.ding.sonar.data.CommonData.TEST_ANNOTATION_NAMES;
import static it.ding.sonar.data.CommonData.TRUTH_PACKAGE_NAME;
import static it.ding.sonar.data.CommonData.USING_PROPERTY;
import static it.ding.sonar.data.CommonData.VERIFY;
import static it.ding.sonar.data.CommonData.VERTX_PACKAGE_NAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

public class CommonUtil {

    public static Map<String, String> getLocatorValueMapInAnnotation(AnnotationTree annotationTree) {
        Map<String, String> locatorMap = new HashMap<>();
        String annotationType = annotationTree.annotationType().toString();
        String fullyQualifiedName = annotationTree.annotationType().symbolType().fullyQualifiedName();

        if (FIND_BY_ANNOTATION_NAME.equals(annotationType) &&
            isPartOfWebDriverPackage(fullyQualifiedName)) {
            for (ExpressionTree expressionTree : annotationTree.arguments()) {
                AssignmentExpressionTree assignmentExpressionTree = (AssignmentExpressionTree) expressionTree;

                String property = assignmentExpressionTree.variable().toString();
                String locator = HOW_PROPERTY.equals(property)
                    ? ((MemberSelectExpressionTree) ((AssignmentExpressionTree) expressionTree)
                    .expression()).identifier().name()
                    : ((AssignmentExpressionTree) expressionTree).variable().toString();

                String propertyValue = assignmentExpressionTree.expression().is(Kind.STRING_LITERAL) ?
                    ((LiteralTree) assignmentExpressionTree.expression()).value() : null;

                ExpressionTree howExpressionTree = annotationTree.arguments()
                    .stream()
                    .filter(aet -> HOW_PROPERTY.equals(((AssignmentExpressionTree) aet).variable().toString()))
                    // Only one "how" property allowed in annotation
                    .findFirst()
                    .orElse(null);

                if (USING_PROPERTY.equals(property)) {
                    String howLocator = ((MemberSelectExpressionTree) ((AssignmentExpressionTree) howExpressionTree)
                        .expression()).identifier().name();
                    locatorMap.put(howLocator, propertyValue);
                } else {
                    locatorMap.put(locator, propertyValue);
                }
            }
        }

        return locatorMap;
    }

    public static boolean methodInvocationIsPartOfWebDriverPackage(MethodInvocationTree methodInvocationTree) {
        if (getIdentifier(methodInvocationTree).symbol().isUnknown()) {
            return false;
        }

        String fullyQualifiedName = methodInvocationTree.symbol().owner().type().fullyQualifiedName();

        return isPartOfWebDriverPackage(fullyQualifiedName);
    }

    public static boolean methodInvocationIsElementFinder(MethodInvocationTree methodInvocationTree) {
        if (getIdentifier(methodInvocationTree).symbol().isUnknown()) {
            return false;
        }

        String methodName = getIdentifier(methodInvocationTree).name();
        String ownerName = getIdentifier(methodInvocationTree).symbol().owner().name();

        return (methodName.matches(FIND_ELEMENT_METHOD_REGEX) || ownerName.equals(BY_OBJECT_NAME)) &&
            methodInvocationIsPartOfWebDriverPackage(methodInvocationTree);
    }

    public static boolean annotationsContainAnnotationWhichIsPartOfTestPackage(List<AnnotationTree> annotationTrees) {
        boolean annotationIsPartOfTestPackage = false;

        for (AnnotationTree annotationTree : annotationTrees) {
            if (annotationIsPartOfTestPackage(annotationTree)) {
                annotationIsPartOfTestPackage = true;
            }
        }

        return annotationIsPartOfTestPackage;
    }

    private static boolean annotationIsPartOfTestPackage(AnnotationTree annotationTree) {
        String annotation = annotationTree.annotationType().toString();
        String fullyQualifiedName = annotationTree.annotationType().symbolType().fullyQualifiedName();

        return TEST_ANNOTATION_NAMES.contains(annotation) && isPartOfTestPackage(fullyQualifiedName);
    }

    public static boolean methodInvocationIsAssertion(MethodInvocationTree methodInvocationTree) {
        if (getIdentifier(methodInvocationTree).symbol().isUnknown()) {
            return false;
        }
        String methodName = getIdentifier(methodInvocationTree).name();

        String fullyQualifiedName = methodInvocationTree.symbol().owner().type().fullyQualifiedName();

        return methodIsAssertion(methodName) && isPartOfAssertionPackage(fullyQualifiedName);
    }

    private static boolean methodIsAssertion(String methodName) {
        return methodName.startsWith(ASSERT) ||
            methodName.startsWith(FAIL) ||
            methodName.startsWith(VERIFY) ||
            methodName.startsWith(EXPECT);
    }

    private static boolean isPartOfAssertionPackage(String fullyQualifiedName) {
        return fullyQualifiedName.startsWith(HAMCREST_PACKAGE_NAME) ||
            fullyQualifiedName.startsWith(JUNIT_PACKAGE_NAME) ||
            fullyQualifiedName.startsWith(ASSERTJ_PACKAGE_NAME) ||
            fullyQualifiedName.startsWith(FEST_PACKAGE_NAME) ||
            fullyQualifiedName.startsWith(TRUTH_PACKAGE_NAME) ||
            fullyQualifiedName.startsWith(VERTX_PACKAGE_NAME);
    }

    private static boolean isPartOfWebDriverPackage(String fullyQualifiedName) {
        return fullyQualifiedName.startsWith(SELENIUM_PACKAGE_NAME) ||
            fullyQualifiedName.startsWith(APPIUM_PACKAGE_NAME);
    }

    public static boolean isPartOfTestPackage(String fullyQualifiedName) {
        return fullyQualifiedName.startsWith(JUNIT_PACKAGE_NAME) ||
            fullyQualifiedName.startsWith(TESTNG_PACKAGE_NAME) ||
            fullyQualifiedName.startsWith(CUCUMBER_PACKAGE_NAME);
    }

    public static IdentifierTree getIdentifier(MethodInvocationTree methodInvocationTree) {
        // methodSelect can only be Tree.Kind.IDENTIFIER or Tree.Kind.MEMBER_SELECT
        if (methodInvocationTree.methodSelect().is(Tree.Kind.IDENTIFIER)) {
            return (IdentifierTree) methodInvocationTree.methodSelect();
        }
        return ((MemberSelectExpressionTree) methodInvocationTree.methodSelect()).identifier();
    }

}
