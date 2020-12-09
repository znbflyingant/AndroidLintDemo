package com.kornos.lint.demo.other;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiReferenceList;

import java.util.Collections;
import java.util.List;

/**
 * 所有Activity继承BasePresenterActivity，避免遗漏和出错
 */
public class BaseActivityDetector extends Detector implements Detector.UastScanner {
    public static Issue ISSUE = Issue.create("BaseActivityExtends",
            "Activity继承BasePresenterActivity",
            "Activity需要继承BasePresenterActivity进行统一管理",
            Category.CORRECTNESS, 5, Severity.ERROR,
            new Implementation(BaseActivityDetector.class, Scope.JAVA_FILE_SCOPE)).addMoreInfo("https://github.com/liulingfeng/lint");

    @Override
    public List<Class<? extends PsiElement>> getApplicablePsiTypes() {
        return Collections.singletonList(PsiClass.class);
    }

    @Override
    public JavaElementVisitor createPsiVisitor(JavaContext context) {
        return new ShowFinder(context);
    }

    private static class ShowFinder extends JavaElementVisitor {
        private boolean isFound;
        private JavaContext context;

        public ShowFinder(JavaContext context) {
            this.context = context;
        }

        @Override
        public void visitClass(PsiClass aClass) {
            String className = aClass.getName();

            if (className == null) {
                return;
            }
            if (!className.endsWith("Activity") || className.endsWith("BasePresenterActivity")) {
                isFound = false;
                return;
            }
            PsiReferenceList referenceList = aClass.getExtendsList();
            if (referenceList != null) {
                PsiJavaCodeReferenceElement[] elements = referenceList.getReferenceElements();
                for (PsiJavaCodeReferenceElement element : elements) {
                    String name = element.getReferenceName();
                    if (name != null)
                        isFound = name.equals("BasePresenterActivity");
                }
                if (!isFound) {
                    context.report(ISSUE, context.getLocation(aClass), "当前Activity没有继承BasePresenterActivity");
                }
            }

        }
    }
}
