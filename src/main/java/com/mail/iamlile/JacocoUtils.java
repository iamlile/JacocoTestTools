/**
 * Created by lee on 2017/7/29.
 */
package com.mail.iamlile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;


public class JacocoUtils {
    static String TAG = "JacocoUtils";

    //ec文件的路径
    private static String DEFAULT_COVERAGE_FILE_PATH = "/mnt/sdcard/coverage.ec";
    public static final  Logger logger = Logger.getLogger("JacocoUtils");

    /**
     * 生成ec文件
     *
     * @param isNew 是否重新创建ec文件
     */
    public static void generateEcFile(boolean isNew) {
//        String DEFAULT_COVERAGE_FILE_PATH = NLog.getContext().getFilesDir().getPath().toString() + "/coverage.ec";
        logger.info("生成覆盖率文件: " + DEFAULT_COVERAGE_FILE_PATH);
        OutputStream out = null;
        //File mCoverageFilePath = new File(DEFAULT_COVERAGE_FILE_PATH);
        File mCoverageFilePath = new File("/mnt/sdcard/coverage" + "-" +"2017"+ ".ec");
        try {
            if (isNew && mCoverageFilePath.exists()) {
                logger.info("JacocoUtils_generateEcFile: 清除旧的ec文件");
                //mCoverageFilePath.delete();
            }
            if (!mCoverageFilePath.exists()) {
                mCoverageFilePath.createNewFile();
            }
            out = new FileOutputStream(mCoverageFilePath.getPath(), true);

            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);

            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                    .invoke(agent, false));

            // ec文件自动上报到服务器
            //UploadServiceOkHttp uploadService = new UploadServiceOkHttp(mCoverageFilePath);
            //uploadService.start();
        } catch (Exception e) {
            logger.info("generateEcFile: " + e.getMessage());
        } finally {
            if (out == null)
                return;
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}