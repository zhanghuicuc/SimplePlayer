package com.zhanghui.xml;

import android.sax.ElementListener;

import com.zhanghui.helper.myString;
import com.zhanghui.mpd.SegmentTimeline;
import com.zhanghui.mpd.Timeline;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/20.
 */
public class TimelineNode implements ElementListener {
    Timeline timeline;
    SegmentTimeline segmentTimeline;

    public TimelineNode(SegmentTimeline segmentTimeline) {
        this.segmentTimeline=segmentTimeline;
    }

    public void start(Attributes attributes) {
        timeline=new Timeline();
        if (attributes.getIndex("t") != -1) {
            timeline.SetStartTime(Integer.parseInt(attributes.getValue(attributes.getIndex("t"))));
        }
        if (attributes.getIndex("d") != -1) {
            timeline.SetDuration(Integer.parseInt(attributes.getValue(attributes.getIndex("d"))));
        }
        if (attributes.getIndex("r") != -1) {
            timeline.SetRepeatCount(Integer.parseInt(attributes.getValue(attributes.getIndex("r"))));
        }
    }

    public void end() {
        segmentTimeline.AddTimelines(timeline);
    }
}
