package com.zhanghui.mpd;

import com.zhanghui.metric.HTTPTransactionType;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class BaseUrl extends AbstractMPDElement implements IBaseUrl,Serializable {
    private String url;
    private String serviceLocation;
    private String byteRange;

    public BaseUrl() {
        url="";
        serviceLocation="";
        byteRange="";
        availabilityTimeOffset=0.0;
        availabilityTimeComplete=true;
    }

    @Override
    public String GetUrl() {
        return url;
    }

    public void SetUrl(String url) {
        this.url = url;
    }

    @Override
    public String GetServiceLocation() {
        return serviceLocation;
    }

    public void SetServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    @Override
    public String GetByteRange() {
        return byteRange;
    }

    public void SetByteRange(String byteRange) {
        this.byteRange = byteRange;
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

    private double		availabilityTimeOffset;
    private boolean		availabilityTimeComplete;

    public ISegment           ToMediaSegment      (Vector<BaseUrl> baseurls){
        Segment seg = new Segment();

        if(seg.Init(baseurls, this.url, this.byteRange, HTTPTransactionType.MediaSegment))
            return seg;

         return null;
    }
}
