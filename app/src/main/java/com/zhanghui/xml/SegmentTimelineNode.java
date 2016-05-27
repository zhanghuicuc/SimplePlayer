package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.mpd.SegmentList;
import com.zhanghui.mpd.SegmentTemplate;
import com.zhanghui.mpd.SegmentTimeline;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/22.
 */
public class SegmentTimelineNode implements ElementListener{
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";
    SegmentTimeline segmentTimeline;
    SegmentList segmentList;
    SegmentTemplate segmentTemplate;
    Element segtimeline;
    public SegmentTimelineNode(SegmentList segmentList,SegmentTemplate segmentTemplate,Element segtimeline) {
        this.segmentList = segmentList;
        this.segmentTemplate=segmentTemplate;
        this.segtimeline=segtimeline;
    }

    public void start(Attributes attributes) {
        this.segmentTimeline=new SegmentTimeline();
        //Get SegmentTimeline.S
        Element S=segtimeline.getChild(NAMESPACE,"S");
        TimelineNode toS=new TimelineNode(this.segmentTimeline);
        S.setElementListener(toS);
    }

    public void end() {
        if(segmentList!=(null))
            segmentList.SetSegmentTimeline(segmentTimeline);
        if(segmentTemplate!=(null))
            segmentTemplate.SetSegmentTimeline(segmentTimeline);

    }
}
