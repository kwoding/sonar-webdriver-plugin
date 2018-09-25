package it.ding.sonar.check.wait;

import static it.ding.sonar.data.CommonData.IMPLICIT_WAIT_CHECK_KEY;
import static it.ding.sonar.util.CommonUtil.isPartOfWebDriverPackage;

import it.ding.sonar.check.Base;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = IMPLICIT_WAIT_CHECK_KEY)
public class ImplicitWaitCheck extends Base {

    private static final String IMPLICITLY_WAIT_METHOD_NAME = "implicitlyWait";

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        JavaFileScannerContext context = getContext();

        if (!tree.methodSelect().is(Tree.Kind.IDENTIFIER)) {
            MemberSelectExpressionTree memberSelectExpressionTree = ((MemberSelectExpressionTree) tree.methodSelect());

            String methodName = memberSelectExpressionTree.identifier().name();
            String fullyQualifiedNameOfExpression = memberSelectExpressionTree.expression().symbolType()
                .fullyQualifiedName();

            if (IMPLICITLY_WAIT_METHOD_NAME.equals(methodName) &&
                isPartOfWebDriverPackage(fullyQualifiedNameOfExpression)) {
                context.reportIssue(this, tree, "Avoid using implicit wait, use explicit wait instead");
            }
        }
    }

}
