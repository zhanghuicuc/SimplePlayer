package com.zhanghui.mpd;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IBaseUrl extends IMPDElement {
    /**
     *  Returns the reference to a string representing a BaseURL
     *  @return     a reference to a string
     */
    public String GetUrl               ();

    /**
     *  Returns the reference to a string that specifies a relationship between Base URLs such that \c <b>BaseURL</b> elements with the same
     *  \c \@serviceLocation value are likely to have their URLs resolve to services at a common network location, for example a common Content Delivery Network
     *  @return     a reference to a string
     */
    public String GetServiceLocation   ();

    /**
     *  Returns the reference to a string that represents a byte range. \n
     *  If present specifies HTTP partial GET requests may alternatively be issued by adding the byte range into a
     *  regular HTTP-URL based on the value of this attribute and the construction rules in Annex E.2. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.\n
     *  If not present, HTTP partial GET requests may not be converted into regular GET requests. \n
     *  \b NOTE:    Such alternative requests are expected to not be used unless the DASH application requires this. For more details refer to Annex E.
     *  @return     a reference to a string
     */
    public String GetByteRange         ();

    /**
     *  Returns a pointer to a dash::mpd::ISegment object which represents a media segment that can be downloaded. Should be used for single base urls inside
     *  a representation.
     *  @param      baseurls    a vector of pointers to dash::mpd::IBaseUrl objects that represent the path to the media segment
     *  @return     a pointer to a dash::mpd::ISegment object
     */
    public ISegment           ToMediaSegment      (Vector<BaseUrl> baseurls);

    /**
     *  Returns a double value that specifies offset to define the adjusted segment availability time. \n
     *  If the value is present in \c <b>SegmentBase</b> then this attribute should not be present. If present in \c <b>SegmentBase</b> and \c <b>BaseURL</b> the value in \c <b>BaseURL</b> shall be ignored.
     *  @return     a double value
     */
    public double				GetAvailabilityTimeOffset();

    /**
     *  When set to \c 'true' specifies that all Segments of all associated Representation are complete at the adjusted availability start time. \n
     *  If the value is present in \c <b>SegmentBase</b> then this attribute should not be present. If present in \c <b>SegmentBase</b> and \c <b>BaseURL</b> the value in \c <b>BaseURL</b> shall be ignored.
     *  @return     a bool value
     */
    public boolean				HasAvailabilityTimeComplete();
}
