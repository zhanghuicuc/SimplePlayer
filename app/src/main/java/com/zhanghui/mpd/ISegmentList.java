package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface ISegmentList extends IMultipleSegmentBase {
    /**
     *  Returns a reference to a vector of pointers to dash::mpd::ISegmentURL objects that are contained in this SegmentList.
     *  @return     a reference to a vector of pointers to dash::mpd::ISegmentURL objects
     */
    public Vector<SegmentURL> GetSegmentURLs  ();

    /**
     *  Returns a reference to a string that specifies a reference to an external <tt><b>SegmentList</b></tt> element.
     *  @return     a reference to a string
     */
    public String                  GetXlinkHref    ();

    /**
     *  Returns a reference to a string that specifies the processing set - can be either \c \"onLoad\" or \c \"onRequest\".
     *  @return     a reference to a string
     */
    public String                  GetXlinkActuate ();
}
