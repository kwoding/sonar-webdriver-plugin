package it.ding.sonar.check;

import static java.util.Arrays.asList;

import java.util.List;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

public class BaseTestCheck extends BaseTreeVisitor implements JavaFileScanner {

    public JavaFileScannerContext context;
    private static final String METHOD = "METHOD";
    private static final List<String> TEST_ANNOTATION_NAMES = asList("Test", "Given", "When", "Then");

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;

        super.scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        List<Tree> members = tree.members();

        for (Tree member : members) {
            if (METHOD.equals(member.kind().toString())) {
                List<AnnotationTree> annotationTrees = ((MethodTree) member).modifiers().annotations();

                for (AnnotationTree annotationTree : annotationTrees) {
                    String annotation = annotationTree.annotationType().toString();

                    if (TEST_ANNOTATION_NAMES.contains(annotation)) {
                        super.visitClass(tree);
                    }
                }
            }
        }
    }
}
