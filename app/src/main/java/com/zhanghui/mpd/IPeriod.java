package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IPeriod extends IMPDElement {
    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IBaseUrl objects that specify base URLs that can be used for reference resolution and alternative URL selection.\n
     *  For more details refer to the description in section 5.6. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IBaseUrl objects
     */
    public Vector<BaseUrl> GetBaseURLs ();

    /**
     *  Returns a pointer to a dash::mpd::ISegmentBase object that specifies default Segment Base information.\n
     *  Information in this element is overridden by information in <tt><b>AdapationSet.SegmentBase</b></tt> and <tt><b>Representation.SegmentBase</b></tt>, if present.\n
     *  For more details see section 5.3.9. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a pointer to a dash::mpd::ISegmentBase object
     */
    public ISegmentBase  GetSegmentBase ();

    /**
     *  Returns a pointer to a dash::mpd::ISegmentList object that specifies default Segment List information.\n
     *  Information in this element is overridden by information in <tt><b>AdapationSet.SegmentList</b></tt> and <tt><b>Representation.SegmentList</b></tt>, if present.\n
     *  For more details see section 5.3.9. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a pointer to a dash::mpd::ISegmentList object
     */
    public ISegmentList GetSegmentList ();

    /**
     *  Returns a pointer to a dash::mpd::ISegmentTemplate object that specifies default Segment Template information.\n
     *  Information in this element is overridden by information in <tt><b>AdapationSet.SegmentTemplate</b></tt> and <tt><b>Representation.SegmentTemplate</b></tt>, if present.
     *  For more details see section 5.3.9. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a pointer to a dash::mpd::ISegmentTemplate object
     */
    public ISegmentTemplate GetSegmentTemplate();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IAdaptationSet objects that specify Adapatation Sets.\n
     *  At least one Adaptation Set shall be present in each Period. However, the actual element may be present only in a remote element if xlink is in use.\n
     *  For more details see section 5.3.3. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IAdaptationSet objects
     */
    public Vector<AdaptationSet> GetAdaptationSets();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::ISubset objects that specify Subsets.\n
     *  For more details see section 5.3.8. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::ISubset objects
     */
    public Vector<Subset> GetSubsets ();

    /**
     *  Returns a reference to a string that specifies a reference to an external <tt><b>Period</b></tt> element.
     *  @return     a reference to a string
     */
    public String  GetXlinkHref ()    ;

    /**
     *  Returns a reference to a string that specifies the processing instructions, which can be either \c \"onLoad\" or \c \"onRequest\".\n
     *  This attribute shall not be present if the \c \@xlink:href attribute is not present.
     *  @return     a reference to a string
     */
    public String  GetXlinkActuate ();

    /**
     *  Returns an reference to a string that specifies an identifier for this Period.
     *  The attribute shall be unique in the scope of the Media Presentation.
     *  @return     a reference to a string
     */
    public String  GetId   ();

    /**
     *  Returns a reference to a string that specifies the \em PeriodStart time of the Period.The \em PeriodStart time is used as an anchor to determine the MPD start
     *  time of each Media Segment as well as to determine the presentation time of each each access unit in the Media Presentation timeline.\n
     *  If not present, refer to the details in section 5.3.2.1. of <em>ISO/IEC 23009-1, Part 1, 2012</em>
     *  @return     a reference to a string
     */
    public String  GetStart ();

    /**
     *  Returns a reference to a string that  specifies the duration of the Period to determine the \em PeriodStart time of the next Period.\n
     *  If not present, refer to the details in section 5.3.2.1. of <em>ISO/IEC 23009-1, Part 1, 2012</em>
     *  @return     a reference to a string
     */
    public String GetDuration ();

    /**
     *  When set to \c 'true', this is equivalent as if the <tt><b>AdaptationSet</b>\@bitstreamSwitching</tt> for each Adaptation Set contained in this Period is set to \c 'true'.
     *  In this case, the <tt><b>AdaptationSet</b>\@bitstreamSwitching</tt> attribute shall not be set to \c 'false' for any Adaptation Set in this Period.
     *  @return     a bool value
     */
    public boolean  GetBitstreamSwitching ();

}
