package com.yixiao.crawler.common;

import java.io.File;

/**
 * 爬虫常量
 * Created by lilianglin on 2016/7/15.
 */
public class CrawlerConstants {

    public static final String doingUrlName = "doingUrl";

    public static final String historyUrlName = "historyUrl";

    public static final String errorUrlName = "errorUrl";

    public static final String proPath = System.getProperty("user.dir");

    public static final String crawlerPath = proPath + File.separator + "crawler";

    public static final String urlPath = crawlerPath + File.separator + "url" + File.separator;

}
