package com.kornos.lint.demo.other;

import com.android.tools.lint.detector.api.Detector;

import java.util.List;

/**
 * 避免混淆产生的实体类问题
 * 实体类必须加@Keep
 */
public class EntityDetector extends Detector implements Detector.UastScanner{

    @Override
    public List<String> getApplicableMethodNames() {
        return super.getApplicableMethodNames();
    }
}
