package com.yixiao.crawler.util;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 网页内容解析工具类
 * Created by lilianglin on 2016/7/14.
 */
public class HtmlParser {

    /**
     * 根据html网页内容获取链接地址
     * @param url
     * @param htmlCont
     * @return
     */
    public List<String> getUrlByHtml(String url,String htmlCont){
        if(htmlCont == null){
            return Collections.emptyList();
        }
        Parser parser = Parser.createParser(htmlCont, "GBK");
        HtmlPage page = new HtmlPage(parser);
        try {
            parser.visitAllNodesWith(page);
        } catch (ParserException e1) {
            e1.printStackTrace();
        }
        NodeList nodelist = page.getBody();
        NodeFilter filter = new TagNameFilter("A");
        nodelist = nodelist.extractAllNodesThatMatch(filter, true);
        List<String> urlList = new ArrayList<String>(nodelist.size());
        for (int i = 0; i < nodelist.size(); i++) {
            LinkTag link = (LinkTag) nodelist.elementAt(i);
            //System.out.println(link.getStringText());
            String href = link.getAttribute("href");
            if(href == null){
                continue;
            }
            if(!href.startsWith("http://")){
                href = url + href;
            }
            if(!urlList.contains(href)){
                urlList.add(href);
            }
        }
        return urlList;
    }

}
