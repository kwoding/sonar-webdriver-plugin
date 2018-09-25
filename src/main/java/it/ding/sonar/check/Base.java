package it.ding.sonar.check;

import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;

public class Base extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;

        scan(context.getTree());
    }

    public JavaFileScannerContext getContext() {
        return context;
    }
}
