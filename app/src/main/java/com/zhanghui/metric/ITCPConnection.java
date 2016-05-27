package com.zhanghui.metric;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface ITCPConnection {
    public int            TCPId                   ();
    public String  DestinationAddress      ();
    public String  ConnectionOpenedTime    ();
    public String  ConnectionClosedTime    ();
    public long            ConnectionTime          ();
}
