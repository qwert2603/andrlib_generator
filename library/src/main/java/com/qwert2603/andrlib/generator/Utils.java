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

//    static long getMillisFromFile(ProcessingEnvironment processingEnv, String filename) {
//        File lastFile = new File(getDirName(processingEnv), filename);
//        BufferedReader bufferedReader = null;
//        try {
//            bufferedReader = new BufferedReader(new FileReader(lastFile));
//            return Long.parseLong(bufferedReader.readLine());
//        } catch (Exception ignored) {
//            return 0;
//        } finally {
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//
//                } catch (IOException ignored) {
//                }
//            }
//        }
//    }
//
//    static void saveMillisToFile(ProcessingEnvironment processingEnv, String filename, long millis) {
//        File lastFile = new File(getDirName(processingEnv), filename);
//        FileWriter lastFileWriter = null;
//        try {
//            lastFileWriter = new FileWriter(lastFile);
//            lastFileWriter.write(String.valueOf(millis));
//            lastFileWriter.flush();
//        } catch (IOException ignored) {
//        } finally {
//            if (lastFileWriter != null) {
//                try {
//                    lastFileWriter.close();
//                } catch (IOException ignored) {
//                }
//            }
//        }
//    }

}
