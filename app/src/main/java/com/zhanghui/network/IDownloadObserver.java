package com.zhanghui.network;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IDownloadObserver {
    /**
     *  Informs the dash::network::IDownloadObserver object that the download rate has changed.
     *  @param      DownloadSize     the number of downloaded bytes
     *  @param      DownloadByteRate    downloaded byte rate
     */
    //virtual void OnDownloadRateChanged  (uint64_t bytesDownloaded)  = 0;
    public void OnDownloadRateChanged(double DownloadByteRate, double DownloadSize, double DownloadTime);

    /**
     *  Informs the dash::network::IDownloadObserver object that the download state has changed.
     *  @param      state               the download state
     */
    public void OnDownloadStateChanged (DownloadState state);
}
