package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class AdaptationSet extends RepresentationBase implements IAdaptationSet {
    public AdaptationSet() {
        segmentBase=null;
                segmentList=null;
                segmentTemplate=null;
                xlinkHref="";
                xlinkActuate="onRequest";
                id=0;
                lang="";
                contentType="";
                par="";
                minBandwidth=0;
                maxBandwidth=0;
                minWidth=0;
                maxWidth=0;
                minHeight=0;
                maxHeight=0;
                minFramerate="";
                maxFramerate="";
                segmentAlignmentIsBool=true;
                subsegmentAlignmentIsBool=true;
                usesSegmentAlignment=false;
                usesSubsegmentAlignment=false;
                segmentAlignment=0;
                subsegmentAlignment=0;
                isBitstreamSwitching=false;
                accessibility=new Vector<Descriptor>();
                role=new Vector<Descriptor>();
                rating=new Vector<Descriptor>();
                viewpoint=new Vector<Descriptor>();
                contentComponent=new Vector<ContentComponent>();
                baseURLs=new Vector<BaseUrl>();
               representation=new Vector<Representation>();
    }

    @Override
    public Vector<Descriptor> GetAccessibility() {
        return accessibility;
    }

    public void AddAccessibility(Descriptor accessibility) {
        this.accessibility.add(accessibility);
    }

    @Override
    public Vector<Descriptor> GetRole() {
        return role;
    }

    public void AddRole(Descriptor role) {
        this.role.add(role);
    }

    @Override
    public Vector<Descriptor> GetRating() {
        return rating;
    }

    public void AddRating(Descriptor rating) {
        this.rating.add(rating);
    }

    @Override
    public Vector<Descriptor> GetViewpoint() {
        return viewpoint;
    }

    public void AddViewpoint(Descriptor viewpoint) {
        this.viewpoint.add(viewpoint);
    }

    @Override
    public Vector<ContentComponent> GetContentComponent() {
        return contentComponent;
    }

    public void AddContentComponent(ContentComponent contentComponent) {
        this.contentComponent.add(contentComponent);
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
    public Vector<Representation> GetRepresentation() {
        return representation;
    }

    public void AddRepresentation(Representation representation) {
        this.representation.add(representation);
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
    public int GetId() {
        return id;
    }

    public void SetId(int id) {
        this.id = id;
    }

    @Override
    public int GetGroup() {
        return group;
    }

    public void SetGroup(int group) {
        this.group = group;
    }

    @Override
    public String GetLang() {
        return lang;
    }

    public void SetLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String GetContentType() {
        return contentType;
    }

    public void SetContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String GetPar() {
        return par;
    }

    public void SetPar(String par) {
        this.par = par;
    }

    @Override
    public int GetMinBandwidth() {
        return minBandwidth;
    }

    public void SetMinBandwidth(int minBandwidth) {
        this.minBandwidth = minBandwidth;
    }

    @Override
    public int GetMaxBandwidth() {
        return maxBandwidth;
    }

    public void SetMaxBandwidth(int maxBandwidth) {
        this.maxBandwidth = maxBandwidth;
    }

    @Override
    public int GetMinWidth() {
        return minWidth;
    }

    public void SetMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    @Override
    public int GetMaxWidth() {
        return maxWidth;
    }

    public void SetMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    @Override
    public int GetMinHeight() {
        return minHeight;
    }

    public void SetMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    @Override
    public int GetMaxHeight() {
        return maxHeight;
    }

    public void SetMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    public String GetMinFramerate() {
        return minFramerate;
    }

    public void SetMinFramerate(String minFramerate) {
        this.minFramerate = minFramerate;
    }

    @Override
    public String GetMaxFramerate() {
        return maxFramerate;
    }

    public void SetMaxFramerate(String maxFramerate) {
        this.maxFramerate = maxFramerate;
    }

    public boolean SegmentAlignmentIsbooleanValue() {
        return segmentAlignmentIsBool;
    }

    public void SetSegmentAlignmentIsBool(boolean segmentAlignmentIsBool) {
        this.segmentAlignmentIsBool = segmentAlignmentIsBool;
    }

    public boolean SubsegmentAlignmentIsbooleanValue() {
        return subsegmentAlignmentIsBool;
    }

    public void SetSubsegmentAlignmentIsBool(boolean subsegmentAlignmentIsBool) {
        this.subsegmentAlignmentIsBool = subsegmentAlignmentIsBool;
    }

    public boolean HasSegmentAlignment() {
        return usesSegmentAlignment;
    }

    public void SetUsesSegmentAlignment(boolean usesSegmentAlignment) {
        this.usesSegmentAlignment = usesSegmentAlignment;
    }

    public boolean HasSubsegmentAlignment() {
        return usesSubsegmentAlignment;
    }

    public void SetUsesSubsegmentAlignment(boolean usesSubsegmentAlignment) {
        this.usesSubsegmentAlignment = usesSubsegmentAlignment;
    }

    public int GetSegmentAligment() {
        return segmentAlignment;
    }

    public void SetSegmentAlignment(int segmentAlignment) {
        this.segmentAlignment = segmentAlignment;
    }

    @Override
    public int GetSubsegmentAlignment() {
        return subsegmentAlignment;
    }

    public void SetSubsegmentAlignment(int subsegmentAlignment) {
        this.subsegmentAlignment = subsegmentAlignment;
    }

    @Override
    public byte GetSubsegmentStartsWithSAP() {
        return subsegmentStartsWithSAP;
    }

    public void SetSubsegmentStartsWithSAP(byte subsegmentStartsWithSAP) {
        this.subsegmentStartsWithSAP = subsegmentStartsWithSAP;
    }

    public boolean GetBitstreamSwitching() {
        return isBitstreamSwitching;
    }

    public void SetIsBitstreamSwitching(boolean isBitstreamSwitching) {
        this.isBitstreamSwitching = isBitstreamSwitching;
    }

    private Vector<Descriptor>       accessibility;
    private Vector<Descriptor>       role;
    private Vector<Descriptor>       rating;
    private Vector<Descriptor>       viewpoint;
    private Vector<ContentComponent> contentComponent;
    private Vector<BaseUrl> baseURLs;
    private SegmentBase                     segmentBase;
    private SegmentList                     segmentList;
    private SegmentTemplate                 segmentTemplate;
    private Vector<Representation>   representation;
    private String                     xlinkHref;
    private String                     xlinkActuate;
    private int                        id;
    private int                        group;
    private String                     lang;
    private String                     contentType;
    private String                     par;
    private int                        minBandwidth;
    private int                        maxBandwidth;
    private int                        minWidth;
    private int                        maxWidth;
    private int                        minHeight;
    private int                        maxHeight;
    private String                     minFramerate;
    private String                     maxFramerate;
    private boolean                            segmentAlignmentIsBool;
    private boolean                            subsegmentAlignmentIsBool;
    private boolean                            usesSegmentAlignment;
    private boolean                            usesSubsegmentAlignment;
    private int                        segmentAlignment;
    private int                        subsegmentAlignment;
    private byte                         subsegmentStartsWithSAP;
    private boolean                            isBitstreamSwitching;
}
