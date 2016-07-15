package com.yixiao.crawler.store;

import com.yixiao.crawler.common.CrawlerConstants;
import com.yixiao.crawler.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 文件存储
 * Created by lilianglin on 2016/7/15.
 */
public class FileService implements StoreService{

    @Override
    public boolean store(String storeType,Set<String> urlList) {
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
        Set<String> doList = new HashSet<String>();
        doList.add("http://www.ssss.net");
        doList.add("ddddddd");
        new FileService().store(CrawlerConstants.doingUrlName,doList);
    }

}
