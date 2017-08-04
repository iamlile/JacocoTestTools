/**
 * Created by lee on 2017/7/29.
 */
package com.mail.iamlile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.logging.Logger;


public class JacocoUtils {
    static String TAG = "JacocoUtils";

    //ec文件的路径
    private static String DEFAULT_COVERAGE_FILE_PATH = "/mnt/sdcard/coverage.ec";
    private static Logger LOG = Logger.getLogger("JacocoUtils");
    /**
     * 生成ec文件
     *
     * @param isNew 是否重新创建ec文件
     */
    public static void generateEcFile2(String serverUrl,boolean isNew,Map<String,String> params) {
        OutputStream out = null;
        String gitSha = params.get("gitSha");
        String filePath = "/mnt/sdcard/coverage" + "-" + gitSha + ".exec";
        File mCoverageFilePath = new File(filePath);
        LOG.info("生成覆盖率文件: " + filePath);
        try {
            if (isNew && mCoverageFilePath.exists()) {
                LOG.info("JacocoUtils_generateEcFile: 清除旧的ec文件");
                mCoverageFilePath.delete();
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
            UploadServiceOkHttp uploadServiceOkHttp = new UploadServiceOkHttp();
            uploadServiceOkHttp.runUploadFile(serverUrl,mCoverageFilePath);
        } catch (Exception e) {
            LOG.info("generateEcFile: " + e.getMessage());
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