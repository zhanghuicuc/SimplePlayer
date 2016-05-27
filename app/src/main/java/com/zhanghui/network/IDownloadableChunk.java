package com.zhanghui.network;

import com.zhanghui.metric.IDASHMetrics;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IDownloadableChunk extends IChunk {
    /**
     *  Starts the download of this chunk and returns a bool value whether the starting of the download was possible or not
     *  @return     a bool value
     */
    public boolean    StartDownload           () ;

    /**
     *  Starts the download of this chunk and returns a bool value whether the starting of the download was possible or not
     *  @param      connection      the dash::network::IConnection that shall be used for downloading
     *  @return     a bool value
     */
    public boolean    StartDownload           (IConnection connection);

    /**
     *  Aborts the download of a chunk
     */
    public void    AbortDownload           ();

    /**
     *  Reads
     *  @param      data    pointer to a block of memory
     *  @param      len     size of the memory block that can be used by the method
     *  @return     amount of data that has been read
     */
    //public int     Read                    (byte[] data, int len);

    /**
     *  Peeks
     *  @param      data    pointer to a block of memory
     *  @param      len     size of the memory block that can be used by the method
     *  @return     amount of data that has been peeked
     */
    //public int     Peek                    (byte[] data, int len);

    /**
     *  Attaches a dash::network::IDownloadObserver to this Chunk
     *  @param      observer    a dash::network::IDownloadObserver
     */
    public void    AttachDownloadObserver  (IDownloadObserver observer);

    /**
     *  Detaches a dash::network::IDownloadObserver from this Chunk
     *  @param      observer    a dash::network::IDownloadObserver
     */
    public void    DetachDownloadObserver  (IDownloadObserver observer);
}
