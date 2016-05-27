package com.zhanghui.mpd;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IMultipleSegmentBase extends ISegmentBase {
    /**
     *  Return a pointer to a dash::mpd::ISegmentTimeline object
     *  @return     a pointer to a dash::mpd::ISegmentTimeline object
     */
    public ISegmentTimeline     GetSegmentTimeline      ();

    /**
     *  Returns a pointer to a dash::mpd::IURLType object that specifies the URL including a possible byte range for the Bitstream Switching Segment.
     *  @return     a pointer to a dash::mpd::IURLType object
     */
    public IURLType             GetBitstreamSwitching   ();

    /**
     *  Returns a integer specifying the constant approximate Segment duration. \n
     *  All Segments within this Representation element have the same duration unless it is the last Segment within the Period, which could be significantly shorter.\n
     *  The value of the duration in seconds is the division of the value of this attribute and the value of the \c \@timescale attribute associated to the containing Representation.\n
     *  For more details refer to section 5.3.9.5.3. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     an unsigned integer
     */
    public int                    GetDuration             ();

    /**
     *  Returns a integer specifying the number of the first Media Segment in this Representation in the Period.\n
     *  For more details refer to 5.3.9.5.3. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     an unsigned integer
     */
    public int                    GetStartNumber          ();
}
