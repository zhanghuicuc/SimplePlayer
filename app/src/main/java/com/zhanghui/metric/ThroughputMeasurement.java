package com.zhanghui.metric;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class ThroughputMeasurement implements IThroughputMeasurement,Serializable {
    private String             startOfPeriod;
    private long                durationOfPeriod;
    private Arrays receivedBytesPerTrace;

    public String StartOfPeriod() {
        return startOfPeriod;
    }

    public void SetStartOfPeriod(String startOfPeriod) {
        this.startOfPeriod = startOfPeriod;
    }

    public long DurationOfPeriod() {
        return durationOfPeriod;
    }

    public void SetDurationOfPeriod(long durationOfPeriod) {
        this.durationOfPeriod = durationOfPeriod;
    }

    public Arrays ReceivedBytesPerTrace() {
        return receivedBytesPerTrace;
    }

    public void SetReceivedBytesPerTrace(Arrays receivedBytesPerTrace) {
        this.receivedBytesPerTrace = receivedBytesPerTrace;
    }
}
