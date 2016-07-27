package com.yixiao.index;

import com.yixiao.index.model.Posting;
import com.yixiao.util.FileUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by lilianglin on 2016/7/27.
 */
public class SimpleSearch {
    public static void main(String[] args) {
        Map<String,List<Posting>> mapp = (Map<String, List<Posting>>) FileUtil.getObject("D:\\workspace_github\\yixiao\\crawler\\index\\index");
        System.out.println(mapp);
        System.out.println(mapp.get("我们"));
    }
}
