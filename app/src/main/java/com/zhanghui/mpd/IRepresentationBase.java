package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IRepresentationBase extends IMPDElement {
    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IDescriptor objects that specifies frame-packing arrangement information of the video media component type.\n
     *  When no <tt><b>FramePacking</b></tt> element is provided for a video component, frame-packing shall not used for the video media component.\n
     *  For further details see sections 5.8.1 and 5.8.4.6 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IDescriptor objects
     */
    public Vector<Descriptor>   GetFramePacking                 ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IDescriptor objects that specifies the audio channel configuration of the audio media component type.\n
     *  For further details see sections 5.8.1 and 5.8.4.7 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IDescriptor objects
     */
    public Vector<Descriptor>   GetAudioChannelConfiguration    ();

    /**
     *  Returns a reference to a vector of pointers to dash::mpd::IDescriptor objects that specifies information about content protection schemes used for the associated Representations.\n
     *  For further details see sections 5.8.1 and 5.8.4.1 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  @return     a reference to a vector of pointers to dash::mpd::IDescriptor objects
     */
    public Vector<Descriptor>   GetContentProtection            ();

    /**
     *  Returns a reference to a vector of strings that specifies the profiles which the associated Representation(s)
     *  conform to of the list of Media Presentation profiles as described in section 8 of <em>ISO/IEC 23009-1, Part 1, 2012</em>.
     *  The value shall be a subset of the respective value in any higher level of the document hierarchy (Representation, Adaptation Set, MPD).\n
     *  If not present, the value is inferred to be the same as in the next higher level of the document hierarchy.
     *  For example, if the value is not present for a Representation, then \c \@profiles at the Adaptation Set level is valid for the Representation.\n
     *  The same syntax as defined in 5.3.1.2 shall be used.
     *  @return     a reference to a vector of strings
     */
    public Vector<String>    GetProfiles                     ();

    /**
     *  Returns an integer that specifies the horizontal visual presentation size of the video
     *  media type on a grid determined by the \c \@sar attribute. \n\n
     *  In the absence of \c \@sar width and height are specified as if the value of \c \@sar were \"1:1\".\n
     *  \b NOTE:    The visual presentation size of the video is equal to the number of horizontal and vertical samples used for presentation
     *  after encoded samples are cropped in response to encoded cropping parameters, 搊verscan?signaling, or 損an/scan?display parameters, e.g. SEI messages.
     *  @return     an unsigned integer
     */
    public int                            GetWidth                        ();

    /**
     *  Returns an integer that specifies the vertical visual presentation size of the video
     *  media type, on a grid determined by the \c \@sar attribute.
     *  @return     an unsigned integer
     */
    public int                            GetHeight                       ();

    /**
     *  Returns a string that specifies the sample aspect ratio of the video media component type,
     *  in the form of a string consisting of two integers separated by ':', e.g., \"10:11\".
     *  The first number specifies the horizontal size of the encoded video pixels (samples) in arbitrary units.
     *  The second number specifies the vertical size of the encoded video pixels (samples) in same units as the horizontal size.
     *  @return     a string
     */
    public String                         GetSar                          ();

    /**
     *  Returns a string that specifies the output frame rate (or in the case of interlaced, half the output field rate)
     *  of the video media type in the Representation. If the frame or field rate is varying, the value is the average frame
     *  or half the average field rate field rate over the entire duration of the Representation.\n
     *  The value is coded as a string, either containing two integers separated by a \"/\", (\"F/D\"), or a single integer \"F\".
     *  The frame rate is the division F/D, or F, respectively, per second (i.e. the default value of D is \"1\").
     *  @return     a string
     */
    public String                         GetFrameRate                    ();

    /**
     *  Returns a string that represents an audio sampling rate. \n
     *  Either a single decimal integer value specifying the sampling rate or a whitespace separated pair of decimal integer
     *  values specifying the minimum and maximum sampling rate of the audio media component type. The values are in samples per second.
     *  @return     a string
     */
    public String                         GetAudioSamplingRate            ();

    /**
     *  Returns a string that specifies the MIME type of the concatenation of the Initialization Segment, if present,
     *  and all consecutive Media Segments in the Representation.
     *  @return     a string
     */
    public String                         GetMimeType                     ();

    /**
     *  Returns a reference to a vector of strings that specifies the profiles of Segments that are essential to process the Representation.
     *  The detailed semantics depend on the value of the \c \@mimeType attribute. The contents of this attribute shall conform to either the
     *  \c pro-simple or \c pro-fancy productions of RFC6381, Section 4.5, without the enclosing \c DQUOTE characters,
     *  i.e. including only the \c unencodedv or \c encodedv elements respectively.
     *  As profile identifier the brand identifier for the Segment as defined in section 6 of <em>ISO/IEC 23009-1, Part 1, 2012</em> shall be used.\n
     *  If not present on any level, the value may be deducted from the value of the\c \@profiles attribute.
     *  @return     a reference to a vector of strings
     */
    public Vector<String>     GetSegmentProfiles              ();

    /**
     *  Returns a reference to a vector of strings that specifies the codecs present within the Representation.
     *  The codec parameters shall also include the profile and level information where applicable. \n
     *  The contents of this attribute shall conform to either the \c simp-list or \c fancy-list productions of RFC6381, Section 3.2,
     *  without the enclosing \c DQUOTE characters. The codec identifier for the Representation's media format,
     *  mapped into the name space for codecs as specified in RFC6381, Section 3.3, shall be used.
     *  @return     a reference to a vector of strings
     */
    public Vector<String> GetCodecs                       ();

    /**
     *  Returns a double value specifying the maximum SAP interval in seconds of all contained media streams,
     *  where the SAP interval is the maximum time interval between the T<sub>SAP</sub> of any two successive SAPs of types 1 to 3
     *  inclusive of one media stream in the associated Representations.
     *  @return     a double
     */
    public double                              GetMaximumSAPPeriod             ();

    /**
     *  Returns an unsigned integer that (when greater than 0) specifies that in the associated Representations,
     *  each Media Segment starts with a SAP of type less than or equal to the value of this attribute value in each media stream.
     *  @return     an unsigned integer
     */
    public byte                             GetStartWithSAP                 ();

    /**
     *  Returns a double that specifies the maximum playout rate as a multiple of the regular playout rate,
     *  which is supported with the same decoder profile and level requirements as the normal playout rate.
     *  @return     a double
     */
    public double                              GetMaxPlayoutRate               ();

    /**
     *  Returns a bool value that informs about coding dependency.\n
     *  If \c 'true', for all contained media streams, specifies that there is at least one access unit that depends on one or more other access units for decoding.
     *  If \c 'false', for any contained media stream, there is no access unit that depends on any other access unit for decoding (e.g. for video all the pictures are intra coded). \n
     *  If not specified on any level, there may or may not be coding dependency between access units.
     *  @return     a bool value
     */
    public boolean                                HasCodingDependency             ();

    /**
     *  Returns a string that specifies the scan type of the source material of the video media component type.
     *  The value may be equal to one of \c \"progressive\", \c \"interlaced\" and \c \"unknown\".
     *  @return     a string
     */
    public String                         GetScanType                     ();
}
