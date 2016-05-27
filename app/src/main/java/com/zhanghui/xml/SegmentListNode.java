package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.helper.myString;
import com.zhanghui.metric.HTTPTransactionType;
import com.zhanghui.mpd.AdaptationSet;
import com.zhanghui.mpd.Period;
import com.zhanghui.mpd.Representation;
import com.zhanghui.mpd.SegmentList;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/21.
 */
public class SegmentListNode implements ElementListener {
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";
    SegmentList segmentList;
    Period period;
    AdaptationSet adaptationSet;
    Representation representation;
    Element seglist;

    public SegmentListNode(Period period,AdaptationSet adaptationSet, Representation representation, Element seglist) {
        this.period=period;
        this.adaptationSet=adaptationSet;
        this.representation=representation;
        this.seglist=seglist;
    }

    public void start(Attributes attributes) {
        //SegmentBase part
        segmentList=new SegmentList();
        if (attributes.getIndex("timescale") != -1) {
            segmentList.SetTimescale(Integer.parseInt(attributes.getValue(attributes.getIndex("timescale"))));
        }
        if (attributes.getIndex("presentationTimeOffset") != -1) {
            segmentList.SetPresentationTimeOffset(Integer.parseInt(attributes.getValue(attributes.getIndex("presentationTimeOffset"))));
        }
        if (attributes.getIndex("timeShiftBufferDepth") != -1) {
            segmentList.SetTimeShiftBufferDepth(attributes.getValue(attributes.getIndex("timeShiftBufferDepth")));
        }
        if (attributes.getIndex("indexRange") != -1) {
            segmentList.SetIndexRange(attributes.getValue(attributes.getIndex("indexRange")));
        }
        if (attributes.getIndex("indexRangeExact") != -1) {
            segmentList.SetIndexRangeExact(myString.ToBool((attributes.getValue(attributes.getIndex("indexRangeExact")))));
        }
        if (attributes.getIndex("availabilityTimeOffset") != -1) {
            segmentList.SetAvailabilityTimeOffset(Double.parseDouble(attributes.getValue(attributes.getIndex("availabilityTimeOffset"))));
        }
        if (attributes.getIndex("availabilityTimeComplete") != -1) {
            segmentList.SetAvailabilityTimeComplete(myString.ToBool(attributes.getValue(attributes.getIndex("availabilityTimeComplete"))));
        }
        //Get SegmentBase.Initialization
        Element initialization=seglist.getChild(NAMESPACE,"Initialization");
        URLTypeNode toInitialization=new URLTypeNode(null,this.segmentList, null,HTTPTransactionType.InitializationSegment);
        initialization.setElementListener(toInitialization);
        //Get SegmentBase.RepresentationIndex
        Element representationIndex=seglist.getChild(NAMESPACE,"RepresentationIndex");
        URLTypeNode toRepresentationIndex=new URLTypeNode(null,this.segmentList,null, HTTPTransactionType.IndexSegment);
        representationIndex.setElementListener(toRepresentationIndex);

        //NultiSegmentBase Part
        if (attributes.getIndex("duration") != -1) {
            segmentList.SetDuration(Integer.parseInt(attributes.getValue(attributes.getIndex("duration"))));
        }
        if (attributes.getIndex("startNumber") != -1) {
            segmentList.SetStartNumber(Integer.parseInt(attributes.getValue(attributes.getIndex("startNumber"))));
        }
        //Get NultiSegmentBase.SegmentTimeline
        Element segmentTimeline=seglist.getChild(NAMESPACE,"SegmentTimeline");
        SegmentTimelineNode toSegmentTimeline=new SegmentTimelineNode(this.segmentList,null,segmentTimeline);
        segmentTimeline.setElementListener(toSegmentTimeline);
        //Get NultiSegmentBase.BitstreamSwitching
        Element bitstreamSwitching=seglist.getChild(NAMESPACE,"BitstreamSwitching");
        URLTypeNode toBitstreamSwitching=new URLTypeNode(null,this.segmentList,null, HTTPTransactionType.BitstreamSwitchingSegment);
        bitstreamSwitching.setElementListener(toBitstreamSwitching);

        //SegmentList part
        if (attributes.getIndex("xlink:href") != -1) {
            segmentList.SetXlinkHref((attributes.getValue(attributes.getIndex("xlink:href"))));
        }
        if (attributes.getIndex("xlink:actuate") != -1) {
            segmentList.SetXlinkActuate((attributes.getValue(attributes.getIndex("xlink:actuate"))));
        }
        //Get SegmentList.SegmentURL
        Element segmentURL=seglist.getChild(NAMESPACE,"SegmentURL");
        SegmentURLNode toSegmentURL=new SegmentURLNode(this.segmentList);
        segmentURL.setElementListener(toSegmentURL);
    }

    public void end() {
        if(period!=(null))
            period.SetSegmentList(segmentList);
        if(adaptationSet!=(null))
            adaptationSet.SetSegmentList(segmentList);
        if(representation!=(null))
            representation.SetSegmentList(segmentList);

    }
}
