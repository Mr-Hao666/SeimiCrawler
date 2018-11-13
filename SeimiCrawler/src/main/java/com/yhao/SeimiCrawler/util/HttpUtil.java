package com.yhao.SeimiCrawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author long.hua
 * @version 1.0.0
 * @since 2016-01-21 21:39
 */
public class HttpUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private static int READ_TIMEOUT = 1000 * 10;
    private static int CONNECT_TIMEOUT = 1000 * 5;

    /**
     * 通过拼接的方式构造请求内容，实现参数传输
     *
     * @param actionUrl 访问的服务器URL
     * @param params    普通参数
     * @return 字符串返回结果
     * @throws IOException
     */
    public static String post(String actionUrl, Map<String, String> params) throws Exception {
        return postByte(actionUrl, params, null);
    }

    /**
     * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
     *
     * @param actionUrl 访问的服务器URL
     * @param params    普通参数
     * @param files     文件参数：key为文件名， value文件对象
     * @return 字符串返回结果
     * @throws IOException
     */
    public static String postFile(String actionUrl, Map<String, String> params, Map<String, File> files) throws Exception {
        if (files == null || files.isEmpty()) {
            return null;
        }

        Map<String, byte[]> byteMap = new HashMap<>();
        for (Map.Entry<String, File> entry : files.entrySet()) {
            byteMap.put(entry.getKey(), toBytes(entry.getValue()));
        }

        return postByte(actionUrl, params, byteMap);
    }

    private static byte[] toBytes(File file) throws Exception {
        return toBytes(new FileInputStream(file));
    }

    private static byte[] toBytes(InputStream is) throws Exception {
        byte[] data = new byte[is.available()];
        if (is.read(data) < 1) {
            throw new RuntimeException("读取数据失败!");
        }
        is.close();
        return data;
    }

    /**
     * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
     *
     * @param actionUrl 访问的服务器URL
     * @param params    普通参数
     * @param files     文件参数：key为文件名， value文件字节数组
     * @return 字符串返回结果
     * @throws IOException
     */
    public static String postByte(String actionUrl, Map<String, String> params, Map<String, byte[]> files) throws Exception {

        // HTTP的POST协议相关
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX1 = "--";
        String PREFIX2 = "--";
        String LINE_END = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";

        // 设置HTTP连接参数
        URL url = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(CONNECT_TIMEOUT); // 连接的最长时间
        conn.setReadTimeout(READ_TIMEOUT); // 读取的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + PREFIX1 + BOUNDARY);

        // 首先组拼文本类型的参数
        StringBuilder param = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            param.append(PREFIX1 + PREFIX2);
            param.append(BOUNDARY);
            param.append(LINE_END);
            param.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"").append(LINE_END);
            param.append(LINE_END);
            param.append(entry.getValue());
            param.append(LINE_END);
        }

        // 发送文本数据
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(param.toString().getBytes());

        // 发送文件数据
        if (files != null && !files.isEmpty()) {
            for (Map.Entry<String, byte[]> file : files.entrySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append(PREFIX1 + PREFIX2);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(file.getKey()).append("\"").append(LINE_END);
                sb.append("Content-Type: image/jpeg").append(LINE_END);
                sb.append(LINE_END);
                outStream.write(sb.toString().getBytes());

                outStream.write(file.getValue());
                outStream.write(LINE_END.getBytes());
            }

            // 请求结束标志
            byte[] endData = (PREFIX1 + PREFIX2 + BOUNDARY + PREFIX2 + LINE_END).getBytes();
            outStream.write(endData);
            outStream.flush();
        }

        // 得到响应码和结果
        int res = conn.getResponseCode();
        if (res == 200) {

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb.toString();
        }
        outStream.close();
        conn.disconnect();

        // 解析服务器返回来的数据
        return null;
    }

    public static void main(String[] args) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("name", "大地");
        param.put("form", "哇塞");
        param.put("age", "123");
        param.put("null", null);
        String result = get("http://localhost/test", param);
        System.out.println(result);


//        ------------------------------------------------------------------
//        Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
//        Map<String, String> paraMap = new HashMap<String, String>();
//        paraMap.put("bizType", "HEAD");
//        paraMap.put("fileType", "IMAGE");
//
//        byte[] data = FileUtil.read(new FileInputStream("C:\\Users\\long.hua\\Desktop\\nandaxue.png"));
//        fileMap.put("nandaxue.png", data);
//        String json = postByte("http://localhost:80/upload/upload", paraMap, fileMap);
//        System.out.println(json);
    }

    protected static String urlEncode(Map<String, Object> paramMap) throws UnsupportedEncodingException {
        if (paramMap == null || paramMap.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String key : paramMap.keySet()) {
            if (paramMap.get(key) != null) {
                sb.append(key).append("=").append(URLEncoder.encode(paramMap.get(key).toString(), "UTF-8")).append("&");
            }
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static String get(String url, Map<String, Object> param) {
        InputStream in = null;
        HttpURLConnection conn = null;
        try {

            String action = url + "?" + urlEncode(param);
            conn = (HttpURLConnection) new URL(action).openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestMethod("GET");
    
            System.out.println("========== url: " + action);

            in = conn.getInputStream();
            byte[] data = toBytes(in);
            return new String(data, "UTF-8");

        } catch (Exception e) {
            LOGGER.error("发送HTTP的GET请求异常！", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                LOGGER.error("发送HTTP的GET请求异常！", e);
            }
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                LOGGER.error("发送HTTP的GET请求异常！", e);
            }
        }

        return null;
    }

    /**
     * post请求
     *
     * @param url
     *            功能和操作
     * @param body
     *            要post的数据
     * @return
     * @throws IOException
     */
    public static String post(String url, String body) {
        System.out.println("url:" + System.lineSeparator() + url);
        System.out.println("body:" + System.lineSeparator() + body);

        String result = "";
        try
        {
            OutputStreamWriter out = null;
            BufferedReader in = null;
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            // 设置连接参数
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(20000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 提交数据
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(body);
            out.flush();

            // 读取返回数据
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = "";
            boolean firstLine = true; // 读第一行不加换行符
            while ((line = in.readLine()) != null)
            {
                if (firstLine)
                {
                    firstLine = false;
                } else
                {
                    result += System.lineSeparator();
                }
                result += line;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
