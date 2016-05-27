package com.zhanghui.helper;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class myString {
    public static void Split   (String s, String delim, Vector<String> vector){

        StringTokenizer spliter=new StringTokenizer(s,delim);
        while(spliter.hasMoreTokens()){
            String item=spliter.nextToken();
            vector.add(item);
        }
    }
    /*
    * 以空格为分隔符切分原字符串，并转为数字保存
    */
    public static void Split   (String s, Vector<Integer> vector){
        StringTokenizer spliter=new StringTokenizer(s);
        while(spliter.hasMoreTokens()){
            String item=spliter.nextToken();
            int tmp=Integer.parseInt(item);
            vector.add(tmp);
        }
    }
    public static boolean ToBool  (String s){
        if(s.compareToIgnoreCase("true")==0)
            return true;
        else
            return false;
    }
}
