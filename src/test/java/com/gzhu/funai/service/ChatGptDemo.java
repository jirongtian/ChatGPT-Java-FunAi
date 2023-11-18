package com.gzhu.funai.service;

import cn.hutool.http.*;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ChatGptDemo {
    public static void main(String[] args) {
        //代理
        String proxyHost = "127.0.0.1";
        int proxyPort = 50373;
//        Map<String,String> headers = new HashMap<String,String>();
//        headers.put("Content-Type","application/json;charset=UTF-8");
//        JSONObject json = new JSONObject();
//        //选择模型
//        json.set("model","text-davinci-003");
//        //添加我们需要输入的内容
//        json.set("prompt","Oracle 计算年龄，精确到天，格式为xx岁xx月xx天？");
//        json.set("temperature",0.9);
//        json.set("max_tokens",2048);
//        json.set("top_p",1);
//        json.set("frequency_penalty",0.0);
//        json.set("presence_penalty",0.6);
//       HttpRequest HttpResponse response = HttpRequest.post("https://api.openai.com/v1/completions")
//                .headerMap(headers, false)
//                .bearerAuth("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")
//                .body(String.valueOf(json))
//                .timeout(5 * 60 * 1000)
//                .execute();
//        System.out.println(response.body());

        Map<String,Object> request = new HashMap<>(16);
        request.put("model","text-davinci-003");
        request.put("prompt","Oracle 计算年龄，精确到天，格式为xx岁xx月xx天？");
        request.put("temperature",0.9);
        request.put("max_tokens",2048);
        request.put("top_p",1);
        request.put("frequency_penalty",0.0);
        request.put("presence_penalty",0.6);

        String json = JSONUtil.toJsonStr(request);
        String result = "";
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        String url = "https://api.openai.com/v1/completions";
        String openAiApiKey ="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";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection(proxy);
            connection.setRequestProperty("Authorization", "Bearer " + openAiApiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestMethod("POST");

            connection.setDoOutput(true);
            byte[] requestBodyBytes = json.getBytes(StandardCharsets.UTF_8);
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestBodyBytes, 0, requestBodyBytes.length);
            }
            try (InputStream inputStream = connection.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                result = response.toString();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(result);
    }
}

