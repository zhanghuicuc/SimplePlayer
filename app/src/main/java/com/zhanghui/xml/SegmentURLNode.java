package com.zhanghui.xml;

import android.sax.ElementListener;

import com.zhanghui.mpd.SegmentList;
import com.zhanghui.mpd.SegmentURL;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/22.
 */
public class SegmentURLNode implements ElementListener {
    SegmentList segmentList;
    SegmentURL segmentURL;

    public SegmentURLNode(SegmentList segmentList) {
        this.segmentList = segmentList;
    }

    public void start(Attributes attributes) {
        this.segmentURL=new SegmentURL();
        if (attributes.getIndex("media") != -1) {
            segmentURL.SetMediaURI(attributes.getValue(attributes.getIndex("media")));
        }
        if (attributes.getIndex("mediaRange") != -1) {
            segmentURL.SetMediaRange(attributes.getValue(attributes.getIndex("mediaRange")));
        }
        if (attributes.getIndex("index") != -1) {
            segmentURL.SetIndexURI(attributes.getValue(attributes.getIndex("index")));
        }
        if (attributes.getIndex("indexRange") != -1) {
            segmentURL.SetIndexRange(attributes.getValue(attributes.getIndex("indexRange")));
        }
    }

    public void end() {
        segmentList.AddSegmentURLs(segmentURL);
    }

}
