package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.helper.myString;
import com.zhanghui.mpd.AdaptationSet;
import com.zhanghui.mpd.Representation;
import com.zhanghui.simpleplayer.SegmentManager;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/22.
 */
public class RepresentationNode implements ElementListener{
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";
    AdaptationSet adaptationSet;
    Representation representation;
    SegmentManager segmentManager;
    String mpdURL;
    Element rep;
    Element as_element;
    public RepresentationNode(AdaptationSet adaptationSet, SegmentManager segmentManager, String mpdURL, Element rep, Element as_element) {
        this.adaptationSet = adaptationSet;
        this.segmentManager = segmentManager;
        this.mpdURL = mpdURL;
        this.rep = rep;
        this.as_element=as_element;
    }

    public void start(Attributes attributes) {
        representation=new Representation();
        //RepresentationBase part
        if (attributes.getIndex("profiles") != -1) {
            representation.SetProfiles(attributes.getValue(attributes.getIndex("profiles")));
        }
        if (attributes.getIndex("width") != -1) {
            representation.SetWidth(Integer.parseInt(attributes.getValue(attributes.getIndex("width"))));
        }
        if (attributes.getIndex("height") != -1) {
            representation.SetHeight(Integer.parseInt(attributes.getValue(attributes.getIndex("height"))));
        }
        if (attributes.getIndex("sar") != -1) {
            representation.SetSar((attributes.getValue(attributes.getIndex("sar"))));
        }
        if (attributes.getIndex("frameRate") != -1) {
            representation.SetFrameRate(attributes.getValue(attributes.getIndex("frameRate")));
        }
        if (attributes.getIndex("audioSamplingRate") != -1) {
            representation.SetAudioSamplingRate(attributes.getValue(attributes.getIndex("audioSamplingRate")));
        }
        if (attributes.getIndex("mimeType") != -1) {
            representation.SetMimeType(attributes.getValue(attributes.getIndex("mimeType")));
        }
        if (attributes.getIndex("segmentProfiles") != -1) {
            representation.SetSegmentProfiles((attributes.getValue(attributes.getIndex("segmentProfiles"))));
        }
        if (attributes.getIndex("codecs") != -1) {
            representation.SetCodecs((attributes.getValue(attributes.getIndex("codecs"))));
        }
        if (attributes.getIndex("maximumSAPPeriod") != -1) {
            representation.SetMaximumSAPPeriod(Double.parseDouble(attributes.getValue(attributes.getIndex("maximumSAPPeriod"))));
        }
        if (attributes.getIndex("startWithSAP") != -1) {
            representation.SetStartWithSAP(Byte.parseByte(attributes.getValue(attributes.getIndex("startWithSAP"))));
        }
        if (attributes.getIndex("maxPlayoutRate") != -1) {
            representation.SetMaxPlayoutRate(Double.parseDouble(attributes.getValue(attributes.getIndex("maxPlayoutRate"))));
        }
        if (attributes.getIndex("codingDependency") != -1) {
            representation.SetCodingDependency(myString.ToBool(attributes.getValue(attributes.getIndex("codingDependency"))));
        }
        if (attributes.getIndex("scanType") != -1) {
            representation.SetScanType(attributes.getValue(attributes.getIndex("scanType")));
        }
        //Get RepresentationBase.FramePacking
        final Element framePacking = rep.getChild(NAMESPACE,"FramePacking");
        DescriptorNode toFramePacking=new DescriptorNode(null,null,representation,null,null,"FramePacking");
        framePacking.setElementListener(toFramePacking);

        //Get RepresentationBase.AudioChannelConfiguration
        Element audioChannelConfiguration=rep.getChild(NAMESPACE,"AudioChannelConfiguration");
        DescriptorNode toAudioChannelConfiguration=new DescriptorNode(null,null,representation,null,null,"AudioChannelConfiguration");
        audioChannelConfiguration.setElementListener(toAudioChannelConfiguration);
        //Get RepresentationBase.Accessibility
        Element contentProtection=rep.getChild(NAMESPACE,"ContentProtection");
        DescriptorNode toContentProtection=new DescriptorNode(null,null,representation,null,null,"ContentProtection");
        contentProtection.setElementListener(toContentProtection);

        //Representation Part
        if (attributes.getIndex("id") != -1) {
            representation.SetId(attributes.getValue(attributes.getIndex("id")));
        }
        if (attributes.getIndex("bandwidth") != -1) {
            representation.SetBandwidth(Integer.parseInt(attributes.getValue(attributes.getIndex("bandwidth"))));
        }
        if (attributes.getIndex("qualityRanking") != -1) {
            representation.SetQualityRanking(Integer.parseInt(attributes.getValue(attributes.getIndex("qualityRanking"))));
        }
        if (attributes.getIndex("dependencyId") != -1) {
            representation.SetDependencyId((attributes.getValue(attributes.getIndex("dependencyId"))));
        }
        if (attributes.getIndex("mediaStreamStructureId") != -1) {
            representation.SetMediaStreamStructureId(attributes.getValue(attributes.getIndex("mediaStreamStructureId")));
        }
        //Get Representation.BaseURL
        Element baseURL=rep.getChild(NAMESPACE,"BaseURL");
        BaseUrlNode toBaseUrl=new BaseUrlNode(null,null,null,representation,segmentManager,mpdURL);
        baseURL.setStartElementListener(toBaseUrl);
        baseURL.setEndTextElementListener(toBaseUrl);
        //Get Representation.SubRepresentation
        Element subRepresentation=rep.getChild(NAMESPACE,"SubRepresentation");
        SubRepresentationNode toSubRepresentation=new SubRepresentationNode(this.representation,subRepresentation);
        subRepresentation.setElementListener(toSubRepresentation);
        //Get Representation.SegmentBase
        final Element segmentBase=rep.getChild(NAMESPACE,"SegmentBase");
        SegmentBaseNode toSegmentBase=new SegmentBaseNode(null,null,this.representation,segmentBase);
        segmentBase.setElementListener(toSegmentBase);
        //Get Representation.SegmentList
        Element segmentList=rep.getChild(NAMESPACE,"SegmentList");
        SegmentListNode toSegmentList=new SegmentListNode(null,null,this.representation,segmentList);
        segmentList.setElementListener(toSegmentList);
        //Get Representation.SegmentTemplate
        Element segmentTemplate=rep.getChild(NAMESPACE,"SegmentTemplate");
        SegmentTemplateNode toSegmentTemplate=new SegmentTemplateNode(null,null,this.representation,segmentTemplate);
        segmentTemplate.setElementListener(toSegmentTemplate);

    }

    public void end() {
        adaptationSet.AddRepresentation(representation);
        rep=as_element.getChild(NAMESPACE,"Representation");
    }
}
