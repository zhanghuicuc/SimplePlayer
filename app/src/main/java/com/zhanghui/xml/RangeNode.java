package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.mpd.Metrics;
import com.zhanghui.mpd.Range;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/22.
 */
public class RangeNode implements ElementListener {
    Range range;
    Metrics metrics;

    public RangeNode(Metrics metrics) {
        this.metrics = metrics;
    }

    public void start(Attributes attributes) {
        range=new Range();
        if (attributes.getIndex("starttime") != -1) {
            range.SetStarttime(attributes.getValue(attributes.getIndex("starttime")));
        }
        if (attributes.getIndex("duration") != -1) {
            range.SetDuration(attributes.getValue(attributes.getIndex("duration")));
        }
    }

    public void end() {
        metrics.AddRanges(this.range);
    }
}
