package com.zhanghui.mpd;

import com.zhanghui.network.IDownloadableChunk;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface ISegment extends IDownloadableChunk {
    /**
     *  This method allows you to specify an absolute URI for this Segment
     *  @param      uri     a string representing an URI
     */
    public abstract void    AbsoluteURI  (String uri);

    /**
     *  This method allows you to specify a host for this Segment
     *  @param      host    a string representing an host
     */
    public abstract void    Host         (String host);

    /**
     *  This method allows you to specify a port for this Segment
     *  @param      port    an integer representing a portnumber
     */
    public abstract void    Port         (int port);

    /**
     *  This method allows you to specify a path for this Segment
     *  @param      path    a string representing a path
     */
    public abstract void    Path         (String path);

    /**
     *  This method allows you to specify a byte range for this Segment
     *  @param      range   a string representing a byte range as definend in <em>ISO/IEC 23009-1, Part 1, 2012</em>, section 5.3.9.2.2, table 13:\n
    <em>The byte range shall be expressed and formatted as a \c byte-range-spec as defined in RFC 2616, Clause 14.35.1.
    It is restricted to a single expression identifying a contiguous range of bytes.</em>
     */
    public abstract void    Range        (String range);

    /**
     *  This method allows you to specify the start byte for this Segment
     *  @param      startByte   an integer representing the start byte
     */
    public abstract void    StartByte    (int startByte);

    /**
     *  This method allows you to specify the end byte for this Segment
     *  @param      endByte     an integer representing the end byte
     */
    public abstract void    EndByte      (int endByte);

    /**
     *  This method allows you to specify whether this Segment has a byte range or not
     *  @param      hasByteRange    a bool value, \c true to specify that this Segment has a byte range, \c false otherwise
     */
    public abstract void    HasByteRange (boolean hasByteRange);
}
