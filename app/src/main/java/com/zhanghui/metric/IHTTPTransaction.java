package com.zhanghui.metric;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IHTTPTransaction {
    public int                                        TCPId                   ();
    public HTTPTransactionType                             Type                    ();
    public String                              OriginalUrl             ();
    public String                              ActualUrl               ();
    public String                              Range                   ();
    public String                             RequestSentTime         ();
    public String                              ResponseReceivedTime    ();
    public short                                        ResponseCode            ();
    public long                                        Interval                ();
    public Vector<ThroughputMeasurement> ThroughputTrace         ();
}
