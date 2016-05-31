package com.zhanghui.mpd;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IMetrics {
    /**
     *  Returns a refernce to a vector of pointers to dash::mpd::IDescriptor objects that specify information about the requested reporting method and formats.\n
     *  For more details refer to section 5.9.4. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IDescriptor objects
     */
    public Vector<Descriptor> GetReportings   ();

    /**
     *  Returns a refernce to a vector of pointers to dash::mpd::IRange objects that specify the time period during which DASH Metrics collection is requested.
     *  When not present, DASH Metrics reporting is requested for the whole duration of the content.
     *  @return     a reference to a vector of pointers to dash::mpd::IRange objects
     */
    public Vector<Range>        GetRanges       ();

    /**
     *  Returns a reference to a string that specifies all DASH Metrics (as a list of DASH Metric keys as defined in Annex D of <em>ISO/IEC 23009-1, Part 1, 2012</em>, separated by a comma)
     *  that the client is desired to report.
     *  @return     a reference to a string
     */
    public String                  GetMetrics      ();
}
