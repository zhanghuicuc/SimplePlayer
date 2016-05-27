package com.zhanghui.mpd;

import com.zhanghui.helper.myString;
import com.zhanghui.metric.HTTPTransactionType;
import java.util.Vector;
/*
 *Created by zhanghui on 2016/5/19.
 */

public class SegmentTemplate extends MultipleSegmentBase implements ISegmentTemplate{
    public SegmentTemplate() {
        media="";
        index="";
        initialization="";
        bitstreamSwitching="";
    }
    public ISegment           ToInitializationSegment        ( Vector<IBaseUrl> baseurls,  String representationID, int bandwidth)
    {
        return ToSegment(this.initialization, baseurls, representationID, bandwidth, HTTPTransactionType.InitializationSegment,0,0);
    }
    public ISegment           ToBitstreamSwitchingSegment    ( Vector<IBaseUrl> baseurls,  String representationID, int bandwidth)
    {
        return ToSegment(this.bitstreamSwitching, baseurls, representationID, bandwidth, HTTPTransactionType.BitstreamSwitchingSegment,0,0);
    }
    public ISegment           GetMediaSegmentFromNumber      ( Vector<IBaseUrl> baseurls,  String representationID, int bandwidth, int number)
    {
        return ToSegment(this.media, baseurls, representationID, bandwidth, HTTPTransactionType.MediaSegment, number,0);
    }
    public ISegment           GetIndexSegmentFromNumber      ( Vector<IBaseUrl> baseurls,  String representationID, int bandwidth, int number)
    {
        return ToSegment(this.index, baseurls, representationID, bandwidth, HTTPTransactionType.IndexSegment, number,0);
    }
    public ISegment           GetMediaSegmentFromTime        ( Vector<IBaseUrl> baseurls,  String representationID, int bandwidth, int time)
    {
        return ToSegment(this.media, baseurls, representationID, bandwidth, HTTPTransactionType.MediaSegment, 0, time);
    }
    public ISegment           GetIndexSegmentFromTime        ( Vector<IBaseUrl> baseurls,  String representationID, int bandwidth, int time)
    {
        return ToSegment(this.index, baseurls, representationID, bandwidth, HTTPTransactionType.IndexSegment, 0, time);
    }
    
    private String ReplaceParameters   ( String uri,  String representationID, int bandwidth, int number, int time) {
        Vector<String> chunks=new Vector<String>();
        String replacedUri="";
        myString.Split(uri, "$", chunks);

        if (chunks.size() > 1)
        {
            for(int i = 0; i < chunks.size(); i++)
            {
                if ( chunks.elementAt(i) == "RepresentationID") {
                    chunks.setElementAt( representationID,i);
                    continue;
                }

                if (chunks.elementAt(i).indexOf("Bandwidth") == 0)
                {
                    FormatChunk(chunks.elementAt(i), bandwidth);
                    continue;
                }

                if (chunks.elementAt(i).indexOf("Number") == 0)
                {
                    FormatChunk(chunks.elementAt(i), number);
                    continue;
                }

                if (chunks.elementAt(i).indexOf("Time") == 0)
                {
                    FormatChunk(chunks.elementAt(i), time);
                    continue;
                }
            }

            for(int i = 0; i < chunks.size(); i++)
                replacedUri += chunks.elementAt(i);

            return replacedUri;
        }
        else
        {
            replacedUri = uri;
            return replacedUri;
        }
    }

    private void        FormatChunk         (String uri, int number){
        String formattedNumber;
        int pos = 0;
        String formatTag = new String("%01d");

        if ( (pos = uri.indexOf("%0")) != -1)
            formatTag = uri.substring(pos)+"d";
        formattedNumber=String.format(formatTag,number);
        uri = formattedNumber;
    }

    private ISegment   ToSegment           ( String uri,  Vector<IBaseUrl > baseurls,  String representationID, int bandwidth,
                                     HTTPTransactionType type, int number, int time) {
        Segment seg = new Segment();

        if(seg.Init(baseurls, ReplaceParameters(uri, representationID, bandwidth, number, time), "", type))
            return seg;

        return null;
    }

    public String Getmedia() {
        return media;
    }

    public void SetMedia(String media) {
        this.media = media;
    }

    public String Getindex() {
        return index;
    }

    public void SetIndex(String index) {
        this.index = index;
    }

    @Override
    public String Getinitialization() {
        return initialization;
    }

    public void SetInitialization(String initialization) {
        this.initialization = initialization;
    }

    @Override
    public String GetbitstreamSwitching() {
        return bitstreamSwitching;
    }

    public void SetBitstreamSwitching(String bitstreamSwitching) {
        this.bitstreamSwitching = bitstreamSwitching;
    }

    private String media;
    private String index;
    private String initialization;
    private String bitstreamSwitching;
}
