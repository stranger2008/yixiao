package com.yixiao.crawler;

import com.yixiao.crawler.common.CrawlerConstants;
import com.yixiao.crawler.store.FileService;
import com.yixiao.crawler.store.StoreService;
import com.yixiao.crawler.util.HtmlParser;
import com.yixiao.crawler.util.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lilianglin on 2016/7/14.
 */
public class SimpleCrawler {

    private Set<String> doingUrlList = new HashSet<String>();
    private Set<String> historyUrlList = new HashSet<String>();
    private Set<String> errorUrlList = new HashSet<String>();

    public static void main(String[] args) throws IOException {
        new SimpleCrawler().crawler("http://www.ruibaotong.net");
    }

    public void crawler(String url){
        if(historyUrlList.contains(url) || errorUrlList.contains(url)){
            return;
        }
        HttpClient httpClient = new HttpClient();
        String htmlCont = httpClient.getUrlStringByGet(url);
        if(htmlCont.equals("")){
            errorUrlList.add(url);
        }
        historyUrlList.add(url);
        doingUrlList.remove(url);
        HtmlParser htmlParser = new HtmlParser();
        List<String> urlList = htmlParser.getUrlByHtml(url,htmlCont);
        doingUrlList.addAll(urlList);
        if(doingUrlList.size() > 10000){
            writeUrlToStore();
            System.exit(-2);
        }
        for(String _url : doingUrlList){
            _url = removeLastSlash(_url);
            crawler(_url);
        }
    }

    public void writeUrlToStore(){
        StoreService storeService = new FileService();
        storeService.store(CrawlerConstants.doingUrlName,doingUrlList);
        storeService.store(CrawlerConstants.errorUrlName,errorUrlList);
        storeService.store(CrawlerConstants.historyUrlName,historyUrlList);
    }

    /**
     * 去掉url后面带的斜杠
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
