package com.mail.iamlile;

import org.junit.Test;

import java.io.File;

/**
 * Created by lee on 2017/7/31.
 */

public class UploadServiceOkHttpTest {

    @Test
    public void testRunUploadFile() throws Exception {
        File file = new File("/Users/lee/PycharmProjects/file-uploader/uploads/tmp/coverage.ec");
        new UploadServiceOkHttp().runUploadFile(null,file);

    }
}