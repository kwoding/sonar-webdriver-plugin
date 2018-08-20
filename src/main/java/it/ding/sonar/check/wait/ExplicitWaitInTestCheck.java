package it.ding.sonar.check.wait;

import static it.ding.sonar.data.CommonData.EXPLICIT_WAIT_IN_TEST_CHECK_KEY;
import static it.ding.sonar.util.CommonUtil.isPartOfWebDriverPackage;

import it.ding.sonar.check.BaseTestCheck;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.NewClassTree;

@Rule(key = EXPLICIT_WAIT_IN_TEST_CHECK_KEY)
public class ExplicitWaitInTestCheck extends BaseTestCheck {

    private static final String WAIT = "wait";

    @Override
    public void visitNewClass(NewClassTree tree) {
        String fullyQualifiedName = tree.symbolType().fullyQualifiedName();
        String identifier = tree.identifier().toString().toLowerCase();

        if (identifier.contains(WAIT) &&
            isPartOfWebDriverPackage(fullyQualifiedName)) {
            context.reportIssue(this, tree, "Avoid using explicit waits in Test classes.");
        }
    }

}
