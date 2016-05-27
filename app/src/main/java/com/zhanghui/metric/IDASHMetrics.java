package com.zhanghui.metric;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IDASHMetrics {
    /**
     *  This function returns a list of DASH Metrics as defined in <em>ISO/IEC 23009-1, Part 1, 2012</em>, annex D.4.2.
     *  @return     a list of dash::metrics::ITCPConnection Metrics Objects
     */
    public Vector<TCPConnection> GetTCPConnectionList    ();

    /**
     *  This function returns a list of DASH Metrics as defined in <em>ISO/IEC 23009-1, Part 1, 2012</em>, annex D.4.3.
     *  @return     a list of dash::metrics::IHTTPConnection Metrics Objets
     */
    public Vector<HTTPTransaction> GetHTTPTransactionList  ();
}
