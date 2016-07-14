package com.yixiao.crawler;

import com.yixiao.crawler.util.HtmlParser;
import com.yixiao.crawler.util.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilianglin on 2016/7/14.
 */
public class SimpleCrawler {

    private List<String> doUrlList = new ArrayList<String>();
    private List<String> historyUrlList = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        new SimpleCrawler().crawler("http://www.sina.com.cn");
    }

    public void crawler(String url){
        if(historyUrlList.contains(url)){
            return;
        }
        HttpClient httpClient = new HttpClient();
        String htmlCont = httpClient.getUrlStringByGet(url);
        historyUrlList.add(url);
        doUrlList.remove(url);
        HtmlParser htmlParser = new HtmlParser();
        List<String> urlList = htmlParser.getUrlByHtml(url,htmlCont);
        doUrlList.addAll(urlList);
        if(doUrlList.size() > 3000){
            System.out.println(doUrlList);
            System.out.println(historyUrlList);
            System.exit(-2);
        }
        for(String _url : doUrlList){
            _url = removeLastSlash(_url);
            crawler(_url);
        }
    }

    /**
     *
     * @param url
     * @return
     */
    public String removeLastSlash(String url){
        String lastSymbol = url.substring(url.length() - 1,url.length());
        if(lastSymbol.equals("/")){
            url = url.substring(0,url.length()-1);
        }
        return url;
    }

}
