package it.ding.sonar.check;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = "hard-coded-sleep-check",
    name = "hard-coded-sleep-check",
    description = "Avoid using hard coded sleeps",
    priority = Priority.CRITICAL,
    tags = {"bug"})
public class HardCodedSleepCheck extends BaseTreeVisitor implements JavaFileScanner {

    public JavaFileScannerContext context;
    private static final String SLEEP_METHOD_NAME = "sleep";
    private static final String THREAD = "java.lang.Thread";

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;

        super.scan(context.getTree());
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (!tree.methodSelect().is(Tree.Kind.IDENTIFIER)) {
            MemberSelectExpressionTree memberSelectExpressionTree = ((MemberSelectExpressionTree) tree.methodSelect());

            String methodName = memberSelectExpressionTree.identifier().name();
            String fullyQualifiedNameOfExpression = memberSelectExpressionTree.expression().symbolType()
                .fullyQualifiedName();

            if (SLEEP_METHOD_NAME.equals(methodName) &&
                fullyQualifiedNameOfExpression.equals(THREAD)) {
                context.reportIssue(this, tree, "Avoid using hard coded sleeps");
            }
        }
    }

}
