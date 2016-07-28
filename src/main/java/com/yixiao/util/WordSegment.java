package com.yixiao.util;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 分词器
 * Created by lilianglin on 2016/7/28.
 */
public class WordSegment {

    /**
     * 分词并获取词频
     * @param cont
     * @return
     */
    public static Map<String,Integer> getWordFreq(String cont){
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
