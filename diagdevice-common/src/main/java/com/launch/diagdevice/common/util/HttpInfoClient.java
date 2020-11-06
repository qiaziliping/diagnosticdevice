package com.launch.diagdevice.common.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author hugo
 * 
 */
public class HttpInfoClient
{
    private static int socketTimeout = 50000;  
    private static int connectTimeout = 50000;  
    private static int connectionRequestTimeout = 50000;  
    private static int maxConnTotal = 200;   //最大不要超过1000  
    private static int maxConnPerRoute = 100;//实际的单个连接池大小，如tps定为50，那就配置50  

    /**
     * 单例
     */
    private static HttpInfoClient instance      = new HttpInfoClient();

    /**
     * HttpClient
     */
    private static HttpClient     client;

    private String                targerServerUrl;

    private HttpInfoClient()
    {
    }

    public static HttpInfoClient getInstance()
    {
        RequestConfig config = RequestConfig.custom()  
            .setSocketTimeout(socketTimeout)  
            .setConnectTimeout(connectTimeout)  
            .setConnectionRequestTimeout(connectionRequestTimeout).build(); 
        
        if (client == null) {
            client = HttpClients.custom().setDefaultRequestConfig(config)  
                .setMaxConnTotal(maxConnTotal)  
                .setMaxConnPerRoute(maxConnPerRoute).build(); 
        }
        return instance;
    }

    public String getServerUrl()
    {
        return this.targerServerUrl;
    }


    /**
     * post pic
     * 
     * @param url
     * @param params
     * @return
     */
    /*---------------------------------------------Interface Call ---------------------------------------*/
    /**
     * 发送post请求
     * 
     * @param url
     *            服务器地址 params Map封装的请求参数
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String sendPostRaw(String url, String json)
    {
        try {
//            HttpClient httpClient = new DefaultHttpClient();  
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);  
            StringEntity postingString = new StringEntity(json);// json传递  
            postingString.setContentEncoding("UTF-8");
            post.setEntity(postingString);  
            post.setHeader("Content-type", "application/json; charset=UTF-8"); 
           // post.setHeader("Accept-Charset", "utf-8");  
           // post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            HttpResponse response = httpClient.execute(post); 
            if (response.getStatusLine().getStatusCode() == 200) {
                // 将返回的实体转换为String
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                return result;
            }else{
                System.out.println(response.getStatusLine().getStatusCode());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 发送post请求
     * 
     * @param url
     *            服务器地址 params Map封装的请求参数
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String sendPost(String url, Map<String, String> params)
    {
        try {
            HttpPost post = new HttpPost(url);
            // 封装参数
            List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
            if (params != null) {
                for (Map.Entry<String, String> item : params.entrySet()) {
                    BasicNameValuePair pair = new BasicNameValuePair(item.getKey(), item.getValue());
                    parameters.add(pair);
                }
                HttpEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");
                post.setEntity(entity);
            }
            // 提交请求
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                // 将返回的实体转换为String
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(result);
                return result;
            }else{
                System.out.println(response.getStatusLine().getStatusCode());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送get请求
     * 
     * @param url
     * @return
     * @throws Exception
     */
    public String sendGet(String url)
    {
        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
//                System.out.println(result);
                return result;
            }else{
                System.out.println(response.getStatusLine().getStatusCode());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 
     * @param requestUrl  请求url
     * @param requestMethod get请求或者post请求
     * @param outputStr   请求的字符串参数
     * @return
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr){
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}");
        } catch (Exception e) {
            System.out.println("https请求异常：{}");
        }
        return null;
    }
    
    
    
    public static void main(String[] args)  
    {  
        // 创建默认的客户端实例  
        HttpClient httpCLient = HttpClientBuilder.create().build(); 
          
        // 创建get请求实例  
        HttpGet httpget = new HttpGet("http://test.lohas-tech.cn/LohasWx/CreateQRCode?ICCID=123456");  
          
        System.out.println("executing request "+httpget.getURI());  
          
        try  
        {  
              
            // 客户端执行get请求 返回响应实体  
            HttpResponse response = httpCLient.execute(httpget);  
              
            // 服务器响应状态行  
            System.out.println(response.getStatusLine());  
             
          
            Header[] heads = response.getAllHeaders();  
            // 打印所有响应头  
            for(Header h:heads){  
                System.out.println(h.getName()+":"+h.getValue());  
            }  
              
            // 获取响应消息实体  
            HttpEntity entity = response.getEntity();  
              
            System.out.println("------------------------------------");  
              
              
              
            if(entity != null){  
                                  
                //响应内容  
                System.out.println(EntityUtils.toString(entity));  
                  
                System.out.println("----------------------------------------");  
                // 响应内容长度  
                System.out.println("响应内容长度："+entity.getContentLength());  
            }  
              
        } catch (ClientProtocolException e){  
            e.printStackTrace();  
        } catch (IOException e){  
            e.printStackTrace();  
        }finally{  
            httpCLient.getConnectionManager().shutdown();  
        }  
    }  
  
}
