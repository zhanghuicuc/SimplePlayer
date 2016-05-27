package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.mpd.AdaptationSet;
import com.zhanghui.mpd.ContentComponent;
import com.zhanghui.mpd.Descriptor;
import com.zhanghui.mpd.Metrics;
import com.zhanghui.mpd.Representation;
import com.zhanghui.mpd.SubRepresentation;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/21.
 */
public class DescriptorNode implements ElementListener{
    Descriptor descriptor;
    AdaptationSet adaptationSet;
    ContentComponent contentComponent;
    Representation representation;
    SubRepresentation subRepresentation;
    Metrics metrics;
    String descripter_name;
    public DescriptorNode(AdaptationSet adaptationSet, ContentComponent contentComponent, Representation representation, SubRepresentation subRepresentation, Metrics metrics, String descripter_name) {
        this.adaptationSet=adaptationSet;
        this.contentComponent=contentComponent;
        this.representation=representation;
        this.subRepresentation=subRepresentation;
        this.metrics=metrics;
        this.descripter_name=descripter_name;
    }

    public void start(Attributes attributes) {
        descriptor=new Descriptor();
        if (attributes.getIndex("schemeIdUri") != -1) {
            descriptor.SetSchemeIdUri(attributes.getValue(attributes.getIndex("schemeIdUri")));
        }
        if (attributes.getIndex("value") != -1) {
            descriptor.SetValue(attributes.getValue(attributes.getIndex("value")));
        }
        if (attributes.getIndex("id") != -1) {
            descriptor.SetId(attributes.getValue(attributes.getIndex("id")));
        }
    }

    public void end() {
        switch (descripter_name){
            case "Accessibility":
                if(adaptationSet!=(null))
                    adaptationSet.AddAccessibility(descriptor);
                if(contentComponent!=(null))
                    contentComponent.AddAccessibility(descriptor);
                break;
            case "Role":
                if(adaptationSet!=(null))
                    adaptationSet.AddRole(descriptor);
                if(contentComponent!=(null))
                    contentComponent.AddRole(descriptor);
                break;
            case "Rating":
                if(adaptationSet!=(null))
                    adaptationSet.AddRating(descriptor);
                if(contentComponent!=(null))
                    contentComponent.AddRating(descriptor);
                break;
            case "Viewpoint":
                if(adaptationSet!=(null))
                    adaptationSet.AddViewpoint(descriptor);
                if(contentComponent!=(null))
                    contentComponent.AddViewpoint(descriptor);
                break;
            case "FramePacking":
                if(adaptationSet!=(null))
                    adaptationSet.AddFramePacking(descriptor);
                if(representation!=(null))
                    representation.AddFramePacking(descriptor);
                if(subRepresentation!=(null))
                    subRepresentation.AddFramePacking(descriptor);
                break;
            case "AudioChannelConfiguration":
                if(adaptationSet!=(null))
                    adaptationSet.AddAudioChannelConfiguration(descriptor);
                if(representation!=(null))
                    representation.AddAudioChannelConfiguration(descriptor);
                if(subRepresentation!=(null))
                    subRepresentation.AddFramePacking(descriptor);
                break;
            case "ContentProtection":
                if(adaptationSet!=(null))
                    adaptationSet.AddContentProtection(descriptor);
                if(representation!=(null))
                    representation.AddContentProtection(descriptor);
                if(subRepresentation!=(null))
                    subRepresentation.AddFramePacking(descriptor);
                break;
            case "Reporting":
                if(metrics!=(null))
                    metrics.AddReportings(descriptor);
                break;
        }
    }
}
