package com.zhanghui.mpd;

import com.zhanghui.metric.IDASHMetrics;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public abstract class IMPD implements IMPDElement, IDASHMetrics,Serializable {
    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IProgramInformation objects that specify descriptive information about the program.\n
     *  For more details refer to the description in section 5.7. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IProgramInformation objects
     */
    public abstract Vector<IProgramInformation>   GetProgramInformations          ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IBaseUrl objects that specify Base URLs that can be used for reference resolution
     *  and alternative URL selection. \n
     *  For more details refer to the description in section 5.6. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IBaseUrl objects
     */
    public abstract Vector<IBaseUrl>              GetBaseUrls                     ();

    /**
     *  Returns a reference to a vector of strings that specify locations at which the MPD is available.
     *  @return     a reference to a vector of strings
     */
    public abstract Vector<String>             GetLocations                    ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IPeriod objects that specify the information of a Period.\n
     *  For more details refer to the description in section 5.3.2. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IPeriod objects
     */
    public abstract Vector<IPeriod>               GetPeriods                      ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IMetrics objects that specify the DASH Metrics.\n
     *  For more details see section 5.9. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IPeriod objects
     */
    public abstract Vector<IMetrics>              GetMetrics                      ();

    /**
     *  Returns a reference to a string that specifies an identifier for the Media Presentation. It is recommended to use an identifier that is unique within
     *  the scope in which the Media Presentation is published. \n
     *  If not specified, no MPD-internal identifier is provided. However, for example the URL to the MPD may be used as an identifier for the Media Presentation.
     *  @return     a reference to a string
     */
    public abstract String                          GetId                           ();

    /**
     *  Returns a reference to a vector of strings that specifies a list of Media Presentation profiles as described in section 8 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.\n
     *  The contents of this attribute shall conform to either the \c pro-simple or \c pro-fancy productions of RFC6381, Section 4.5, without the enclosing \c DQUOTE characters,
     *  i.e. including only the \c unencodedv or \c encodedv elements respectively.
     *  As profile identifier the URI defined for the conforming Media Presentation profiles as described in section 8 shall be used.
     *  @return     a reference to a vector of pointers to dash::mpd::IProgramInformation objects
     */
    public abstract Vector<String>             GetProfiles                     ();

    /**
     *  Returns a reference to a string that specifies whether the Media Presentation Description may be updated (<tt>\@type=\"dynamic\"</tt>) or not (<tt>\@type=\"static\"</tt>).\n
     * \b NOTE:     Static MPDs are typically used for On-Demand services, whereas dynamic MPDs are used for live services.
     *  @return     a reference to a string
     */
    public abstract String                          GetType                         ();

    /**
     *  Returns a reference to a string that specifies
     *  <ul>
     *      <li>the anchor for the computation of the earliest availability time (in UTC) for any Segment in the Media Presentation if <tt>\@type=\"dynamic\"</tt>.
     *      <li>the Segment availability start time for all Segments referred to in this MPD if <tt>\@type=\"static\"</tt>.
     *  </ul>
     *  If not present, all Segments described in the MPD shall become available at the time the MPD becomes available.\n
     *  For <tt>\@type=\"dynamic\"</tt> this attribute shall be present.
     *  @return     a reference to a string
     */
    public abstract String                          GetAvailabilityStarttime        ();

    /**
     *  Returns a reference to a string that specifies the wall-clock time when the MPD was generated and published at the origin server.
     *  MPDs with a later value of <tt><b>MPD</b>\@publishTime</tt> shall be an update as defined in section 5.4. of <em>ISO/IEC 23009-1, Part 1, 2014</em> to MPDs with earlier <tt><b>MPD</b>\@publishTime</tt>.
     *  @return     a reference to a string
     */
    public abstract String							GetPublishTime					();

    /**
     *  Returns a reference to a string that specifies the latest Segment availability end time for any Segment in the Media Presentation. When not present, the value is unknown.
     *  @return     a reference to a string
     */
    public abstract String                         GetAvailabilityEndtime          ();

    /**
     *  Returns a reference to a string that specifies the duration of the entire Media Presentation. If the attribute is not present, the duration of the Media Presentation is unknown.
     *  In this case the attribute <tt><b>MPD</b>\@minimumUpdatePeriod</tt> shall be present.\n
     *  This attribute shall be present when the attribute <tt><b>MPD</b>\@minimumUpdatePeriod</tt> is not present.
     *  @return     a reference to a string
     */
    public abstract String                          GetMediaPresentationDuration    ();

    /**
     *  Returns a reference to a string that specifies the smallest period between potential changes to the MPD.
     *  This can be useful to control the frequency at which a client checks for updates. \n
     *  If this attribute is not present it indicates that the MPD does not change.
     *  If <tt><b>MPD</b>\@type</tt> is \c \"static\", \c \@minimumUpdatePeriod shall not be present.\n
     *  Details on the use of the value of this attribute are specified in section 5.4. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a string
     */
    public abstract String                          GetMinimumUpdatePeriod          ();

    /**
     *  Returns a reference to a string that specifies a common duration used in the definition of the Representation data rate
     *  (see \c \@bandwidth attribute in section 5.3.5.2 of <em>ISO/IEC 23009-1, Part 1, 2012</em>).
     *  @return     a reference to a string
     */
    public abstract String                          GetMinBufferTime                ();


    /**
     *  Returns a reference to a string that specifies the duration of the time shifting buffer that is guaranteed to be available for a Media Presentation
     *  with type \c \"dynamic\". When not present, the value is infinite. This value of the attribute is undefined if the type attribute is equal to \c \"static\".
     *  @return     a reference to a string
     */
    public abstract String                          GetTimeShiftBufferDepth         ();

    /**
     *  Returns a reference to a string that specifies
     *  <ul>
     *      <li>when \c \@type is \c \"dynamic\", a fixed delay offset in time from the presentation time of each access unit that is suggested to be used for presentation of each access unit.\n
     For more details refer to 7.2.1. \n
     When not specified, then no value is provided and the client is expected to choose a suitable value.
     <li>when \c \@type is \c \"static\" the value of the attribute is undefined and may be ignored.
     </ul>
     *  @return     a reference to a string
     */
    public abstract String                          GetSuggestedPresentationDelay   ();

    /**
     *  Returns a reference to a string that specifies the maximum duration of any Segment in any Representation in the Media Presentation,
     *  i.e. documented in this MPD and any future update of the MPD. If not present, then the maximum Segment duration shall be the maximum duration of any Segment documented in this MPD.
     *  @return     a reference to a string
     */
    public abstract String                          GetMaxSegmentDuration           ();

    /**
     *  Returns a reference to a string that specifies the maximum duration of any Media Subsegment in any Representation in the Media Presentation.
     *  If not present, the same value as for the maximum Segment duration is implied.
     *  @return     a reference to a string
     */
    public abstract String                          GetMaxSubsegmentDuration        ();

    /**
     *  Returns a pointer to a dash::mpd::IBaseUrl that specifies the absolute path to the MPD file. \n
     *  This absolute path is needed if there is no BaseURL specified and all other BaseURLs are relative.
     *  @return     a pointer to a dash::mpd::IBaseUrl
     */
    public abstract IBaseUrl                                   GetMPDPathBaseUrl               ();

    /**
     *  Returns the UTC time in seconds when the MPD was fetched.\n
     *  It is up to the client to check that this value has been set accordingly. \n
     *  See SetFetchTime() for further details.
     *  @return     an unsigned integer
     */
    public abstract int                                    GetFetchTime                    ();
}
