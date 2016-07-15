package com.yixiao.crawler.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文件操作工具
 * Created by lilianglin on 2016/7/15.
 */
public class FileUtil {

    public static boolean write(String filePath,String fileCont){
        File file = new File(filePath);
        try {
            FileUtils.writeStringToFile(file,fileCont);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
