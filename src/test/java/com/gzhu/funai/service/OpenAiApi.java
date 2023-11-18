/*
package com.gzhu.funai.service;

public class OpenAiApi {


    public String OpenAiAnswerer(CompletionRequest request, String openAiApiKey) {

        //代理
        String proxyHost = "代理地址";
        int proxyPort = 代理端口;
        String url = "https://api.openai.com/v1/completions";
        // json为请求体
        String json = JSONUtil.toJsonStr(request);
        String result = "";
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
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

        return JSONUtil.toBean(result, CompletionResponse.class);
    }

}*/
