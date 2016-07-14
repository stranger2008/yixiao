package com.yixiao.crawler.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * http抓取网页工具类
 * Created by lilianglin on 2016/7/14.
 */
public class HttpClient {

    /**
     * 根据url地址获取网页内容
     * @param url
     * @return
     * @throws IOException
     */
    public String getUrlStringByGet(String url){
        org.apache.http.client.HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response;
        StringBuffer sb = new StringBuffer();
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while((line = rd.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }



}
