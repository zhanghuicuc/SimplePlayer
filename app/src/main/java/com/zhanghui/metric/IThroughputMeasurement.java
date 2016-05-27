package com.zhanghui.metric;

import java.util.Arrays;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IThroughputMeasurement {
    public String              StartOfPeriod           ();
    public long                        DurationOfPeriod        ();
    public Arrays ReceivedBytesPerTrace   ();
}
