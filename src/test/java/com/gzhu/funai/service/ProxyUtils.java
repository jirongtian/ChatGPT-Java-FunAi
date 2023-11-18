package com.gzhu.funai.service;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class ProxyUtils {

    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static String getResultByHttpConnectionProxy(String url, String content, String proxyHost, int proxyPort) {

        String result = "";
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            //设置proxy
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            URL proxyUrl = new URL(url);
            //判断是哪种类型的请求
            if (url.startsWith("https")) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) proxyUrl.openConnection(proxy);
                httpsURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE);
                //允许写入
                httpsURLConnection.setDoInput(true);
                //允许写出
                httpsURLConnection.setDoOutput(true);
                //请求方法的类型 POST/GET
                httpsURLConnection.setRequestMethod("POST");
                //是否使用缓存
                httpsURLConnection.setUseCaches(false);
                //读取超时
                httpsURLConnection.setReadTimeout(15000);
                //连接超时
                httpsURLConnection.setConnectTimeout(15000);
                //设置SSL
                httpsURLConnection.setSSLSocketFactory(getSsf());
                //设置主机验证程序
                httpsURLConnection.setHostnameVerifier((s, sslSession) -> true);

                outputStream = httpsURLConnection.getOutputStream();
                outputStream.write(content.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                inputStream = httpsURLConnection.getInputStream();
            } else {
                HttpURLConnection httpURLConnection = (HttpURLConnection) proxyUrl.openConnection(proxy);
                httpURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setReadTimeout(15000);

                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(content.getBytes("UTF-8"));
                outputStream.flush();
                inputStream = httpURLConnection.getInputStream();
            }

            byte[] bytes = read(inputStream, 1024);
            result = (new String(bytes, "UTF-8")).trim();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static byte[] read(InputStream inputStream, int bufferSize) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[bufferSize];

        for (int num = inputStream.read(buffer); num != -1; num = inputStream.read(buffer)) {
            baos.write(buffer, 0, num);
        }

        baos.flush();
        return baos.toByteArray();
    }

    private static SSLSocketFactory getSsf() {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0],
                    new TrustManager[]{new ProxyUtils.DefaultTrustManager()},
                    new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert ctx != null;
        return ctx.getSocketFactory();
    }

    private static final class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
