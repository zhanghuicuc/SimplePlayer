package com.zhanghui.mpd;

import com.zhanghui.metric.HTTPTransactionType;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class URLType extends AbstractMPDElement implements IURLType,Serializable {
    String                         sourceURL;
    String                        range;
    HTTPTransactionType type;

    public void SetType(HTTPTransactionType type) {
        this.type = type;
    }

    @Override
    public String GetSourceURL() {
        return sourceURL;
    }

    public void SetSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    @Override
    public String GetRange() {
        return range;
    }

    public void SetRange(String range) {
        this.range = range;
    }

    public URLType() {
        sourceURL="";
        range="";
    }

    public Segment           ToSegment       (Vector<BaseUrl> baseurls){
        Segment seg = new Segment();

        if(seg.Init(baseurls, this.sourceURL, this.range, this.type))
            return seg;

        return null;
    }
}
