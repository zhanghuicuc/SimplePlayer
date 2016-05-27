package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.helper.myString;
import com.zhanghui.metric.HTTPTransactionType;
import com.zhanghui.mpd.AdaptationSet;
import com.zhanghui.mpd.Period;
import com.zhanghui.mpd.Representation;
import com.zhanghui.mpd.SegmentList;
import com.zhanghui.mpd.SegmentTemplate;
import com.zhanghui.mpd.SegmentTimeline;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/22.
 */
public class SegmentTemplateNode implements ElementListener {
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";
    Period period;
    AdaptationSet adaptationSet;
    SegmentTemplate segmentTemplate;
    Representation representation;
    Element segtemplate;

    public SegmentTemplateNode(Period period, AdaptationSet adaptationSet, Representation representation,Element segtemplate) {
        this.period=period;
        this.segtemplate = segtemplate;
        this.representation=representation;
        this.adaptationSet = adaptationSet;
    }

    public void start(Attributes attributes) {
        //SegmentBase part
        segmentTemplate=new SegmentTemplate();
        if (attributes.getIndex("timescale") != -1) {
            segmentTemplate.SetTimescale(Integer.parseInt(attributes.getValue(attributes.getIndex("timescale"))));
        }
        if (attributes.getIndex("presentationTimeOffset") != -1) {
            segmentTemplate.SetPresentationTimeOffset(Integer.parseInt(attributes.getValue(attributes.getIndex("presentationTimeOffset"))));
        }
        if (attributes.getIndex("timeShiftBufferDepth") != -1) {
            segmentTemplate.SetTimeShiftBufferDepth(attributes.getValue(attributes.getIndex("timeShiftBufferDepth")));
        }
        if (attributes.getIndex("indexRange") != -1) {
            segmentTemplate.SetIndexRange(attributes.getValue(attributes.getIndex("indexRange")));
        }
        if (attributes.getIndex("indexRangeExact") != -1) {
            segmentTemplate.SetIndexRangeExact(myString.ToBool((attributes.getValue(attributes.getIndex("indexRangeExact")))));
        }
        if (attributes.getIndex("availabilityTimeOffset") != -1) {
            segmentTemplate.SetAvailabilityTimeOffset(Double.parseDouble(attributes.getValue(attributes.getIndex("availabilityTimeOffset"))));
        }
        if (attributes.getIndex("availabilityTimeComplete") != -1) {
            segmentTemplate.SetAvailabilityTimeComplete(myString.ToBool(attributes.getValue(attributes.getIndex("availabilityTimeComplete"))));
        }
        //Get SegmentBase.Initialization
        Element initialization=segtemplate.getChild(NAMESPACE,"Initialization");
        URLTypeNode toInitialization=new URLTypeNode(null,null,this.segmentTemplate, HTTPTransactionType.InitializationSegment);
        initialization.setElementListener(toInitialization);
        //Get SegmentBase.RepresentationIndex
        Element representationIndex=segtemplate.getChild(NAMESPACE,"RepresentationIndex");
        URLTypeNode toRepresentationIndex=new URLTypeNode(null,null,this.segmentTemplate, HTTPTransactionType.IndexSegment);
        representationIndex.setElementListener(toRepresentationIndex);

        //NultiSegmentBase Part
        if (attributes.getIndex("duration") != -1) {
            segmentTemplate.SetDuration(Integer.parseInt(attributes.getValue(attributes.getIndex("duration"))));
        }
        if (attributes.getIndex("startNumber") != -1) {
            segmentTemplate.SetStartNumber(Integer.parseInt(attributes.getValue(attributes.getIndex("startNumber"))));
        }
        //Get NultiSegmentBase.SegmentTimeline
        Element segmentTimeline=segtemplate.getChild(NAMESPACE,"SegmentTimeline");
        SegmentTimelineNode toSegmentTimeline=new SegmentTimelineNode(null,this.segmentTemplate,segmentTimeline);
        segmentTimeline.setElementListener(toSegmentTimeline);
        //Get NultiSegmentBase.BitstreamSwitching
        Element bitstreamSwitching=segtemplate.getChild(NAMESPACE,"BitstreamSwitching");
        URLTypeNode toBitstreamSwitching=new URLTypeNode(null,null,this.segmentTemplate, HTTPTransactionType.BitstreamSwitchingSegment);
        bitstreamSwitching.setElementListener(toBitstreamSwitching);

        //SegmentTemplate part
        if (attributes.getIndex("media") != -1) {
            segmentTemplate.SetMedia((attributes.getValue(attributes.getIndex("media"))));
        }
        if (attributes.getIndex("index") != -1) {
            segmentTemplate.SetIndex((attributes.getValue(attributes.getIndex("index"))));
        }
        if (attributes.getIndex("initialization") != -1) {
            segmentTemplate.SetInitialization((attributes.getValue(attributes.getIndex("initialization"))));
        }
        if (attributes.getIndex("bitstreamSwitching") != -1) {
            segmentTemplate.SetBitstreamSwitching((attributes.getValue(attributes.getIndex("bitstreamSwitching"))));
        }
    }

    public void end() {
        if(period!=(null))
            period.SetSegmentTemplate(this.segmentTemplate);
        if(adaptationSet!=(null))
            adaptationSet.SetSegmentTemplate(this.segmentTemplate);
        if(representation!=(null))
            representation.SetSegmentTemplate(this.segmentTemplate);

    }
}
