package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.helper.myString;
import com.zhanghui.mpd.Representation;
import com.zhanghui.mpd.SubRepresentation;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/22.
 */
public class SubRepresentationNode implements ElementListener {
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";
    SubRepresentation subRepresentation;
    Representation representation;
    Element subrep;

    public SubRepresentationNode(Representation representation, Element subrep) {
        this.representation = representation;
        this.subrep = subrep;
    }

    public void start(Attributes attributes) {
        subRepresentation=new SubRepresentation();
        //RepresentationBase part
        if (attributes.getIndex("profiles") != -1) {
            subRepresentation.SetProfiles(attributes.getValue(attributes.getIndex("profiles")));
        }
        if (attributes.getIndex("width") != -1) {
            subRepresentation.SetWidth(Integer.parseInt(attributes.getValue(attributes.getIndex("width"))));
        }
        if (attributes.getIndex("height") != -1) {
            subRepresentation.SetHeight(Integer.parseInt(attributes.getValue(attributes.getIndex("height"))));
        }
        if (attributes.getIndex("sar") != -1) {
            subRepresentation.SetSar((attributes.getValue(attributes.getIndex("sar"))));
        }
        if (attributes.getIndex("frameRate") != -1) {
            subRepresentation.SetFrameRate(attributes.getValue(attributes.getIndex("frameRate")));
        }
        if (attributes.getIndex("audioSamplingRate") != -1) {
            subRepresentation.SetAudioSamplingRate(attributes.getValue(attributes.getIndex("audioSamplingRate")));
        }
        if (attributes.getIndex("mimeType") != -1) {
            subRepresentation.SetMimeType(attributes.getValue(attributes.getIndex("mimeType")));
        }
        if (attributes.getIndex("segmentProfiles") != -1) {
            subRepresentation.SetSegmentProfiles((attributes.getValue(attributes.getIndex("segmentProfiles"))));
        }
        if (attributes.getIndex("codecs") != -1) {
            subRepresentation.SetCodecs((attributes.getValue(attributes.getIndex("codecs"))));
        }
        if (attributes.getIndex("maximumSAPPeriod") != -1) {
            subRepresentation.SetMaximumSAPPeriod(Double.parseDouble(attributes.getValue(attributes.getIndex("maximumSAPPeriod"))));
        }
        if (attributes.getIndex("startWithSAP") != -1) {
            subRepresentation.SetStartWithSAP(Byte.parseByte(attributes.getValue(attributes.getIndex("startWithSAP"))));
        }
        if (attributes.getIndex("maxPlayoutRate") != -1) {
            subRepresentation.SetMaxPlayoutRate(Double.parseDouble(attributes.getValue(attributes.getIndex("maxPlayoutRate"))));
        }
        if (attributes.getIndex("codingDependency") != -1) {
            subRepresentation.SetCodingDependency(myString.ToBool(attributes.getValue(attributes.getIndex("codingDependency"))));
        }
        if (attributes.getIndex("scanType") != -1) {
            subRepresentation.SetScanType(attributes.getValue(attributes.getIndex("scanType")));
        }
        //Get RepresentationBase.FramePacking
        Element framePacking=subrep.getChild(NAMESPACE,"FramePacking");
        DescriptorNode toFramePacking=new DescriptorNode(null,null,null,subRepresentation,null,"FramePacking");
        framePacking.setElementListener(toFramePacking);
        //Get RepresentationBase.AudioChannelConfiguration
        Element audioChannelConfiguration=subrep.getChild(NAMESPACE,"AudioChannelConfiguration");
        DescriptorNode toAudioChannelConfiguration=new DescriptorNode(null,null,null,subRepresentation,null,"AudioChannelConfiguration");
        audioChannelConfiguration.setElementListener(toAudioChannelConfiguration);
        //Get RepresentationBase.Accessibility
        Element contentProtection=subrep.getChild(NAMESPACE,"ContentProtection");
        DescriptorNode toContentProtection=new DescriptorNode(null,null,null,subRepresentation,null,"ContentProtection");
        contentProtection.setElementListener(toContentProtection);

        //Subrepresentation Part
        if (attributes.getIndex("level") != -1) {
            subRepresentation.SetLevel(Integer.parseInt(attributes.getValue(attributes.getIndex("level"))));
        }
        if (attributes.getIndex("dependencyLevel") != -1) {
            subRepresentation.SetDependencyLevel((attributes.getValue(attributes.getIndex("dependencyLevel"))));
        }
        if (attributes.getIndex("bandwidth") != -1) {
            subRepresentation.SetBandWidth(Integer.parseInt(attributes.getValue(attributes.getIndex("bandwidth"))));
        }
        if (attributes.getIndex("contentComponent") != -1) {
            subRepresentation.SetContentComponent((attributes.getValue(attributes.getIndex("contentComponent"))));
        }

    }

    public void end() {
        representation.AddSubRepresentations(this.subRepresentation);
        subrep=subrep.getChild("SubRepresentation");
    }
}
