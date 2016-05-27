package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface ISegmentTimeline extends IMPDElement{
    /**
     *  Returns a reference to a vector of pointers to dash::mpd::ITimeline objects, that correspond to the to <b><tt>S</tt></b> elements.
     *  @return     a reference to vector of pointers to dash::mpd::ITimeline objects
     */
    public Vector<ITimeline> GetTimelines ();
}
