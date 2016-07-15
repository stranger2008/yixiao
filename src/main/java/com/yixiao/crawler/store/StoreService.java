package com.yixiao.crawler.store;

import java.util.List;
import java.util.Set;

/**
 * 存储介质操作接口
 * Created by lilianglin on 2016/7/15.
 */
public interface StoreService {

    /**
     * 根据类型存储，待爬、已爬、无效
     * @param storeType
     * @param urlList
     * @return
     */
    public boolean store(String storeType,Set<String> urlList);

    /**
     * 根据类型获取url list
     * @param storeType
     * @return
     */
    public List<String> getUrlList(String storeType);

}
