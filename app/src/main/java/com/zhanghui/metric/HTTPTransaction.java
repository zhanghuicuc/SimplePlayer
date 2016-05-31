package com.zhanghui.metric;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class HTTPTransaction implements IHTTPTransaction,Serializable {
    //Identifier of the TCP connection on which the HTTP request was sent.
    private int                                tcpId;
    private HTTPTransactionType                     type;
    private String                             url;
    private String                             actualUrl;
    private String                             range;
    private String                             tRequest;
    private String                             tResponse;
    private short                                responseCode;
    private long                                interval;
    private Vector<ThroughputMeasurement>    trace;

    public HTTPTransaction() {
        tcpId=0;
        type=HTTPTransactionType.Other;
        responseCode=0;
        interval=0;
        url="";
        actualUrl="";
        range="";
        tRequest="";
        tResponse="";
        trace=new Vector<ThroughputMeasurement>();
    }

    public void SetTcpId(int tcpId) {
        this.tcpId = tcpId;
    }

    public void SetType(HTTPTransactionType type) {
        this.type = type;
    }

    public void SetOriginalUrl(String url) {
        this.url = url;
    }

    public void SetActualUrl(String actualUrl) {
        this.actualUrl = actualUrl;
    }

    public void SetRange(String range) {
        this.range = range;
    }

    public void SettRequest(String tRequest) {
        this.tRequest = tRequest;
    }

    public void SettResponse(String tResponse) {
        this.tResponse = tResponse;
    }

    public void SetResponseCode(short responseCode) {
        this.responseCode = responseCode;
    }

    public void SetInterval(long interval) {
        this.interval = interval;
    }

    public void AddThroughputMeasurement(ThroughputMeasurement throuputEntry) {
        this.trace.add(throuputEntry);
    }

    public int TCPId() {
        return tcpId;
    }

    @Override
    public HTTPTransactionType Type() {
        return type;
    }

    public String OriginalUrl() {
        return url;
    }

    @Override
    public String ActualUrl() {
        return actualUrl;
    }

    @Override
    public String Range() {
        return range;
    }

    public String RequestSentTime() {
        return tRequest;
    }

    public String ResponseReceivedTime() {
        return tResponse;
    }

    @Override
    public short ResponseCode() {
        return responseCode;
    }

    @Override
    public long Interval() {
        return interval;
    }

    public Vector<ThroughputMeasurement> ThroughputTrace() {
        return trace;
    }
}
