package com.yixiao.crawler;

import com.yixiao.config.Constants;
import com.yixiao.crawler.store.FileService;
import com.yixiao.crawler.store.StoreService;
import com.yixiao.util.FileUtil;
import com.yixiao.util.HtmlParser;
import com.yixiao.util.HttpClient;

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

    private final static int pageNum = 100;

    public static void main(String[] args) throws IOException {
        SimpleCrawler simpleCrawler = new SimpleCrawler();
        //simpleCrawler.crawlerUrl("http://www.ruibaotong.net");
        simpleCrawler.crawlerUrl();
    }

    /**
     * 根据爬下来的url地址下载网页并保存到文件中
     */
    public void crawlerUrl() {
        List<String> _urlList = new ArrayList<String>();
        StoreService storeService = new FileService();
        List<String> doingList = storeService.getUrlList(Constants.doingUrlName);
        List<String> historyList = storeService.getUrlList(Constants.historyUrlName);
        _urlList.addAll(doingList);
        _urlList.addAll(historyList);
        HttpClient httpClient = new HttpClient();
        int i = 1;
        for(String url : _urlList){
            String htmlCont = httpClient.getUrlStringByGet(url);
            FileUtil.write(Constants.pagePath + i + ".html" ,htmlCont);
            i++;
        }

    }

    /**
     * 根据根url爬取相关链接
     * @param url
     */
    public void crawlerUrl(String url){
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
        if(doingUrlList.size() > pageNum){
            writeUrlToStore();
            System.exit(-2);
        }
        for(String _url : doingUrlList){
            _url = removeLastSlash(_url);
            crawlerUrl(_url);
        }
    }

    public void writeUrlToStore(){
        StoreService storeService = new FileService();
        storeService.store(Constants.doingUrlName,doingUrlList);
        storeService.store(Constants.errorUrlName,errorUrlList);
        storeService.store(Constants.historyUrlName, historyUrlList);
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
