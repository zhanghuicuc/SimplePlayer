package com.zhanghui.mpd;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IRange {
    /**
     *  Returns a reference to a string that specifies the start time of the DASH Metrics collection operation.
     *  When not present, DASH Metrics collection is requested from the beginning of content consumption.
     *  For services with <tt><b>MPD</b>\@type='dynamic'</tt>, the start time is indicated in wallclock time by adding the value of this
     *  attribute to the value of the <tt><b>MPD</b>\@availabilityStartTime</tt> attribute.
     *  For services with <tt><b>MPD</b>\@type='static'</tt>, the start time is indicated in Media Presentation time and is relative to the
     *  PeriodStart time of the first Period in this MPD.
     *  \b NOTE:    For example, if <tt><b>MPD</b>\@availabilityStartTime</tt> is 14:30 and the metrics collection is intended to start at 14:45, then \c \@starttime is 0:15.
     *  @return     a reference to a string
     */
    public String  GetStarttime    ();

    /**
     *  Returns a reference to a string that specifies the duration of the DASH metrics collection interval. The value of the attribute expresses in Media Presentation time.
     *  If not present, the value is identical to the Media Presentation duration.
     *  @return     a reference to string
     */
    public String  GetDuration     ();
}
