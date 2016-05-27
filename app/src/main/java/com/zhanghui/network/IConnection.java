package com.zhanghui.network;

import com.zhanghui.metric.IDASHMetrics;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IConnection extends IDASHMetrics{
    /**
     *  This function should read a block of bytes from the specified chunk.
     *  @param      data    pointer to a block of memory
     *  @param      len     size of the memory block that can be used by the method
     *  @param      chunk   the dash::network::IChunk object from which data should be read
     *  @return     amount of data that has been read
     */
    public int Read (byte data, int len, IChunk chunk);

    /**
     *  This function should peek a block of bytes from the specified chunk.
     *  @param      data    pointer to a block of memory
     *  @param      len     size of the memory block that can be used by the method
     *  @param      chunk   the dash::network::IChunk object from which data should be peeked
     *  @return     amount of data that has been peeked
     */
    public int Peek (byte data, int len, IChunk chunk);
}
