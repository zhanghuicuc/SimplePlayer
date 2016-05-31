package com.zhanghui.mpd;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class Metrics extends AbstractMPDElement implements IMetrics,Serializable {
    private Vector<Descriptor> reportings;
    private Vector<Range> ranges;
    private String metrics;

    public Metrics() {
        this.reportings = new Vector<Descriptor>();
        this.ranges = new Vector<Range>();
        this.metrics = "";
    }

    @Override
    public Vector<Descriptor> GetReportings() {
        return reportings;
    }

    @Override
    public Vector<Range> GetRanges() {
        return ranges;
    }

    @Override
    public String GetMetrics() {
        return metrics;
    }

    public void AddReportings(Descriptor reporting) {
        this.reportings.add(reporting);
    }

    public void AddRanges(Range range) {
        this.ranges.add(range);
    }

    public void SetMetrics(String metrics) {
        this.metrics = metrics;
    }
}
