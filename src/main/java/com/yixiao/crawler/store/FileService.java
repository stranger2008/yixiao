package com.yixiao.crawler.store;

import com.yixiao.crawler.common.CrawlerConstants;
import com.yixiao.crawler.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件存储
 * Created by lilianglin on 2016/7/15.
 */
public class FileService implements StoreService{

    @Override
    public boolean store(String storeType,List<String> urlList) {
        if(storeType.equals("") || CollectionUtils.isEmpty(urlList)){
            return false;
        }
        StringBuilder sb = new StringBuilder();
        for(String url : urlList){
            sb.append(url);
            sb.append("\n");
        }
        return FileUtil.write(CrawlerConstants.urlPath + storeType,sb.toString());
    }

    @Override
    public List<String> getUrlList(String storeType) {
        return null;
    }

    public static void main(String[] args) {
        List<String> doList = new ArrayList<String>();
        doList.add("http://www.ssss.net");
        doList.add("ddddddd");
        new FileService().store(CrawlerConstants.doingUrlName,doList);
    }

}
