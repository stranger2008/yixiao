package com.yixiao.crawler.store;

import com.yixiao.config.Constants;
import com.yixiao.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

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
        return FileUtil.write(Constants.urlPath + storeType,sb.toString());
    }

    @Override
    public List<String> getUrlList(String storeType) {
        String fStr = FileUtil.read(Constants.urlPath + storeType);
        String fileStr[] = fStr.split("\n");
        return Arrays.asList(fileStr);
    }

    public static void main(String[] args) {
        Set<String> doList = new HashSet<String>();
        doList.add("http://www.ssss.net");
        doList.add("ddddddd");
        new FileService().store(Constants.doingUrlName,doList);
    }

}
