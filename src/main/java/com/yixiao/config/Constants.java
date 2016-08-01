package com.yixiao.config;

import java.io.File;

/**
 * 常量
 * Created by lilianglin on 2016/7/15.
 */
public class Constants {

    public static final String doingUrlName = "doingUrl";

    public static final String historyUrlName = "historyUrl";

    public static final String errorUrlName = "errorUrl";

    public static final String proPath = System.getProperty("user.dir");

    public static final String crawlerPath = proPath + File.separator + "crawler";

    public static final String urlPath = crawlerPath + File.separator + "url" + File.separator;

    public static final String pagePath = crawlerPath + File.separator + "page" + File.separator;

    public static final String indexPath = crawlerPath + File.separator + "index" + File.separator;

    //倒排列表索引文件名称
    public static final String postingList = "postingList";
    //文档索引文件名称
    public static final String documentName = "doc";
    //词典索引文件名称
    public static final String termName = "term";

    public static final String THREAD_COMMON_KEY_THREAD_NAME = "thread-name";

}
