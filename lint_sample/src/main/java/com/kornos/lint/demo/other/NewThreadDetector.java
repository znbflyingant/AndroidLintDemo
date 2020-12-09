package com.kornos.lint.demo.other;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNewExpression;

import java.util.Collections;
import java.util.List;

/**
 * 禁止直接创建线程
 */
public class NewThreadDetector extends Detector implements Detector.UastScanner {

    public static final Issue ISSUE = Issue.create(
            "NewThread",
            "避免自己创建Thread",
            "自己创建Thread很容易导致一系列问题",
            Category.PERFORMANCE, 5, Severity.WARNING,
            new Implementation(NewThreadDetector.class, Scope.JAVA_FILE_SCOPE)).addMoreInfo("https://github.com/liulingfeng/lint");

    @Override
    public List<String> getApplicableConstructorTypes() {
        return Collections.singletonList("java.lang.Thread");
    }

    @Override
    public void visitConstructor(@NonNull JavaContext context, @com.android.annotations.Nullable JavaElementVisitor visitor,
                                 @NonNull PsiNewExpression node, @NonNull PsiMethod constructor) {
        context.report(ISSUE, node, context.getLocation(node), "请勿直接调用new Thread()，建议使用AsyncTask或统一的线程管理工具类");
    }
}
