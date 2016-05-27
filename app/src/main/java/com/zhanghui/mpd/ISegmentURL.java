package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface ISegmentURL extends IMPDElement {
    /**
     *  Returns a reference to a string that  in combination with the \c \@mediaRange attribute specifies the HTTP-URL for the Media Segment.\n
     *  It shall be formated as an \c <absolute-URI> according to RFC 3986, Clause 4.3, with a fixed scheme of \"http\" or \"https\" or
     *  as a \c <relative-ref> according to RFC 3986, Clause 4.2.\n
     *  If not present, then any <tt><b>BaseURL</b></tt> element is mapped to the \c \@media attribute and the range attribute shall be present.
     *  @return     a reference to a string
     */
    public String  GetMediaURI     ();

    /**
     *  Returns a reference to a string that specifies the byte range within the resource identified by the \c \@media corresponding to the Media Segment.
     *  The byte range shall be expressed and formatted as a \c byte-range-spec as defined in RFC 2616, Clause 14.35.1.
     *  It is restricted to a single expression identifying a contiguous range of bytes.\n
     *  If not present, the Media Segment is the entire resource referenced by the \c \@media attribute.
     *  @return     a reference to a string
     */
    public String  GetMediaRange   ();

    /**
     *  Returns a reference to a string that in combination with the \c \@indexRange attribute specifies the HTTP-URL for the Index Segment.
     *  It shall be formated as an \c <absolute-URI> according to RFC 3986, Clause 4.3, with a fixed scheme of \"http\" or \"https\" or
     *  as a \c <relative-ref> according to RFC 3986, Clause 4.2. \n
     *  If not present and the \c \@indexRange not present either, then no Index Segment information is provided for this Media Segment.\n
     *  If not present and the \c \@indexRange present, then the \c \@media attribute is mapped to the \c \@index. If the \c \@media attribute is not present either,
     *  then any <tt><b>BaseURL</b></tt> element is mapped to the \c \@index attribute and the \c \@indexRange attribute shall be present.
     *  @return     a reference to a string
     */
    public String  GetIndexURI     ();

    /**
     *  Returns a reference to a string that specifies the byte range within the resource identified by the \c \@index corresponding to the Index Segment.
     *  If \c \@index is not present, it specifies the byte range of the Segment Index in Media Segment.\n
     *  The byte range shall be expressed and formatted as a \c byte-range-spec as defined in RFC 2616, Clause 14.35.1. It is restricted to a single
     *  expression identifying a contiguous range of bytes. \n
     *  If not present, the Index Segment is the entire resource referenced by the \c \@index attribute.
     *  @return     a reference to a string
     */
    public String  GetIndexRange   ();

    /**
     *  Returns a pointer to a dash::mpd::ISegment object which represents a media segment that can be downloaded.
     *  @param      baseurls    a vector of pointers to dash::mpd::IBaseUrl objects that represent the path to the media segment
     *  @return     a pointer to a dash::mpd::ISegment object
     */
    public ISegment           ToMediaSegment  (Vector<IBaseUrl> baseurls);

    /**
     *  Returns a pointer to a dash::mpd::ISegment object that represents an index segment and can be downloaded.
     *  @param      baseurls    a vector of pointers to dash::mpd::IBaseUrl objects that represent the path to the index segment
     *  @return     a pointer to a dash::mpd::ISegment object
     */
    public ISegment           ToIndexSegment ( Vector<IBaseUrl> baseurls);
}
