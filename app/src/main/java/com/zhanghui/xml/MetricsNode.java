package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.mpd.MPD;
import com.zhanghui.mpd.Metrics;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/22.
 */
public class MetricsNode implements ElementListener {
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";
    Metrics metrics;
    MPD mpd;
    Element metric_element;

    public MetricsNode(MPD mpd, Element metric_element) {
        this.mpd = mpd;
        this.metric_element = metric_element;
    }

    public void start(Attributes attributes) {
        metrics=new Metrics();
        if (attributes.getIndex("metrics") != -1) {
            metrics.SetMetrics(attributes.getValue(attributes.getIndex("metrics")));
        }
        //Get Metrics.Reporting
        Element reporting=metric_element.getChild(NAMESPACE,"Reporting");
        DescriptorNode toReporting=new DescriptorNode(null,null,null,null,metrics,"Reporting");
        reporting.setElementListener(toReporting);
        //Get Metrics.Range
        Element range=metric_element.getChild(NAMESPACE,"Range");
        RangeNode toRange=new RangeNode(metrics);
        range.setElementListener(toRange);
    }

    public void end() {

        mpd.AddMetrics(this.metrics);

    }

}
