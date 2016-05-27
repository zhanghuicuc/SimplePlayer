package com.zhanghui.mpd;

import com.zhanghui.helper.myString;
import com.zhanghui.metric.HTTPTransaction;
import com.zhanghui.metric.TCPConnection;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class MPD extends AbstractMPDElement implements IMPDElement {
    public MPD() {
        id="";
        type="static";
        availabilityStarttime="";
        publishTime="";
        availabilityEndtime="";
        mediaPresentationDuration="";
        minimumUpdatePeriod="";
        minBufferTime="";
        timeShiftBufferDepth="";
        suggestedPresentationDelay="";
        maxSegmentDuration="";
        maxSubsegmentDuration="";
        programInformations=new Vector<ProgramInformation>();
        baseUrls=new Vector<BaseUrl>();
        locations=new Vector<String>();
        periods=new Vector<Period>();
        metrics=new Vector<Metrics>();
        profiles=new Vector<String>();
        tcpConnections=new Vector<TCPConnection>();
        httpTransactions=new Vector<HTTPTransaction>();
    }

    public Vector<ProgramInformation> GetProgramInformations() {
        return programInformations;
    }

    public void AddProgramInformations(ProgramInformation programInformation) {
        this.programInformations.add( programInformation);
    }

    public Vector<BaseUrl> GetBaseUrls() {
        return baseUrls;
    }

    public void AddBaseUrls(BaseUrl baseUrl) {
        this.baseUrls.add( baseUrl);
    }

    public Vector<String> GetLocations() {
        return locations;
    }

    public void AddLocations(String location) {
        this.locations.add( location);
    }

    public Vector<Period> GetPeriods() {
        return periods;
    }

    public void AddPeriods(Period period) {
        this.periods.add( period);
    }

    public Vector<Metrics> GetMetrics() {
        return metrics;
    }

    public void AddMetrics(Metrics metric) {
        this.metrics.add( metric);
    }

    public String GetId() {
        return id;
    }

    public void SetId(String id) {
        this.id = id;
    }

    public Vector<String> GetProfiles() {
        return profiles;
    }

    public void SetProfiles(String profiles) {
        myString.Split(profiles,",",this.profiles);
    }

    public String GetType() {
        return type;
    }

    public void SetType(String type) {
        this.type = type;
    }

    public String GetAvailabilityStarttime() {
        return availabilityStarttime;
    }

    public void SetAvailabilityStarttime(String availabilityStarttime) {
        this.availabilityStarttime = availabilityStarttime;
    }

    public String GetPublishTime() {
        return publishTime;
    }

    public void SetPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String GetAvailabilityEndtime() {
        return availabilityEndtime;
    }

    public void SetAvailabilityEndtime(String availabilityEndtime) {
        this.availabilityEndtime = availabilityEndtime;
    }

    public String GetMediaPresentationDuration() {
        return mediaPresentationDuration;
    }

    public void SetMediaPresentationDuration(String mediaPresentationDuration) {
        this.mediaPresentationDuration = mediaPresentationDuration;
    }

    public String GetMinimumUpdatePeriod() {
        return minimumUpdatePeriod;
    }

    public void SetMinimumUpdatePeriod(String minimumUpdatePeriod) {
        this.minimumUpdatePeriod = minimumUpdatePeriod;
    }

    public String GetMinBufferTime() {
        return minBufferTime;
    }

    public void SetMinBufferTime(String minBufferTime) {
        this.minBufferTime = minBufferTime;
    }

    public String GetTimeShiftBufferDepth() {
        return timeShiftBufferDepth;
    }

    public void SetTimeShiftBufferDepth(String timeShiftBufferDepth) {
        this.timeShiftBufferDepth = timeShiftBufferDepth;
    }

    public String GetSuggestedPresentationDelay() {
        return suggestedPresentationDelay;
    }

    public void SetSuggestedPresentationDelay(String suggestedPresentationDelay) {
        this.suggestedPresentationDelay = suggestedPresentationDelay;
    }

    public String GetMaxSegmentDuration() {
        return maxSegmentDuration;
    }

    public void SetMaxSegmentDuration(String maxSegmentDuration) {
        this.maxSegmentDuration = maxSegmentDuration;
    }

    public String GetMaxSubsegmentDuration() {
        return maxSubsegmentDuration;
    }

    public void SetMaxSubsegmentDuration(String maxSubsegmentDuration) {
        this.maxSubsegmentDuration = maxSubsegmentDuration;
    }

    public BaseUrl GetMpdPathBaseUrl() {
        return mpdPathBaseUrl;
    }

    public void SetMpdPathBaseUrl(BaseUrl mpdPathBaseUrl) {
        this.mpdPathBaseUrl = mpdPathBaseUrl;
    }

    public int GetFetchTime() {
        return fetchTime;
    }

    public void SetFetchTime(int fetchTimeInSec) {
        this.fetchTime = fetchTimeInSec;
    }

    public Vector<TCPConnection> GetTcpConnections() {
        return tcpConnections;
    }

    public void AddTcpConnections(TCPConnection tcpConnection) {
        this.tcpConnections.add(tcpConnection);
    }

    public Vector<HTTPTransaction> GetHttpTransactions() {
        return httpTransactions;
    }

    public void AddHttpTransactions(HTTPTransaction httpTransaction) {
        this.httpTransactions.add(httpTransaction);
    }

    private Vector<ProgramInformation>   programInformations;
    private Vector<BaseUrl>              baseUrls;
    private Vector<String>               locations;
    private Vector<Period>               periods;
    private Vector<Metrics>              metrics;
    private String                         id;
    private Vector<String>            profiles;
    private String                         type;
    private String                         availabilityStarttime;
    private String                         publishTime;
    private String                         availabilityEndtime;
    private String                         mediaPresentationDuration;
    private String                         minimumUpdatePeriod;
    private String                         minBufferTime;
    private String                         timeShiftBufferDepth;
    private String                         suggestedPresentationDelay;
    private String                         maxSegmentDuration;
    private String                         maxSubsegmentDuration;
    private BaseUrl                             mpdPathBaseUrl;
    private int                            fetchTime;

    private Vector<TCPConnection>     tcpConnections;
    private Vector<HTTPTransaction>   httpTransactions;
}
