package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.helper.myString;
import com.zhanghui.mpd.MPD;
import com.zhanghui.mpd.Period;
import com.zhanghui.mpd.SegmentBase;
import com.zhanghui.simpleplayer.SegmentManager;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/22.
 */
public class PeriodNode implements ElementListener {
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";
    MPD mpd;
    Period period;
    Element period_element;
    SegmentManager segmentManager;
    String mpdURL;

    public PeriodNode(MPD mpd, SegmentManager segmentManager, String mpdURL, Element period_element) {
        this.mpd = mpd;
        this.period_element = period_element;
        this.segmentManager = segmentManager;
        this.mpdURL = mpdURL;
    }

    public void start(Attributes attributes) {
        period=new Period();
        if (attributes.getIndex("xlink:href") != -1) {
            period.SetXlinkHref(attributes.getValue(attributes.getIndex("xlink:href")));
        }
        if (attributes.getIndex("xlink:actuate") != -1) {
            period.SetXlinkActuate(attributes.getValue(attributes.getIndex("xlink:actuate")));
        }
        if (attributes.getIndex("id") != -1) {
            period.SetId(attributes.getValue(attributes.getIndex("id")));
        }
        if (attributes.getIndex("start") != -1) {
            period.SetStart(attributes.getValue(attributes.getIndex("start")));
        }
        if (attributes.getIndex("duration") != -1) {
            period.SetDuration(attributes.getValue(attributes.getIndex("duration")));
        }
        if (attributes.getIndex("bitstreamSwitching") != -1) {
            period.SetBitstreamSwitching(myString.ToBool(attributes.getValue(attributes.getIndex("bitstreamSwitching"))));
        }
        //Get Period.BaseURL
        Element baseURL=period_element.getChild(NAMESPACE,"BaseURL");
        BaseUrlNode toBaseUrl=new BaseUrlNode(null,period,null,null,segmentManager,mpdURL);
        baseURL.setStartElementListener(toBaseUrl);
        baseURL.setEndTextElementListener(toBaseUrl);

        //Get Period.AdaptationSet
        Element adaptationSet=period_element.getChild(NAMESPACE,"AdaptationSet");
        AdaptationSetNode toAdaptationSet=new AdaptationSetNode(period,segmentManager,mpdURL,adaptationSet);
        adaptationSet.setElementListener(toAdaptationSet);
        //Get Period.Subset
        Element subset=period_element.getChild(NAMESPACE,"Subset");
        SubsetNode toSubset=new SubsetNode(period);
        subset.setElementListener(toSubset);
        //Get Period.SegmentBase
        Element segmentBase=period_element.getChild(NAMESPACE,"SegmentBase");
        SegmentBaseNode toSegmentBase=new SegmentBaseNode(this.period,null,null,segmentBase);
        segmentBase.setElementListener(toSegmentBase);
        //Get Period.SegmentList
        Element segmentList=period_element.getChild(NAMESPACE,"SegmentList");
        SegmentListNode toSegmentList=new SegmentListNode(this.period,null,null,segmentList);
        segmentList.setElementListener(toSegmentList);
        //Get Period.SegmentTemplate
        Element segmentTemplate=period_element.getChild(NAMESPACE,"SegmentTemplate");
        SegmentTemplateNode toSegmentTemplate=new SegmentTemplateNode(this.period,null,null,segmentTemplate);
        segmentTemplate.setElementListener(toSegmentTemplate);
    }

    public void end() {
        mpd.AddPeriods(this.period);

    }
}
