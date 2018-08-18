package it.ding.sonar.check.wait;

import static it.ding.sonar.data.CommonData.HARD_CODED_SLEEP_CHECK_KEY;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = HARD_CODED_SLEEP_CHECK_KEY)
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
                context.reportIssue(this, tree, "Avoid using hard coded sleeps, use explicit wait instead");
            }
        }
    }

}
