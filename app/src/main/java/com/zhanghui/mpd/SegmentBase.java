package com.zhanghui.mpd;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class SegmentBase extends AbstractMPDElement implements ISegmentBase {
    private URLType     initialization;
    private URLType     representationIndex;
    private int    timescale;
    private int    presentationTimeOffset;
    private String timeShiftBufferDepth;

    public SegmentBase() {
        initialization=null;
                representationIndex=null;
                timescale=0;
                presentationTimeOffset=0;
                indexRange="";
                indexRangeExact=false;
                availabilityTimeOffset=0.0;
                availabilityTimeComplete=true;
    }

    @Override
    public URLType GetRepresentationIndex() {
        return representationIndex;
    }

    public void SetRepresentationIndex(URLType representationIndex) {
        this.representationIndex = representationIndex;
    }

    @Override
    public URLType GetInitialization() {
        return initialization;
    }

    public void SetInitialization(URLType initialization) {
        this.initialization = initialization;
    }

    @Override
    public int GetTimescale() {
        return timescale;
    }

    public void SetTimescale(int timescale) {
        this.timescale = timescale;
    }

    @Override
    public int GetPresentationTimeOffset() {
        return presentationTimeOffset;
    }

    public void SetPresentationTimeOffset(int presentationTimeOffset) {
        this.presentationTimeOffset = presentationTimeOffset;
    }

    @Override
    public String GetTimeShiftBufferDepth() {
        return timeShiftBufferDepth;
    }

    public void SetTimeShiftBufferDepth(String timeShiftBufferDepth) {
        this.timeShiftBufferDepth = timeShiftBufferDepth;
    }

    @Override
    public String GetIndexRange() {
        return indexRange;
    }

    public void SetIndexRange(String indexRange) {
        this.indexRange = indexRange;
    }

    public boolean HasIndexRangeExact() {
        return indexRangeExact;
    }

    public void SetIndexRangeExact(boolean indexRangeExact) {
        this.indexRangeExact = indexRangeExact;
    }

    @Override
    public double GetAvailabilityTimeOffset() {
        return availabilityTimeOffset;
    }

    public void SetAvailabilityTimeOffset(double availabilityTimeOffset) {
        this.availabilityTimeOffset = availabilityTimeOffset;
    }

    public boolean HasAvailabilityTimeComplete() {
        return availabilityTimeComplete;
    }

    public void SetAvailabilityTimeComplete(boolean availabilityTimeComplete) {
        this.availabilityTimeComplete = availabilityTimeComplete;
    }

    private String indexRange;
    private boolean        indexRangeExact;
    private double		availabilityTimeOffset;
    private  boolean		availabilityTimeComplete;
}
