package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.helper.myString;
import com.zhanghui.metric.HTTPTransactionType;
import com.zhanghui.mpd.AdaptationSet;
import com.zhanghui.mpd.Period;
import com.zhanghui.mpd.Representation;
import com.zhanghui.mpd.SegmentBase;
import com.zhanghui.mpd.URLType;
import com.zhanghui.simpleplayer.SegmentManager;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/21.
 */
public class SegmentBaseNode implements ElementListener{
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";
    Period period;
    AdaptationSet adaptationSet;
    SegmentBase segmentBase;
    Representation representation;
    Element segmentbase;
    public SegmentBaseNode(Period period,AdaptationSet adaptationSet,Representation representation, Element segmentbase) {
        this.period=period;
        this.adaptationSet=adaptationSet;
        this.representation=representation;
        this.segmentbase=segmentbase;
    }

    public void start(Attributes attributes) {
        segmentBase=new SegmentBase();
        if (attributes.getIndex("timescale") != -1) {
            segmentBase.SetTimescale(Integer.parseInt(attributes.getValue(attributes.getIndex("timescale"))));
        }
        if (attributes.getIndex("presentationTimeOffset") != -1) {
            segmentBase.SetPresentationTimeOffset(Integer.parseInt(attributes.getValue(attributes.getIndex("presentationTimeOffset"))));
        }
        if (attributes.getIndex("timeShiftBufferDepth") != -1) {
            segmentBase.SetTimeShiftBufferDepth(attributes.getValue(attributes.getIndex("timeShiftBufferDepth")));
        }
        if (attributes.getIndex("indexRange") != -1) {
            segmentBase.SetIndexRange(attributes.getValue(attributes.getIndex("indexRange")));
        }
        if (attributes.getIndex("indexRangeExact") != -1) {
            segmentBase.SetIndexRangeExact(myString.ToBool((attributes.getValue(attributes.getIndex("indexRangeExact")))));
        }
        if (attributes.getIndex("availabilityTimeOffset") != -1) {
            segmentBase.SetAvailabilityTimeOffset(Double.parseDouble(attributes.getValue(attributes.getIndex("availabilityTimeOffset"))));
        }
        if (attributes.getIndex("availabilityTimeComplete") != -1) {
            segmentBase.SetAvailabilityTimeComplete(myString.ToBool(attributes.getValue(attributes.getIndex("availabilityTimeComplete"))));
        }
        //Get SegmentBase.Initialization
        Element initialization=segmentbase.getChild(NAMESPACE,"Initialization");
        URLTypeNode toInitialization=new URLTypeNode(this.segmentBase,null, null,HTTPTransactionType.InitializationSegment);
        initialization.setElementListener(toInitialization);
        //Get SegmentBase.RepresentationIndex
        Element representationIndex=segmentbase.getChild(NAMESPACE,"RepresentationIndex");
        URLTypeNode toRepresentationIndex=new URLTypeNode(this.segmentBase,null,null,HTTPTransactionType.IndexSegment);
        representationIndex.setElementListener(toRepresentationIndex);
    }

    public void end() {
        if(period!=(null))
            period.SetSegmentBase(segmentBase);
        if(adaptationSet!=(null))
            adaptationSet.SetSegmentBase(segmentBase);
        if(representation!=(null))
            representation.SetSegmentBase(segmentBase);

    }
}
