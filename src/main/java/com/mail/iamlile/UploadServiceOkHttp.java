package com.mail.iamlile;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lee on 2017/8/02.
 */

public class UploadServiceOkHttp {
    public static final MediaType MEDIA_TYPE_FILE = MediaType.parse("application/octet-stream");
    public static final String UPLOAD_SERVER = "http://10.250.105.200:3000/upload";
    public static final String PREFIX = "--", LINEND = "\r\n";
    public static final OkHttpClient client = new OkHttpClient();


    public void runUploadFile(String serverUrl, File file) {
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date_str = sdf.format(d);
        //String fileName = "kuai-android" + "-" + "2.0" + "-" + date_str + "-" + file.getName();
        String fileName = file.getName();
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"uploadfile\"; filename=\"" + fileName + "\"" + LINEND),
                        RequestBody.create(MEDIA_TYPE_FILE, file))
                .addFormDataPart("app_version", "2.0")
                .addFormDataPart("platform", "android")
                .addFormDataPart("create_time", "today")
                .build();

        if (null == serverUrl) {
            serverUrl = UPLOAD_SERVER;
        }
        Request request = new Request.Builder()
                .url(serverUrl)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // Handle the error
                }
                // Upload successful
                response.body().close();
            }
        });
    }

}
