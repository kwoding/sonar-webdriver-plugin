package it.ding.sonar.check;

import static it.ding.sonar.util.CommonUtil.annotationsContainAnnotationWhichIsPartOfTestPackage;
import static it.ding.sonar.util.CommonUtil.getAnnotationTrees;

import java.util.List;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;

public class BaseNonTestCheck extends Base {

    @Override
    public void visitClass(ClassTree tree) {
        List<AnnotationTree> annotationTrees = getAnnotationTrees(tree);

        if (annotationTrees.isEmpty() || !annotationsContainAnnotationWhichIsPartOfTestPackage(annotationTrees)) {
            super.visitClass(tree);
        }
    }

}
