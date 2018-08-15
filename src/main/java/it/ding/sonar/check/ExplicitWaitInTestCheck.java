package it.ding.sonar.check;

import static it.ding.sonar.data.CommonData.SELENIUM_PACKAGE_NAME;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.NewClassTree;

@Rule(key = "explicit-wait-in-test-check",
    name = "explicit-wait-in-test-check",
    description = "Avoid using explicit waits in Test classes",
    priority = Priority.CRITICAL,
    tags = {"bug"})
public class ExplicitWaitInTestCheck extends BaseTestCheck {

    private static final String WAIT = "wait";

    @Override
    public void visitNewClass(NewClassTree tree) {
        String fullyQualifiedName = tree.symbolType().fullyQualifiedName();
        String identifier = tree.identifier().toString().toLowerCase();

        if (identifier.contains(WAIT) &&
            fullyQualifiedName.startsWith(SELENIUM_PACKAGE_NAME)) {
            context.reportIssue(this, tree, "Avoid using explicit waits in Test classes.");
        }
    }

}
