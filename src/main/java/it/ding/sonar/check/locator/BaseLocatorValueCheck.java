package it.ding.sonar.check.locator;

import static it.ding.sonar.data.CommonData.LOCATORS_RECOMMENDED;
import static it.ding.sonar.util.CommonUtil.getLocatorValueMapInAnnotationTree;
import static it.ding.sonar.util.CommonUtil.getLocatorValueMapInMethodInvocationTree;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.SPACE;

import it.ding.sonar.check.Base;
import it.ding.sonar.data.LocatorStrategyCheckType;
import java.util.List;
import java.util.Map;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

class BaseLocatorValueCheck extends Base {

    private static final List<String> CLASS_NAME_LOCATORS = asList("classname", "class_name");
    private static final List<String> CSS_LOCATORS = asList("cssselector", "css");
    private static final List<String> ID_LOCATORS = singletonList("id");
    private static final List<String> XPATH_LOCATORS = singletonList("xpath");
    private static final List<String> LOCATORS_TO_AVOID = asList(
        "linkText",
        "partialLinkText",
        "tagName",
        "LINK_TEXT",
        "PARTIAL_LINK_TEXT",
        "TAG_NAME"
    );
    private static final String VALID_ID_LOCATOR_REGEX = "^[A-Za-z]+[\\w\\-:.]*$";
    // At least two times a space or ">"
    private static final String AVOID_CSS_LOCATOR_REGEX = "(([^>]*\\s+[^>]*)|(.*\\s*>\\s*.*)){2,}";
    // At least starting with double slash and zero or one time a single slash in the rest of the string
    private static final String RECOMMENDED_XPATH_LOCATOR_REGEX = "^//[^/]*/?[^/]*$";

    void checkAnnotationLocators(AnnotationTree tree, LocatorStrategyCheckType locatorStrategyCheckType) {
        Map<String, String> locatorsInAnnotationTree = getLocatorValueMapInAnnotationTree(tree);

        for (Map.Entry<String, String> locator : locatorsInAnnotationTree.entrySet()) {
            checkLocator(tree, locator.getKey(), locator.getValue(), locatorStrategyCheckType);
        }
    }

    void checkMethodInvocationLocators(MethodInvocationTree tree, LocatorStrategyCheckType locatorStrategyCheckType) {
        Map<String, String> locatorsInMethodInvocationTree = getLocatorValueMapInMethodInvocationTree(tree);

        for (Map.Entry<String, String> locator : locatorsInMethodInvocationTree.entrySet()) {
            checkLocator(tree, locator.getKey(), locator.getValue(), locatorStrategyCheckType);
        }
    }

    private void checkLocator(ExpressionTree expressionTree, String locatorKey, String locatorValue,
        LocatorStrategyCheckType locatorStrategyCheckType) {
        switch (locatorStrategyCheckType) {
            case CLASS_NAME_VALUE:
                reportClassNameIssue(expressionTree, locatorKey, locatorValue);
                break;
            case ID_VALUE:
                reportIdIssue(expressionTree, locatorKey, locatorValue);
                break;
            case CSS_VALUE:
                reportCssIssue(expressionTree, locatorKey, locatorValue);
                break;
            case XPATH_VALUE:
                reportXpathIssue(expressionTree, locatorKey, locatorValue);
                break;
            case XPATH_LOCATOR_STRATEGY:
                reportXpathLocatorIssue(expressionTree, locatorKey);
                break;
            case LINK_TEXT_AND_TAG_NAME_LOCATOR_STRATEGY:
                reportLinkTextAndTagNameLocatorIssue(expressionTree, locatorKey);
                break;
        }
    }

    private void reportLinkTextAndTagNameLocatorIssue(ExpressionTree expressionTree, String locatorKey) {
        JavaFileScannerContext context = getContext();

        if (LOCATORS_TO_AVOID.contains(locatorKey)) {
            context.reportIssue(this, expressionTree,
                "Avoid using " + locatorKey + " locator, try using " + LOCATORS_RECOMMENDED.toString());
        }
    }

    private void reportXpathLocatorIssue(ExpressionTree expressionTree, String locatorKey) {
        JavaFileScannerContext context = getContext();

        if (XPATH_LOCATORS.contains(locatorKey.toLowerCase())) {
            context.reportIssue(this, expressionTree,
                "Xpath locator is not recommended, try using " + LOCATORS_RECOMMENDED.toString());
        }
    }

    private void reportXpathIssue(ExpressionTree expressionTree, String locatorKey, String locatorValue) {
        JavaFileScannerContext context = getContext();

        if (XPATH_LOCATORS.contains(locatorKey.toLowerCase()) && !locatorValue
            .matches(RECOMMENDED_XPATH_LOCATOR_REGEX)) {
            context.reportIssue(this, expressionTree,
                "Avoid using xpath locator tied to page layout");
        }
    }

    private void reportCssIssue(ExpressionTree expressionTree, String locatorKey, String locatorValue) {
        JavaFileScannerContext context = getContext();

        if (CSS_LOCATORS.contains(locatorKey.toLowerCase()) && locatorValue.matches(AVOID_CSS_LOCATOR_REGEX)) {
            context.reportIssue(this, expressionTree,
                "Avoid using css locator tied to page layout");
        }
    }

    private void reportIdIssue(ExpressionTree expressionTree, String locatorKey, String locatorValue) {
        JavaFileScannerContext context = getContext();

        if (ID_LOCATORS.contains(locatorKey.toLowerCase()) && !locatorValue.matches(VALID_ID_LOCATOR_REGEX)) {
            context.reportIssue(this, expressionTree,
                "Invalid id locator");
        }
    }

    private void reportClassNameIssue(ExpressionTree expressionTree, String locatorKey, String locatorValue) {
        JavaFileScannerContext context = getContext();

        if (CLASS_NAME_LOCATORS.contains(locatorKey.toLowerCase()) && locatorValue.contains(SPACE)) {
            context.reportIssue(this, expressionTree,
                "Avoid compound class names with by class name locator strategy");
        }
    }

}
