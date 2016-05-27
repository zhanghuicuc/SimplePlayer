package com.zhanghui.network;

import com.zhanghui.metric.HTTPTransactionType;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IChunk {
    /**
     *  Returns a reference to a string that specifies the absolute URI to this chunk
     *  @return     a reference to a string
     */
    public String    AbsoluteURI     ();

    /**
     *  Returns a reference to a string that specifies the host of this chunk
     *  @return     a reference to a string
     */
    public String   Host            ();

    /**
     *  Returns an unsigned integer representing the port belonging to this chunk
     *  @return     an unsigned integer
     */
    public int          Port            ();

    /**
     *  Returns a reference to a string that specifies the path to this chunk
     *  @return     a reference to a string
     */
    public String    Path            ();

    /**
     *  Returns a reference to a string that specifies the byte range belonging to this chunk
     *  @return     a reference to a string
     */
    public String    Range           ();

    /**
     *  Returns an unsigned integer representing the start byte of the byte range that belongs to this chunk
     *  @return     an unsigned integer
     */
    public int         StartByte       ();

    /**
     *  Returns an unsigned integer representing the end byte of the byte range that belongs to this chunk
     *  @return     an unsigned integer
     */
    public int          EndByte         ();

    /**
     *  Returns a bool value that represents whether this chunk has a byte range or not
     *  @return     a bool value
     */
    public boolean             HasByteRange    ();

    /**
     *  Returns the type of a <b>HTTP Request/Response Transaction</b> in <em>ISO/IEC 23009-1, Part 1, 2012</em>, annex D.4.3, Table D.2
     *  @return     a dash::metrics::HTTPTransactionType
     */
    public HTTPTransactionType GetType  ();
}
