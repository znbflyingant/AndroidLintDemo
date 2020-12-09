package com.kornos.lint.demo.other;

import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.ResourceContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.kornos.lint.demo.LintConfig;

/**
 * 图片大小检测
 * 之后引入项目的图片都需要控制大小
 */
public class ImageFileDetector extends Detector implements Detector.BinaryResourceScanner {
    public static Issue ISSUE = Issue.create("ImageFileSizeOut",
            "图片资源过大",
            "图片资源超过了项目限定的图片大小",
            Category.CORRECTNESS, 5, Severity.ERROR,
            new Implementation(ImageFileDetector.class, Scope.BINARY_RESOURCE_FILE_SCOPE)).addMoreInfo("https://github.com/liulingfeng/lint");

    private String reportStr;
    private LintConfig lintConfig;
    private int maxSize = 200;

    @Override
    public void beforeCheckProject(Context context) {
        lintConfig = new LintConfig(context);
        if ("".equals(lintConfig.getConfig("image-file-maxsize"))) {
            reportStr = "图片文件过大: %d" + "KB,超过了项目限制的:" + 200 + "KB,请进行压缩或找设计重新出图.";
        } else {
            maxSize = Integer.parseInt(lintConfig.getConfig("image-file-maxsize"));
            reportStr = "图片文件过大: %d" + "KB,超过了项目限制的:" + lintConfig.getConfig("image-file-maxsize") + "KB,请进行压缩或找设计重新出图.";
        }
    }

    @Override
    public void beforeCheckLibraryProject(Context context) {
        lintConfig = new LintConfig(context);
        if ("".equals(lintConfig.getConfig("image-file-maxsize"))) {
            reportStr = "图片文件过大: %d" + "KB,超过了项目限制的:" + 200 + "KB,请进行压缩或找设计重新出图.";
        } else {
            maxSize = Integer.parseInt(lintConfig.getConfig("image-file-maxsize"));
            reportStr = "图片文件过大: %d" + "KB,超过了项目限制的:" + lintConfig.getConfig("image-file-maxsize") + "KB,请进行压缩或找设计重新出图.";
        }
    }

    @Override
    public boolean appliesTo(ResourceFolderType folderType) {
        return folderType.getName().equalsIgnoreCase(String.valueOf(ResourceFolderType.MIPMAP)) || folderType.getName().equalsIgnoreCase(String.valueOf(ResourceFolderType.DRAWABLE));
    }

    @Override
    public void checkBinaryResource(ResourceContext context) {
        String filename = context.file.getName();

        if (filename.contains(".png")
                || filename.contains(".jpeg")
                || filename.contains(".jpg")
                ) {
            long fileSize = context.file.length() / 1024;

            if (fileSize > maxSize) {

                String repS = String.format(reportStr, fileSize);

                Location fileLocation = Location.create(context.file);
                context.report(ISSUE,
                        fileLocation,
                        repS);
            }
        }
    }
}
