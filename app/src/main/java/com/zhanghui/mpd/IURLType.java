package com.zhanghui.mpd;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IURLType extends  IMPDElement{
    /**
     *  Returns a reference to a string that pecifies the source URL part and shall be formated either as an \c <absolute-URI> according to RFC 3986, Clause 4.3,
     *  with a fixed scheme of \"http\" or \"https\" or as a \c <relative-ref> according to RFC 3986, Clause 4.2.\n
     * If not present, then any <tt><b>BaseURL</b></tt> element is mapped to the \c \@sourceURL attribute and the range attribute shall be present.
     *  @return     a reference to a string
     */
    public String  GetSourceURL    ();

    /**
     *  Returns a reference to a string that specifies the byte range restricting the above HTTP-URL.\n
     *  The byte range shall be expressed and formatted as a byte-range-spec as defined in RFC 2616, Clause 14.35.1. It is restricted to a single expression identifying a
     *  contiguous range of bytes.\n
     *  If not present, the element refers to the entire resource referenced in the \c \@sourceURL attribute.
     *  @return     a reference to a string
     */
    public String  GetRange        ();

    /**
     *  Returns a pointer to a dash::mpd::ISegment object, that can be downloaded
     *  @param      baseurls    a reference to a vector of pointers to dash::mpd::IBaseUrl objects representing the path to \c \@sourceURL
     *  @return     a pointer to a dash::mpd::ISegment object
     */
    public ISegment           ToSegment       (Vector<BaseUrl> baseurls);
}
