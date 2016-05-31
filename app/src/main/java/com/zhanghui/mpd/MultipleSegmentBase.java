package com.zhanghui.mpd;

import java.io.Serializable;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class MultipleSegmentBase extends SegmentBase implements IMultipleSegmentBase ,Serializable {
    private SegmentTimeline                 segmentTimeline;
    private URLType                         bitstreamSwitching;
    private int                        duration;
    private int                        startNumber;

    public MultipleSegmentBase() {
        bitstreamSwitching=null;
        segmentTimeline=null;
        duration=0;
        startNumber=0;
    }

    @Override
    public SegmentTimeline GetSegmentTimeline() {
        return segmentTimeline;
    }

    public void SetSegmentTimeline(SegmentTimeline segmentTimeline) {
        this.segmentTimeline = segmentTimeline;
    }

    @Override
    public URLType GetBitstreamSwitching() {
        return bitstreamSwitching;
    }

    public void SetBitstreamSwitching(URLType bitstreamSwitching) {
        this.bitstreamSwitching = bitstreamSwitching;
    }

    @Override
    public int GetDuration() {
        return duration;
    }

    public void SetDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int GetStartNumber() {
        return startNumber;
    }

    public void SetStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }
}
