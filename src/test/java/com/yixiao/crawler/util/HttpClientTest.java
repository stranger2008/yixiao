package com.yixiao.crawler.util;

import java.io.IOException;

/**
 * Created by lilianglin on 2016/7/14.
 */
public class HttpClientTest {

    public static void main(String[] args) throws IOException {
        HttpClient httpClient = new HttpClient();
        String url = "http://www.ruibaotong.net";
        String htmlCont = httpClient.getUrlStringByGet(url);
        //System.out.println(htmlCont);

        HtmlParser htmlParser = new HtmlParser();
        System.out.println( htmlParser.getUrlByHtml(url,htmlCont) );
    }

}
