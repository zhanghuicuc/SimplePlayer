package com.zhanghui.mpd;

import com.zhanghui.helper.Path;
import com.zhanghui.metric.HTTPTransactionType;
import com.zhanghui.network.AbstractChunk;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class Segment extends AbstractChunk implements ISegment{
    public Segment() {
        host="";
        port=0;
        path="";
        startByte=0;
        endByte=0;
        hasByteRange=false;
    }

    public boolean Init(Vector<IBaseUrl> baseurls, String uri, String range, HTTPTransactionType type)
    {
        String host        = "";
        int      port        = 80;
        String path        = "";
        int      startByte   = 0;
        int      endByte     = 0;

        this.absoluteuri = "";

        for(int i = 0; i < baseurls.size(); i++)
            this.absoluteuri = Path.CombinePaths(this.absoluteuri, baseurls.elementAt(i).GetUrl());

        this.absoluteuri = Path.CombinePaths(this.absoluteuri, uri);

        if (uri != "" && Path.GetHostPortAndPath(this.absoluteuri, host, port, path))
        {
            this.host = host;
            this.port = port;
            this.path = path;

            if (range != "" && Path.GetStartAndEndBytes(range, startByte, endByte))
            {
                this.range         = range;
                this.hasByteRange  = true;
                this.startByte     = startByte;
                this.endByte       = endByte;
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
