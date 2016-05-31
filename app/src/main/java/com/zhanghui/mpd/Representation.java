package com.zhanghui.mpd;

import com.zhanghui.helper.myString;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class Representation extends RepresentationBase implements IRepresentation,Serializable {
    public Representation() {
        segmentBase=null;
        segmentList=null;
        segmentTemplate=null;
        id="";
        bandwidth=0;
        qualityRanking=0;
        baseURLs=new Vector<BaseUrl>();
        subRepresentations=new Vector<SubRepresentation>();
        dependencyId=new Vector<String>();
        mediaStreamStructureId=new Vector<String>();
    }

    @Override
    public Vector<BaseUrl> GetBaseURLs() {
        return baseURLs;
    }

    public void AddBaseURLs(BaseUrl baseURL) {
        this.baseURLs.add(baseURL);
    }

    @Override
    public Vector<SubRepresentation> GetSubRepresentations() {
        return subRepresentations;
    }

    public void AddSubRepresentations(SubRepresentation subRepresentation) {
        this.subRepresentations.add(subRepresentation);
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
    public String GetId() {
        return id;
    }

    public void SetId(String id) {
        this.id = id;
    }

    @Override
    public int GetBandwidth() {
        return bandwidth;
    }

    public void SetBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    @Override
    public int GetQualityRanking() {
        return qualityRanking;
    }

    public void SetQualityRanking(int qualityRanking) {
        this.qualityRanking = qualityRanking;
    }

    @Override
    public Vector<String> GetDependencyId() {
        return dependencyId;
    }

    public void SetDependencyId(String dependencyId) {
        myString.Split(dependencyId," ",this.dependencyId);
    }

    @Override
    public Vector<String> GetMediaStreamStructureId() {
        return mediaStreamStructureId;
    }

    public void SetMediaStreamStructureId(String mediaStreamStructureId) {
        myString.Split(mediaStreamStructureId," ",this.mediaStreamStructureId);
    }

    private Vector<BaseUrl>              baseURLs;
    private Vector<SubRepresentation>    subRepresentations;
    private SegmentBase                         segmentBase;
    private SegmentList                         segmentList;
    private SegmentTemplate                     segmentTemplate;
    private String                         id;
    private int                            bandwidth;
    private int                            qualityRanking;
    private Vector<String> dependencyId;
    private Vector<String>            mediaStreamStructureId;
}
