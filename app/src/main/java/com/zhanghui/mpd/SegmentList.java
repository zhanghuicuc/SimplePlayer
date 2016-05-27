package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class SegmentList extends MultipleSegmentBase implements ISegmentList {
    String xlinkHref;
    String xlinkActuate;
    Vector<SegmentURL> segmentURLs;

    public SegmentList() {
        xlinkHref="";
        xlinkActuate="onRequest";
        segmentURLs=new Vector<SegmentURL>();
    }

    @Override
    public String GetXlinkHref() {
        return xlinkHref;
    }

    public void SetXlinkHref(String xlinkHref) {
        this.xlinkHref = xlinkHref;
    }

    @Override
    public String GetXlinkActuate() {
        return xlinkActuate;
    }

    public void SetXlinkActuate(String xlinkActuate) {
        this.xlinkActuate = xlinkActuate;
    }

    @Override
    public Vector<SegmentURL> GetSegmentURLs() {
        return segmentURLs;
    }

    public void AddSegmentURLs(SegmentURL segmentURL) {
        this.segmentURLs.add( segmentURL);
    }
}
