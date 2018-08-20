package it.ding.sonar.check;

import static it.ding.sonar.data.CommonData.METHOD_KIND;
import static it.ding.sonar.util.CommonUtil.annotationsContainAnnotationWhichIsPartOfTestPackage;

import java.util.ArrayList;
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

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;

        super.scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        List<AnnotationTree> annotationTrees = new ArrayList<>();
        List<Tree> members = tree.members();

        for (Tree member : members) {
            if (METHOD_KIND.equals(member.kind().toString())) {
                annotationTrees.addAll(((MethodTree) member).modifiers().annotations());
            }
        }

        if (annotationsContainAnnotationWhichIsPartOfTestPackage(annotationTrees)) {
            super.visitClass(tree);
        }
    }
}
