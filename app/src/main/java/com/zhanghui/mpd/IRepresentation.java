package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IRepresentation extends IRepresentationBase {
    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IBaseUrl objects that specifies Base URLs that can be used for reference resolution and alternative URL selection.\n
     *   For more details refer to the description in section 5.6. of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IBaseUrl objects
     */
    public Vector<BaseUrl> GetBaseURLs                 ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::ISubRepresentation objects that specifies information about Sub-Representations 
     *  that are embedded in the containing Representation.
     *  For more detail see section 5.3.6 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::ISubRepresentation objects
     */
    public Vector<SubRepresentation>    GetSubRepresentations       ();

    /**
     *  Returns a pointer to a dash::mpd::ISegmentBase object that specifies default Segment Base information.
     *  For more detail see section 5.3.9 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a pointer to a dash::mpd::ISegmentBase object
     */
    public ISegmentBase                               GetSegmentBase              ();

    /**
     *  Returns a pointer to a dash::mpd::ISegmentList object that specifies the Segment List information.
     *  For more detail see section 5.3.9 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a pointer to a dash::mpd::ISegmentList object
     */
    public ISegmentList                               GetSegmentList              ();

    /**
     *  Returns a pointer to a dash::mpd::ISegmentTemplate object that specifies the Segment Template information.
     *  For more detail see section 5.3.9 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a pointer to a dash::mpd::ISegmentTemplate object
     */
    public ISegmentTemplate                           GetSegmentTemplate          ();

    /**
     *  Returns a reference to a string that specifies an identifier for this Representation. The identifier shall be unique within a Period 
     *  unless the Representation is functionally identically to another Representation in the same Period. \n
     *  The identifier shall not contain whitespace characters. \n
     *  If used in the template-based URL construction as defined in 5.3.9.4.4 of <em>ISO/IEC 23009-1, Part 1, 2012</em>, 
     *  the string shall only contain characters that are permitted within an HTTP-URL according to RFC 1738.
     *  @return     a reference to a string
     */
    public String                          GetId                       ();

    /**
     *  Returns an integer that specifies a bandwidth in bits per seconds (bps).\n
     *  If the Representation is continuously delivered at this bitrate, starting at any SAP that is indicated either by \c \@startWithSAP 
     *  or by any Segment Index box, a client can be assured of having enough data for continuous playout providing playout begins after 
     *  \c \@minBufferTime * \c \@bandwidth bits have been received (i.e. at time \c \@minBufferTime after the first bit is received).\n\n
     *  For dependent Representations this value shall specify the minimum bandwidth as defined above of this Representation and all complementary Representations.
     *  @return     an unsigned integer
     */
    public int                                    GetBandwidth                ();

    /**
     *  Returns an integer that specifies a quality ranking of the Representation relative to other Representations in the same Adaptation Set. 
     *  Lower values represent higher quality content.
     *  @return     an unsigned integer
     */
    public int                                    GetQualityRanking           ();

    /**
     *  Returns a reference to a vector of strings that specifies all complementary Representations the Representation depends on in the decoding and/or 
     *  presentation process as values of \c \@id attributes.\n
     *  If not present, the Representation can be decoded and presented independently of any other Representation. \n
     *  This attribute shall not be present where there are no dependencies.
     *  @return     a reference to a vector of strings
     */
    public Vector<String>             GetDependencyId             ();

    /**
     *  Returns a reference to a vector of strings that specifies media stream structure identifier values.\n
     *  The attribute may be present for Representations containing video and its semantics are unspecified for any other type of Representations.\n
     *  If present, the attribute \c \@mediaStreamStructureId specifies a whitespace-separated list of media stream structure identifier values.
     *  If media streams share the same media stream structure identifier value, the media streams shall have the following characteristics:
     *  <ul>
     *  <li>    The media streams have the same number of Stream Access Points of type 1 to 3.
     *  <li>    The values of T<sub>SAP</sub>, T<sub>DEC</sub>, T<sub>EPT</sub>, and T<sub>PTF</sub> of the <em>i</em>-th SAP of type 1 to 3 in one media stream are identical 
     *          to the values of T<sub>SAP</sub>, T<sub>DEC</sub>, T<sub>EPT</sub>, and T<sub>PTF</sub>, respectively, of the <em>i</em>-th SAP of type 1 to 3 
     *          in the other media streams for any value of \em i from 1 to the number of SAPs of type 1 to 3 in any of the media streams.
     *  <li>    A media stream formed by concatenating the media stream of a first Representation until I<sub>SAU</sub> (exclusive) of the <em>i</em>-th SAP of type 1 to 3 and the 
     *          media stream of a second Representation (having the same media stream structure identifier value as for the first Representation) starting from the I<sub>SAU</sub> 
     *          (inclusive) of the <em>i</em>-th SAP of type 1 to 3 conforms to the specification in which the media stream format is specified for any value of \em i from 1 to the number 
     *          of SAPs of type 1 to 3 in either media stream. Furthermore, the decoded pictures have an acceptable quality regardless of type of the Stream Access Point access unit used.
     *  </ul>
     *  All media stream structure identifier values for one Adaptation Set shall differ from those of another Adaptation Set.\n
     *  If not present, then for this Representation no similarities to other Representations are known.
     *  \b NOTE     Indicating multiple media stream structure identifier values for a Representation can be useful in cases where switching between Representations A and B
     *              as well as between Representations B and C is allowed at non-IDR intra pictures, but switching between Representations A and C would cause too 
     *              severe a degradation in the quality of the leading pictures and is hence not allowed. To indicate these permissions and restrictions, 
     *              Representation A would contain \c \@mediaStreamStructureId equal to \"1"\", Representation B would contain \c \@mediaStreamStructureId equal to \"1 2\",
     *              and Representation C would contain \c \@mediaStreamStructureId equal to \"2\".
     *  @return     a reference to a vector of strings
     */
    public Vector<String>             GetMediaStreamStructureId   ();
}
