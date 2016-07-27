package com.yixiao.util;

import org.apache.commons.io.FileUtils;

import java.io.*;

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

    public static String read(String filePath){
        File file = new File(filePath);
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void writeObject(Object object,String filePath){
        try {
            ObjectOutputStream os = new ObjectOutputStream(
                    new FileOutputStream(filePath));
            os.writeObject(object);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getObject(String filePath){
        Object obj = null;
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(filePath));
            obj = is.readObject();// 从流中读取User的数据
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
