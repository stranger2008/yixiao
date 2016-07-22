package com.yixiao.index;

import org.apache.commons.io.FileUtils;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lilianglin on 2016/7/22.
 */
public class SimpleIndex {

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\workspace_github\\yixiao\\crawler\\page\\1.html";
        String fileCont = FileUtils.readFileToString(new File(filePath));
        SimpleIndex simpleIndex = new SimpleIndex();
        System.out.println(simpleIndex.getWord(fileCont));
    }

    public Map<String,Integer> getWord(String cont){
        StringReader re = new StringReader(cont);
        IKSegmentation ik = new IKSegmentation(re,true);
        Lexeme lex = null;
        Map<String,Integer> wordMap = new HashMap<String,Integer>();
        try {
            while((lex=ik.next())!=null){
                String word=lex.getLexemeText();
                if (!wordMap.containsKey(word)) {
                    wordMap.put(word, 1);
                }else{
                    wordMap.put(word, wordMap.get(word)+1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordMap;
    }

}
