package com.zhanghui.mpd;

import com.zhanghui.helper.Path;
import com.zhanghui.metric.HTTPTransactionType;
import com.zhanghui.network.AbstractChunk;

import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class Segment extends AbstractChunk implements ISegment,Serializable {
    public Segment() {
        host="";
        port=80;
        path="";
        startByte=0;
        endByte=0;
        hasByteRange=false;

    }



    public boolean Init(Vector<BaseUrl> baseurls, String uri, String range, HTTPTransactionType type)
    {
        this.absoluteuri = "";

        for(int i = 0; i < baseurls.size(); i++)
            this.absoluteuri = CombinePaths(this.absoluteuri, baseurls.elementAt(i).GetUrl());

        this.absoluteuri = CombinePaths(this.absoluteuri, uri);

        if (uri != "" && GetHostPortAndPath(this.absoluteuri))
        {

            if (range != "" && GetStartAndEndBytes(this.range))
            {
                this.hasByteRange  = true;
            }

            this.type = type;

            return true;
        }

        return false;
    }

    public String AbsoluteURI() {
        return absoluteuri;
    }

    public void AbsoluteURI(String absoluteuri) {
        this.absoluteuri = absoluteuri;
    }

    public void Host(String host) {
        this.host = host;
    }

    public void Port(int port) {
        this.port = port;
    }

    public void Path(String path) {
        this.path = path;
    }

    public void Range(String range) {
        this.range = range;
    }

    public void StartByte(int startByte) {
        this.startByte = startByte;
    }

    public void EndByte(int endByte) {
        this.endByte = endByte;
    }

    public void HasByteRange(boolean hasByteRange) {
        this.hasByteRange = hasByteRange;
    }

    @Override
    public String Host() {
        return host;
    }

    @Override
    public int Port() {
        return port;
    }

    @Override
    public String Path() {
        return path;
    }

    @Override
    public String Range() {
        return range;
    }

    @Override
    public int StartByte() {
        return startByte;
    }

    @Override
    public int EndByte() {
        return endByte;
    }

    public boolean HasByteRange() {
        return hasByteRange;
    }

    public HTTPTransactionType GetType() {
        return type;
    }

    /*
    * 拼接两个路径，确保中间有一个'/'分隔符
	*/
    public String              CombinePaths        ( String path1,  String path2){
        if(path1 .compareTo("")==0 )
            return path2;
        if(path2 .compareTo("")==0 )
            return path1;

        char path1Last  = path1.charAt(path1.length() - 1);
        char path2First = path2.charAt(0);

        if(path1Last == '/' && path2First == '/')
            return path1 + path2.substring(1, 1+path2.length());

        if(path1Last != '/' && path2First != '/')
            return path1 + "/" + path2;

        return path1 + path2;
    }

    public String              GetDirectoryPath    ( String path){
        int pos = path.lastIndexOf('/');
        return path.substring(0, pos);
    }

    /*
	* 以delim为分隔符切分string
	*/
    public Vector<String> Split               ( String s, String delim){
        Vector<String> ret=new Vector<String>();
        StringTokenizer spliter=new StringTokenizer(s,delim);
        while(spliter.hasMoreTokens()){
            String item=spliter.nextToken();
            ret.add(item);
        }
        return ret;
    }

    public boolean                     GetHostPortAndPath  ( String url){
        String hostPort="";
        int found=0;
        int pathBegin=0;
        if (url.substring(0, 7) .compareTo("http://") ==0 || url.substring(0, 8).compareTo("https://") ==0 )
        {
            found = url.indexOf("//");
            pathBegin = url.indexOf('/', found + 2);//在"//"之后的第一个'/'为路径的开始，如http://www.baidu.com:80/hahahahah.mp4
            path = url.substring(pathBegin);

            hostPort = url.substring(found + 2, pathBegin);
            found = hostPort.indexOf(':');
            if (found !=-1)
            {
                port = Integer.parseInt(hostPort.substring(found + 1));
                host = hostPort.substring(0, found);
            }
            else
                host = hostPort;
            return (host.length() > 0) && (path.length() > 0);
        }

        return false;
    }

    public boolean                     GetStartAndEndBytes ( String byteRange){
        int found = 0;

        found = byteRange.indexOf('-');
        if (found != -1 && found < byteRange.length()-1 )
        {
            startByte = Integer.parseInt(byteRange.substring(0, found));
            endByte = Integer.parseInt(byteRange.substring(found + 1));
            return (startByte <= endByte);
        }

        return false;
    }

    private String                         absoluteuri;
    private String                         host;
    private int                              port;
    private String                         path;
    private String                         range;
    private int                              startByte;
    private int                              endByte;
    private boolean                                hasByteRange;
    private HTTPTransactionType type;
}
