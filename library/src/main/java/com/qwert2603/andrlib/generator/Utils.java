package com.qwert2603.andrlib.generator;

import java.io.File;

import javax.annotation.processing.ProcessingEnvironment;

final class Utils {

    static String getDirName(ProcessingEnvironment processingEnv) {
        final String dirName = processingEnv.getOptions()
                .get("kapt.kotlin.generated")
                .replace("kaptKotlin", "kapt")
                + "/com.qwert2603.andrlib.generated";
        new File(dirName).mkdirs();
        return dirName;
    }

}
