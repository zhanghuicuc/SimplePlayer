package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.mpd.Period;
import com.zhanghui.mpd.Subset;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/22.
 */
public class SubsetNode implements ElementListener {
    Subset subset;
    Period period;

    public SubsetNode(Period period) {
        this.period = period;
    }

    public void start(Attributes attributes) {
        subset=new Subset();
        if (attributes.getIndex("contains") != -1) {
            subset.SetContains(attributes.getValue(attributes.getIndex("contains")));
        }
        if (attributes.getIndex("id") != -1) {
            subset.SetId((attributes.getValue(attributes.getIndex("id"))));
        }
    }

    public void end() {
        period.AddSubsets(this.subset);
    }
}
