package com.zhanghui.mpd;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface ISegmentBase extends IMPDElement {
    /**
     *  Returns a pointer to a dash::mpd::IURLType object that specifies the URL including a possible byte range for the Initialization Segment.
     *  @return     a pointer to dash::mpd::IURLType object
     */
    public IURLType     GetInitialization           ();

    /**
     *  Returns a pointer to a dash::mpd::IURLType object that specifies the URL including a possible byte range for the Representation Index Segment.
     *  @return     a pointer to dash::mpd::IURLType object
     */
    public IURLType     GetRepresentationIndex      ();

    /**
     *  Returns an integer representing a timescale that specifies the timescale in units per seconds
     *  to be used for the derivation of different real-time duration values in the Segment Information.\n
     *  \b NOTE:  This may be any frequency but typically is the media clock frequency of one of the media streams (or a positive integer multiple thereof).
     *  @return     an unsigned integer
     */
    public int            GetTimescale                ();

    /**
     *  Returns an integer that specifies the presentation time offset of the Representation relative to the start of the Period.\n
     *  The value of the presentation time offset in seconds is the division of the value of this attribute and the value of the \c \@timescale attribute.\n
     *  If not present on any level, the value of the presentation time offset is 0.
     *  @return     an unsigned integer
     */
    public int            GetPresentationTimeOffset   ();

    /**
     *  Returns a string that specifies the duration of the time shifting buffer for this Representation that is guaranteed to be available for a Media Presentation with type 'dynamic'. \n
     *  When not present, the value is of the \c \@timeShiftBufferDepth on MPD level applies. If present, this value shall be not smaller than the value on MPD level. \n
     *  This value of the attribute is undefined if the type attribute is equal to ‘static’.
     *  NOTE: When operating in a time-shift buffer on a Representation with value larger than the time-shift buffer than signalled on MPD level, not all Representations may be available for switching.
     *  @return     a reference to a string
     */
    public String  GetTimeShiftBufferDepth		();

    /**
     *  Returns a string that specifies the byte range that contains the Segment Index in all Media Segments of the Representation.\n
     *  The byte range shall be expressed and formatted as a \c byte-range-spec as defined in RFC 2616, Clause 14.35.1.
     *  It is restricted to a single expression identifying a contiguous range of bytes.
     *  @return     a reference to a string
     */
    public String  GetIndexRange               ();

    /**
     *  When set to \c 'true' specifies that for all Segments in the Representation, the data outside the prefix defined by \c \@indexRange contains the data
     *  needed to access all access units of all media streams syntactically and semantically.
     *  @return     a bool value
     */
    public boolean                HasIndexRangeExact          ();

    /**
     *  Returns a double value that specifies offset to define the adjusted segment availability time. The value is specified in seconds, possibly with arbitrary precision.\n
     *  The offset provides the time how much earlier these segments are available compared to their computed availability start time for all Segments of all associated Representation.\n
     *  The segment availability start time defined by this value is referred to as adjusted segment availability start time. For details on computing the adjusted segment availability start time, refer to 5.3.9.5. \n
     *  If not present, no adjusted segment availability start time is defined. \n
     *  NOTE: the value of "INF" implies availability of all segments starts at <tt><b>MPD</b>\@availabilityStartTime</tt>.
     *  @return     a double value
     */
    public double				GetAvailabilityTimeOffset	();

    /**
     *  When set to \c 'true' specifies that all Segments of all associated Representation are complete at the adjusted availability start time. The attribute shall be ignored if \c @availabilityTimeOffset is not present on any level.\n
     *  If not present on any level, the value is inferred to true.\n
     *  NOTE: If the value is set to \c 'false', then it may be inferred by the client that the segment is available at its announced location prior being complete.
     *  @return     a bool value
     */
    public boolean				HasAvailabilityTimeComplete	();
}
