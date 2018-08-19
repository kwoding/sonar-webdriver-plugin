package it.ding.sonar.check;

import static it.ding.sonar.data.CommonData.METHOD_KIND;
import static it.ding.sonar.util.CommonUtil.annotationsContainAnnotationWhichIsPartOfTestPackage;

import java.util.List;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

public class BaseNonTestCheck extends BaseTreeVisitor implements JavaFileScanner {

    public JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;

        super.scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        boolean visitClass = false;
        List<Tree> members = tree.members();

        for (Tree member : members) {
            if (METHOD_KIND.equals(member.kind().toString())) {
                List<AnnotationTree> annotationTrees = ((MethodTree) member).modifiers().annotations();

                if (annotationTrees.isEmpty()) {
                    visitClass = true;
                } else {
                    if (!annotationsContainAnnotationWhichIsPartOfTestPackage(annotationTrees)) {
                        visitClass = true;
                    }
                }
            }
        }

        if (visitClass) {
            super.visitClass(tree);
        }
    }
}
