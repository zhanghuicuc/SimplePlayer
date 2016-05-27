package com.zhanghui.xml;

import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

import com.zhanghui.helper.myString;
import com.zhanghui.mpd.AdaptationSet;
import com.zhanghui.mpd.BaseUrl;
import com.zhanghui.mpd.MPD;
import com.zhanghui.mpd.Period;
import com.zhanghui.mpd.Representation;
import com.zhanghui.simpleplayer.SegmentManager;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/20.
 */
public class BaseUrlNode implements StartElementListener,EndTextElementListener {
    private MPD mpd;
    private Period period;
    private AdaptationSet adaptationSet;
    private BaseUrl baseUrl;
    private Representation representation;
    private SegmentManager segmentManager;
    private String mpdURL;
    public BaseUrlNode(MPD mpd,Period period,AdaptationSet adaptationSet, Representation representation,SegmentManager segmentManager, String mpdURL) {
        this.mpd=mpd;
        this.period=period;
        this.adaptationSet=adaptationSet;
        this.representation=representation;
        this.mpdURL=mpdURL;
        this.segmentManager=segmentManager;
    }

    @Override
    public void start(Attributes attributes) {
        baseUrl=new BaseUrl();
        if (attributes.getIndex("serviceLocation") != -1) {
            baseUrl.SetServiceLocation(attributes.getValue(attributes.getIndex("serviceLocation")));
        }
        if (attributes.getIndex("byteRange") != -1) {
            baseUrl.SetByteRange(attributes.getValue(attributes.getIndex("byteRange")));
        }
        if (attributes.getIndex("availabilityTimeOffset") != -1) {
            double availabilityTimeOffset = Double.parseDouble(attributes.getValue(attributes.getIndex("availabilityTimeOffset")));
            baseUrl.SetAvailabilityTimeOffset(availabilityTimeOffset);
        }
        if (attributes.getIndex("availabilityTimeComplete") != -1) {
            boolean availabilityTimeComplete = myString.ToBool(attributes.getValue(attributes.getIndex("availabilityTimeComplete")));
            baseUrl.SetAvailabilityTimeComplete(availabilityTimeComplete);
        }
    }

    @Override
    public void end(String body) {
        segmentManager.setBaseURL(body);
        if (body.compareTo("./") == 0)
            baseUrl.SetUrl(mpdURL);
        else
            baseUrl.SetUrl(body);
        if(mpd!=(null))
            mpd.AddBaseUrls(baseUrl);
        if(period!=(null))
            period.AddBaseURLs(baseUrl);
        if(adaptationSet!=(null))
            adaptationSet.AddBaseURLs(baseUrl);
        if(representation!=(null))
            representation.AddBaseURLs(baseUrl);
    }
}
