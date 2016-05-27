package com.zhanghui.xml;

import android.sax.ElementListener;

import com.zhanghui.metric.HTTPTransactionType;
import com.zhanghui.mpd.SegmentBase;
import com.zhanghui.mpd.SegmentList;
import com.zhanghui.mpd.SegmentTemplate;
import com.zhanghui.mpd.URLType;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/21.
 */
public class URLTypeNode implements ElementListener {
    URLType urlType;
    SegmentBase segmentBase;
    SegmentList segmentList;
    SegmentTemplate segmentTemplate;
    HTTPTransactionType type;
    public URLTypeNode(SegmentBase segmentBase,SegmentList segmentList, SegmentTemplate segmentTemplate, HTTPTransactionType type) {
        this.segmentBase=segmentBase;
        this.segmentList=segmentList;
        this.segmentTemplate=segmentTemplate;
        this.type=type;
    }

    public void start(Attributes attributes) {
        urlType=new URLType();
        urlType.SetType(type);
        if (attributes.getIndex("sourceURL") != -1) {
            urlType.SetSourceURL(attributes.getValue(attributes.getIndex("sourceURL")));
        }
        if (attributes.getIndex("range") != -1) {
            urlType.SetRange(attributes.getValue(attributes.getIndex("range")));
        }
    }

    public void end() {
        switch (type){
            case InitializationSegment:
                if(segmentBase!=(null))
                    this.segmentBase.SetInitialization(urlType);
                if(segmentList!=(null))
                    this.segmentList.SetInitialization(urlType);
                if(segmentTemplate!=(null))
                    this.segmentTemplate.SetInitialization(urlType);
                break;
            case IndexSegment:
                if(segmentBase!=(null))
                    this.segmentBase.SetRepresentationIndex(urlType);
                if(segmentList!=(null))
                    this.segmentList.SetRepresentationIndex(urlType);
                if(segmentTemplate!=(null))
                    this.segmentTemplate.SetRepresentationIndex(urlType);
                break;
        }
    }
}
