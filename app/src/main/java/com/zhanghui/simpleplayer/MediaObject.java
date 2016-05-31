package com.zhanghui.simpleplayer;

import android.os.Environment;

import com.zhanghui.mpd.IRepresentation;
import com.zhanghui.mpd.ISegment;
import com.zhanghui.mpd.Representation;
import com.zhanghui.network.DownloadState;
import com.zhanghui.network.IDownloadObserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhanghui on 2016/5/30.
 */
public class MediaObject implements IDownloadObserver {
    public MediaObject             (com.zhanghui.mpd.Segment segment, Representation rep){
        this.segment=segment;
                this.rep=rep;
                this.last_download_byterate=(0.0);
        this.last_download_size=(0.0);
        this.last_download_time=(0.0);
    }

    public File GetBufferfile() {
        return bufferfile;
    }

    public boolean    StartDownload					(){
        this.segment.AttachDownloadObserver(this);
        return this.segment.StartDownload();
    }

    public synchronized void    WaitFinished					(){
        while(this.state != DownloadState.COMPLETED && this.state != DownloadState.ABORTED){
            try{
                wait();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        try {
            String filename=this.segment.AbsoluteURI().substring(this.segment.AbsoluteURI().lastIndexOf("/")+1);
            bufferfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "buffered_"+filename);
            FileOutputStream testStream = new FileOutputStream(bufferfile);
            int bufChar = 0;
            while ((bufChar = this.segment.GetBlockStream().read()) != -1) {
                testStream.write(bufChar);
            }
            testStream.flush();
            testStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //int     Read							(byte[] data, int len)
    //int     Peek							(byte[] data, int len)

    public int		GetBandWidth					(){
        return this.rep.GetBandwidth();
    }		//in bps
    public double	GetLastDownloadByteRate			(){
        return this.last_download_byterate;
    }	//in byte rate
    public double	GetLastDownloadSize				(){
        return this.last_download_size;
    }   //in byte rate
    public double	GetLastDownloadTime				(){
        return this.last_download_time;
    }	//in byte rate

    public synchronized void     OnDownloadStateChanged  (DownloadState state){
        this.state = state;
        notifyAll();
    }
    //virtual void    OnDownloadRateChanged   (uint64_t bytesDownloaded);
    public void    OnDownloadRateChanged	(double DownloadByteRate, double DownloadSize, double DownloadTime){
        this.last_download_byterate = DownloadByteRate;
        this.last_download_size = DownloadSize;
        this.last_download_time = DownloadTime;
    }


    private com.zhanghui.mpd.Segment             segment;
    private Representation      rep;
    private DownloadState    state;
    private File bufferfile;
    private double							last_download_byterate;
    private double							last_download_time;
    private double							last_download_size;

}
