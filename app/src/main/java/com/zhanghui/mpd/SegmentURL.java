package com.zhanghui.mpd;

import java.util.Vector;
import com.zhanghui.metric.HTTPTransactionType;
/**
 * Created by zhanghui on 2016/5/19.
 */
public class SegmentURL extends AbstractMPDElement implements ISegmentURL {
    private String mediaURI;
    private String mediaRange;
    private String indexURI;
    private String indexRange;

    public SegmentURL() {
        mediaRange="";
        mediaURI="";
        indexRange="";
        indexURI="";
    }

    public ISegment  ToMediaSegment  (Vector<IBaseUrl> baseurls){
        Segment seg = new Segment();

        if(seg.Init(baseurls, this.mediaURI, this.mediaRange, HTTPTransactionType.MediaSegment))
            return seg;


        return null;
    }

    public ISegment  ToIndexSegment  (Vector<IBaseUrl> baseurls){
        Segment seg = new Segment();

        if(seg.Init(baseurls, this.indexURI, this.indexRange, HTTPTransactionType.IndexSegment))
            return seg;

        return null;
    }

    @Override
    public String GetMediaURI() {
        return mediaURI;
    }

    public void SetMediaURI(String mediaURI) {
        this.mediaURI = mediaURI;
    }

    @Override
    public String GetMediaRange() {
        return mediaRange;
    }

    public void SetMediaRange(String mediaRange) {
        this.mediaRange = mediaRange;
    }

    @Override
    public String GetIndexURI() {
        return indexURI;
    }

    public void SetIndexURI(String indexURI) {
        this.indexURI = indexURI;
    }

    @Override
    public String GetIndexRange() {
        return indexRange;
    }

    public void SetIndexRange(String indexRange) {
        this.indexRange = indexRange;
    }
}
