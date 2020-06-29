package cn.itcast.jd.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author kai
 * @date 2020/5/21 12:03
 */
@Component
public class HttpUtils {
    //建立连接池
    private PoolingHttpClientConnectionManager cm;
    public HttpUtils() {
    //线程池，进行线程管理
        this.cm =new PoolingHttpClientConnectionManager();

        this.cm.setMaxTotal(100);
        this.cm.setDefaultMaxPerRoute(10);
    }
    //根据请求地址下载页面数据
    public String doGetHtml(String url){
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        HttpGet httpGet=new HttpGet(url);
        httpGet.setConfig(this.getConfig());
        //设置请求Request Headers中的User-Agent，告诉京东说我这是浏览器访问，您只需要做一个安静的美男子就够了
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        CloseableHttpResponse response=null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode()==200){
                if (response.getEntity()!=null){
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response !=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    //下载图片，返回名称
    public String doGetImage(String url){
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        HttpGet httpGet=new HttpGet(url);
        httpGet.setConfig(this.getConfig());
        CloseableHttpResponse response=null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode()==200){
                if (response.getEntity()!=null){
                    //图片后缀
                    String extName = url.substring(url.lastIndexOf("."));
                    //重命名
                    String picName = UUID.randomUUID().toString()+extName;
                    //下载图片
                    OutputStream outputStream=new FileOutputStream(new File("C:\\Users\\da\\Desktop\\images\\"+picName));
                    response.getEntity().writeTo(outputStream);
                    return picName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response !=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
    //设置请求信息
    private RequestConfig getConfig() {
        RequestConfig config=RequestConfig.custom()
                .setConnectTimeout(1000)  //创建连接的最长时间
                .setConnectionRequestTimeout(500) //获取连接的最长时间
                .setSocketTimeout(100000)  //数据传输的最长时间
                .build();
        return config;
    }
}
