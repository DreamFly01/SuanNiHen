package com.fdl.updata;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/12/8<p>
 * <p>changeTime：2018/12/8<p>
 * <p>version：1<p>
 */
public class YCDownRequest implements ProgressResponseBody.ProgressResponseListener {

    private OkHttpClient mOkHttpClient;
    private String appendedParamsUrl;
    private Request.Builder builder;
    private String url;
    private String saveFileDir;

    public String getSaveFileName() {
        return saveFileName;
    }

    public YCDownRequest setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
        return this;
    }

    private String saveFileName;
    private Handler mDelivery;
    private RequestResponseListener requestResponseListener;
    public YCDownRequest() {

        mDelivery = new Handler(Looper.getMainLooper());
        builder = new Request.Builder();
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5,TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();


    }

    public YCDownRequest setUrl(String urlParam) {
        if (builder == null) {
            throw new IllegalArgumentException("builder can not be empty!");
        }
        url = urlParam;
        builder.url(url);
        return this;
    }

    public YCDownRequest setRequestTag(String requestTag) {
        if (builder == null) {
            throw new IllegalArgumentException("builder can not be empty!");
        }
        builder.tag(requestTag);
        return this;
    }

    public YCDownRequest addHeadedrs(Map<String, String> header) {

        if (builder == null) {
            throw new IllegalArgumentException("builder can not be empty!");
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if (header == null || header.isEmpty()) {
            return this;
        }

        for (String key : header.keySet()) {
            headerBuilder.add(key, header.get(key));
        }

        builder.headers(headerBuilder.build());
        return this;
    }

    public YCDownRequest addParams(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return this;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        appendedParamsUrl = sb.toString();
        builder.url(appendedParamsUrl);
        return this;
    }

    public YCDownRequest setSaveFileDir(String saveFileDir) {
        this.saveFileDir = saveFileDir;
        return  this;
    }



    public YCDownRequest setRequestResponseListener(RequestResponseListener requestResponseListener) {
        this.requestResponseListener = requestResponseListener;
        return this;
    }

    public YCDownRequest invokeAsyn() {
        initAPKDir();
        mOkHttpClient.newBuilder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), YCDownRequest.this))
                        .build();
            }
        } );
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                requestResponseListener.onStart();
            }
        });
        mOkHttpClient.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        requestResponseListener.onRequestFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.code() >= 400 && response.code() <= 599) {
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            requestResponseListener.onRequestFailed(new RuntimeException("下载失败！code:"+response.code()));
                        }
                    });

                } else {
                    try {
                        //将返回结果转化为流，并写入文件
                        int len;
                        byte[] buf = new byte[2048];
                        InputStream inputStream = response.body().byteStream();
                        //可以在这里自定义路径
                        File file1 = new File(saveFileDir + saveFileName);
                        FileOutputStream fileOutputStream = new FileOutputStream(file1);
                        while ((len = inputStream.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, len);
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        inputStream.close();
                    } catch (Exception e) {
                        final String erroMsg = e.getMessage();
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                requestResponseListener.onRequestFailed(new RuntimeException("转化下载流失败,erroMsg=" + erroMsg));
                            }
                        });
                    }
                }
            }

        });

        return this;

    }

    public void cancelRequest(String tag) {
//        mOkHttpClient.newBuilder().cancel(tag);
    }

    private void initAPKDir() {
        File destDir = new File(saveFileDir);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        } else {
            deleteFile(destDir);
        }
    }

    public  void deleteFile(File oldPath) {

        if (oldPath.isDirectory()) {
            File[] files = oldPath.listFiles();
            for (File file : files) {
                deleteFile(file);
                file.delete();
            }
        }
    }


    @Override
    public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
        final Long bytesReadLong = bytesRead;
        final Long contentLengthLong = contentLength;
        final Boolean doneBoolean = done;
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                requestResponseListener.inProgress( bytesReadLong,  contentLengthLong,  doneBoolean);

            }
        });
    }





}
