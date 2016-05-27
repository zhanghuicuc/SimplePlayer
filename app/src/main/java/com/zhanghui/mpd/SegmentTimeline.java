package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class SegmentTimeline extends AbstractMPDElement implements ISegmentTimeline {
    private Vector<ITimeline> timelines;

    public SegmentTimeline() {
        timelines=new Vector<ITimeline>();
    }

    @Override
    public Vector<ITimeline> GetTimelines() {
        return timelines;
    }

    public void AddTimelines(Timeline timelines) {
        this.timelines.add(timelines);
    }
}
