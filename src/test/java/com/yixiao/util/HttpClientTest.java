package com.yixiao.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lilianglin on 2016/7/14.
 */
public class HttpClientTest {

    public static void main(String[] args) throws IOException {
//        HttpClient httpClient = new HttpClient();
//        String url = "http://www.ruibaotong.net";
//        String htmlCont = httpClient.getUrlStringByGet(url);
//
//        HtmlParser htmlParser = new HtmlParser();
//        System.out.println( htmlParser.getUrlByHtml(url,htmlCont) );

        String url = "http://anotherbug.blog.chinajavaworld.com/entry/4545/0/";
        //Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",Pattern.CASE_INSENSITIVE);

        //获取完整的域名
        Pattern p =Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        matcher.find();
        System.out.println(matcher.group());

    }

}
