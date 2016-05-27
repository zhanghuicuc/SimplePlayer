package com.zhanghui.mpd;
 import java.util.Vector;
/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IAdaptationSet extends IRepresentationBase {
    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IDescriptor objects that specify information about accessibility scheme.\n
     *  For more details refer to sections 5.8.1 and 5.8.4.3. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IDescriptor objects
     */
    public   Vector<Descriptor >       GetAccessibility                ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IDescriptor objects that specify information on role annotation scheme.
     *  For more details refer to sections 5.8.1 and 5.8.4.2. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IDescriptor objects
     */
    public   Vector<Descriptor >       GetRole                         ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IDescriptor objects that specify information on rating scheme.\n
     *  For more details refer to sections 5.8.1 and 5.8.4.4. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IDescriptor objects
     */
    public   Vector<Descriptor >       GetRating                       ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IDescriptor objects that specify information on viewpoint annotation scheme.\n
     *  For more details refer to sections 5.8.1 and 5.8.4.5. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IDescriptor objects
     */
    public   Vector<Descriptor >       GetViewpoint                    ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IContentComponent objects that specifies the properties
     *  of one media content component contained in this Adaptation Set.\n
     *  For more details refer to section 5.3.4. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IContentComponent objects
     */
    public   Vector<ContentComponent > GetContentComponent             ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IBaseUrl objects that specify base URLs that can be used for reference resolution and alternative URL selection.\n
     *  For more details refer to the description in section 5.6. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IBaseUrl objects
     */
    public   Vector<BaseUrl >          GetBaseURLs                     ();

    /**
     *  Returns a pointer to a dash::mpd::ISegmentBase object that specifies default Segment Base information.\n
     *  Information in this element is overridden by information in the <tt><b>Representation.SegmentBase</b></tt>, if present.\n
     *  For more details see section 5.3.9. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a pointer to a dash::mpd::ISegmentBase object
     */
    public ISegmentBase                           GetSegmentBase                  ();

    /**
     *  Returns a pointer to a dash::mpd::ISegmentList object that specifies default Segment List information.\n
     *  Information in this element is overridden by information in the <tt><b>Representation.SegmentList</b></tt>, if present.\n
     *  For more details see section 5.3.9. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a pointer to a dash::mpd::ISegmentList object
     */
    public ISegmentList                          GetSegmentList                  ();

    /**
     *  Returns a pointer to a dash::mpd::ISegmentTemplate object that specifies default Segment Template information.\n
     *  Information in this element is overridden by information in the <tt><b>Representation.SegmentTemplate</b></tt>, if present.\n
     *  For more details see section 5.3.9. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a pointer to a dash::mpd::ISegmentTemplate object
     */
    public ISegmentTemplate                       GetSegmentTemplate              ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IRepresentation objects that specifies a Representation.\n
     *  At least one Representation element shall be present in each Adaptation Set. The actual element may however be part of a remote element.\n
     *  For more details refer to section 5.3.5. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IRepresentation objects
     */
    public   Vector<Representation >   GetRepresentation               ();

    /**
     *  Returns a reference to a string that specifies a reference to external <tt><b>AdaptationSet</b></tt> element.
     *  @return     a reference to a string
     */
    public   String                      GetXlinkHref                    ();

    /**
     *  Returns a reference to a string that specifies the processing instructions, which can be either \c \"onLoad\" or \c \"onRequest\".
     *  @return     a reference to a string
     */
    public   String                      GetXlinkActuate                 ();

    /**
     *  Returns an unsigned integer that specifies an unique identifier for this Adaptation Set in the scope of the Period.
     *  The attribute shall be unique in the scope of the containing Period. \n\n
     *  The attribute shall not be present in a remote element.\n\n
     *  If not present, no identifier for the Adaptation Set is specified.
     *  @return     an unsigned integer
     */
    public int                                GetId                           ();

    /**
     *  Returns an unsigned integer that specifies an identifier for the group that is unique in the scope of the containing Period.\n
     *  For details refer to section 5.3.3.1. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     an unsigned integer
     */
    public int                                GetGroup                        ();

    /**
     *  Returns a reference to a string that declares the language code for this Adaptation Set. The syntax and semantics according to IETF RFC 5646 shall be used.\n
     *  If not present, the language code may be defined for each media component or it may be unknown.
     *  @return     a reference to a string
     */
    public   String                      GetLang                         ();

    /**
     *  Returns a reference to a string that specifies the media content component type for this Adaptation Set.
     *  A value of the top-level Content-type 'type' value as defined in RFC1521, Clause 4 shall be taken.\n
     *  If not present, the media content component type may be defined for each media component or it may be unknown.
     *  @return     a reference to a string
     */
    public   String                      GetContentType                  ();

    /**
     *  Returns a reference to a string that specifies the picture aspect ratio of the video media component type,
     *  in the form of a string consisting of two integers separated by ':', e.g., \c \"16:9\". When this attribute is present,
     *  and the attributes \c \@width and \c \@height for the set of Representations are also present, the picture aspect ratio as specified by this attribute shall be the same
     *  as indicated by the values of \c \@width, \c \@height, and \c \@sar, i.e. it shall express the same ratio as (\c \@width * \em sarx): (\c \@height * \em sary),
     *  with \em sarx the first number in \c \@sar and \em sary the second number.\n
     *  If not present, the picture aspect ratio may be defined for each media component or it may be unknown.
     *  @return     a reference to a string
     */
    public   String                      GetPar                          ();

    /**
     *  Returns an unsigned integer that specifies the minimum \c \@bandwidth value in all Representations in this Adaptation Set.
     *  This value has the same units as the \c \@bandwidth attribute.
     *  @return     an unsigned integer
     */
    public int                                GetMinBandwidth                 ();

    /**
     *  Returns an unsigned integer that specifies the maximum \c \@bandwidth value in all Representations in this Adaptation Set.
     *  This value has the same units as the \c \@bandwidth attribute.
     *  @return     an unsigned integer
     */
    public int                                GetMaxBandwidth                 ();

    /**
     *  Returns an unsigned integer that specifies the minimum \c \@width value in all Representations in this Adaptation Set.
     *  This value has the same units as the \c \@width attribute.
     *  @return     an unsigned integer
     */
    public int                                GetMinWidth                     ();

    /**
     *  Returns an unsigned integer that specifies the maximum \c \@width value in all Representations in this Adaptation Set.
     *  This value has the same units as the \c \@width attribute.
     *  @return     an unsigned integer
     */
    public int                                GetMaxWidth                     ();

    /**
     *  Returns an unsigned integer that specifies specifies the minimum \c \@height value in all Representations in this Adaptation Set.
     *  This value has the same units as the \c \@height attribute.
     *  @return     an unsigned integer
     */
    public int                                GetMinHeight                    ();

    /**
     *  Returns an unsigned integer that specifies specifies the maximum \c \@height value in all Representations in this Adaptation Set.
     *  This value has the same units as the \c \@height attribute.
     *  @return     an unsigned integer
     */
    public int                                GetMaxHeight                    ();

    /**
     *  Returns a reference to a string that specifies the minimum \c \@framerate value in all Representations in this Adaptation Set.
     *  This value is encoded in the same format as the \c \@frameRate attribute.
     *  @return     a reference to a string
     */
    public   String                      GetMinFramerate                 ();

    /**
     *  Returns a reference to a string that specifies the maximum \c \@framerate value in all Representations in this Adaptation Set.
     *  This value is encoded in the same format as the \c \@frameRate attribute.
     *  @return     a reference to a string
     */
    public   String                      GetMaxFramerate                 ();

    /**
     *  Because of the fact that the type of the attribute \em segmentAlignment is a union of \c xs:unsignedInt and \c xs:booleanean this method is needed to determine
     *  whether its value is of type boolean or integer.\n
     *  If and only if \c 'true' is returned, an invocation of HasSegmentAlignment() is neccessary to retrieve the boolean value.\n
     *  If and only if \c 'false' is returned, an invocation of GetSegmentAlignment() is neccessary to retrieve the integer value.
     *  @return     a boolean value
     */
    public boolean                                    SegmentAlignmentIsbooleanValue     ();

    /**
     *  If the return value of SegmentAlignmentIsbooleanValue() equals \c 'true' the boolean value returned by this method
     *  specifies whether Segment Alignment is used or not. This is only valid for Adaptation Sets containing Representations with multiple media content components.
     *  If \c 'true' is returned, this specifies that for any two Representations,
     *  X and Y, within the same Adaptation Set, the <em>m</em>-th Segment of X and the <em>n</em>-th Segment of Y
     *  are non-overlapping (as defined in section 4.5.2 of <em>ISO/IEC 23009-1, Part 1, 2012</em>) whenever \em m is not equal to \em n.
     *  @return     a boolean value
     */
    public boolean                                    HasSegmentAlignment             ();

    /**
     *  If the return value of SegmentAlignmentIsbooleanValue() equals \c 'false' this specifies that for any two Representations,
     *  X and Y, within the same Adaptation Set, the <em>m</em>-th Segment of X and the <em>n</em>-th Segment of Y
     *  are non-overlapping (as defined in section 4.5.2 of <em>ISO/IEC 23009-1, Part 1, 2012</em>) whenever \em m is not equal to \em n.\n\n
     *  For Adaptation Sets containing Representations with a single media content component, when two <b><tt>AdaptationSet</tt></b> elements within a Period share the same
     *  integer value for this attribute - <b>which is the return value of this method</b>, then for any two Representations, X and Y, within the union of the two Adaptation Sets,
     *  the <em>m</em>-th Segment of X and the <em>n</em>-th Segment of Y are non-overlapping (as defined in section 4.5.2 of <em>ISO/IEC 23009-1, Part 1, 2012</em>)
     *  whenever \em m is not equal to \em n.
     *  @return     an unsigned integer
     */
    public int                                GetSegmentAligment              ();

    /**
     *  Because of the fact that the type of the attribute \em subsegmentAlignment is a union of \c xs:unsignedInt and \c xs:booleanean this method is needed to determine
     *  whether its value is of type boolean or integer.\n
     *  If and only if \c 'true' is returned, an invocation of HasSubsegmentAlignment() is neccessary to retrieve the boolean value.\n
     *  If and only if \c 'false' is returned, an invocation of GetSubsegmentAlignment() is neccessary to retrieve the integer value.
     *  @return     a boolean value
     */
    public boolean                                    SubsegmentAlignmentIsbooleanValue  ();

    /**
     *  If and only if the return value of SubsegmentAlignmentIsbooleanValue() equals \c 'true' the boolean value returned by this method
     *  specifies whether Subsegment Alignment is used or not.
     *  If \c 'true' is returned, the following conditions shall be satisfied:
     *  <ul>
     *      <li>Each Media Segment shall be indexed (i.e. either it contains a Segment index or there is an Index Segment providing an index for the Media Segment)
     *      <li>For any two Representations, X and Y, within the same Adaptation Set, the <em>m</em>-th Subsegment of X and the <em>n</em>-th Subsegment of Y are
     *          non-overlapping (as defined in section 4.5.2 of <em>ISO/IEC 23009-1, Part 1, 2012</em>) whenever \em m is not equal to \em n.
     *  </ul>
     *  @return     a boolean value
     */
    public boolean                                    HasSubsegmentAlignment          ();

    /**
     *  If the return value of SubsegmentAlignmentIsbooleanValue() equals \c 'false' the following conditions shall be satisfied:
     *  <ul>
     *      <li>Each Media Segment shall be indexed (i.e. either it contains a Segment index or there is an Index Segment providing an index for the Media Segment)
     *      <li>For any two Representations, X and Y, within the same Adaptation Set, the <em>m</em>-th Subsegment of X and the <em>n</em>-th Subsegment of Y are
     *          non-overlapping (as defined in section 4.5.2 of <em>ISO/IEC 23009-1, Part 1, 2012</em>) whenever \em m is not equal to \em n.
     *      <li>For Adaptation Sets containing Representations with a single media content component, when two <tt><b>AdaptationSet</b></tt> elements within a Period share
     *          the same integer value for this attribute - <b>which is the return value of this method</b>, then for any two Representations, X and Y,
     *          within the union of the two Adaptation Sets, the <em>m</em>-th Subsegment of X and the <em>n</em>-th Subsegment of Y are non-overlapping
     *          (as defined in section 4.5.2 of <em>ISO/IEC 23009-1, Part 1, 2012</em>) whenever m is not equal to n.
     *  </ul>
     *  @return     an unsigned integer
     */
    public int                                GetSubsegmentAlignment          ();

    /**
     *  Returns a unsigned integer that when greater than 0, specifies that each Subsegment with \c SAP_type greater than 0 starts with a SAP of type
     *  less than or equal to the value of \c \@subsegmentStartsWithSAP. A Subsegment starts with SAP when the Subsegment contains a SAP,
     *  and for the first SAP, I<sub>SAU</sub> is the index of the first access unit that follows I<sub>SAP</sub> , and I<sub>SAP</sub> is contained in the Subsegment.\n
     *  The semantics of \c \@subsegmentStartsWithSAP equal to 0 are unspecified.
     *  @return     an unsigned integer
     */
    public byte                                 GetSubsegmentStartsWithSAP      ();

    /**
     *  Returns a boolean value that when true, the following applies:
     *  <ul>
     *      <li>All Representations in the Adaptation Set shall have the same number \em M of Media Segments;
     *      <li>Let \em R<sub>1</sub> , \em R<sub>2</sub> , ..., \em R<sub>N</sub>  be all the Representations within the Adaptation Set.
     *      <li>Let
     *          <ul>
     *              <li><em>S<sub>i,j</sub></em> , for \em j > 0, be the \em j<sup>th</sup> Media Segment in the \em i<sup>th</sup> Representation (i.e., \em R<sub>i</sub> )
     *              <li>if present, let <em>S<sub>i,0</sub></em> be the Initialization Segment in the \em i<sup>th</sup> Representation, and
     *              <li>if present, let \em B<sub>i</sub>  be the Bitstream Switching Segment in the \em i<sup>th</sup> Representation.
     *          </ul>
     *      <li>The sequence of
     *          <ul>
     *              <li>any Initialization Segment, if present, in the Adaptation Set, with,
     *              <li> if Bitstream Switching Segments are present, \n
     *                  <em> B<sub>i(1)</sub>, S<sub>i(1),1</sub>, B<sub>i(2)</sub>,   S<sub>i(2),2</sub>, ...,
     *                  B<sub>i(k)</sub>, S<sub>i(k),k</sub>, ..., B<sub>i(M)</sub>, S<sub>i(M),M</sub> </em>
     *              <li>else \n
     *                  S<sub>i(1),1</sub>, S<sub>i(2),2</sub>, ..., S<sub>i(k),k</sub>, ..., S<sub>i(M),M</sub>,
     *          </ul>
     *          wherein any \em i(k) for all \em k values in the range of 1 to \em M, respectively, is an integer value in the range of 1 to \em N,
     *          results  in  a  \"conforming  Segment  sequence\"  as defined in section 4.5.3 of <em>ISO/IEC 23009-1, Part 1, 2012</em>
     *          with the media format as specified in the \c \@mimeType attribute.
     *  </ul>
     *  More detailed rules may be defined for specific media formats.
     *  @return     a boolean value
     */
    public boolean                                    GetBitstreamSwitching           ();
}
