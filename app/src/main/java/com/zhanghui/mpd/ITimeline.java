package com.zhanghui.mpd;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface ITimeline extends IMPDElement {
    /**
     *  Returns an integer that specifies the MPD start time, in \c \@timescale units, the first Segment in the series starts relative to the beginning of the Period.\n
     *  The value of this attribute must be equal to or greater than the sum of the previous <tt><b>S</b></tt> element earliest presentation time and
     *  the sum of the contiguous Segment durations. \n
     *  If the value of the attribute is greater than what is expressed by the previous <tt><b>S</b></tt> element, it expresses discontinuities in the timeline.\n
     *  If not present then the value shall be assumed to be zero for the first S element and for the subsequent <tt><b>S</b></tt> elements,
     *  the value shall be assumed to be the sum of the previous <tt><b>S</b></tt> element's earliest presentation time and contiguous duration
     *  (i.e. previous <tt><b>S</b>\@t</tt> + \c \@d * (\c \@r + 1)).\n\n
     *  \em StartTime corresponds to the \c \@t attribute.
     *  @return     an unsigned integer
     */
    public int    GetStartTime    ();

    /**
     *  Returns the integer that specifies the Segment duration, in units of the value of the \c \@timescale. \n\n
     *  \em Duration corresponds to the \c \@d attribute.
     *  @return     an unsigned integer
     */
    public int    GetDuration     ();

    /**
     *  Returns an integer that specifies the repeat count of the number of following contiguous Segments with the same duration expressed by the value of \c \@d.
     *  This value is zero-based (e.g. a value of three means four Segments in the contiguous series). \n\n
     *  \em RepeatCount corresponds to the \c \@r attribute.
     *  @return     an unsigned integer
     */
    public int    GetRepeatCount  ();
}
