package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class Period extends AbstractMPDElement implements IPeriod {
    public Period() {
        segmentBase=null;
        segmentList=null;
        segmentTemplate=null;
        xlinkActuate="onRequest";
        xlinkHref="";
        id="";
        start="";
        duration="";
        isBitstreamSwitching=false;
        baseURLs=new Vector<BaseUrl>();
        adaptationSets=new Vector<AdaptationSet>();
        subsets=new Vector<Subset>();
    }

    @Override
    public Vector<BaseUrl> GetBaseURLs() {
        return baseURLs;
    }

    public void AddBaseURLs(BaseUrl baseURL) {
        this.baseURLs.add(baseURL);
    }

    @Override
    public SegmentBase GetSegmentBase() {
        return segmentBase;
    }

    public void SetSegmentBase(SegmentBase segmentBase) {
        this.segmentBase = segmentBase;
    }

    @Override
    public SegmentList GetSegmentList() {
        return segmentList;
    }

    public void SetSegmentList(SegmentList segmentList) {
        this.segmentList = segmentList;
    }

    @Override
    public SegmentTemplate GetSegmentTemplate() {
        return segmentTemplate;
    }

    public void SetSegmentTemplate(SegmentTemplate segmentTemplate) {
        this.segmentTemplate = segmentTemplate;
    }

    @Override
    public Vector<AdaptationSet> GetAdaptationSets() {
        return adaptationSets;
    }

    public void AddAdaptationSets(AdaptationSet adaptationSet) {
        this.adaptationSets.add(adaptationSet);
    }

    @Override
    public Vector<Subset> GetSubsets() {
        return subsets;
    }

    public void AddSubsets(Subset subset) {
        this.subsets.add(subset);
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
    public String GetId() {
        return id;
    }

    public void SetId(String id) {
        this.id = id;
    }

    @Override
    public String GetStart() {
        return start;
    }

    public void SetStart(String start) {
        this.start = start;
    }

    @Override
    public String GetDuration() {
        return duration;
    }

    public void SetDuration(String duration) {
        this.duration = duration;
    }

    public boolean GetBitstreamSwitching() {
        return isBitstreamSwitching;
    }

    public void SetBitstreamSwitching(boolean isBitstreamSwitching) {
        this.isBitstreamSwitching = isBitstreamSwitching;
    }

    private Vector<BaseUrl> baseURLs;
    private SegmentBase                     segmentBase;
    private SegmentList                     segmentList;
    private SegmentTemplate                 segmentTemplate;
    private Vector<AdaptationSet>    adaptationSets;
    private Vector<Subset>           subsets;
    private String                     xlinkHref;
    private String                     xlinkActuate;
    private String                     id;
    private String                     start;
    private String                     duration;
    private boolean                            isBitstreamSwitching;

}
