package com.zhanghui.network;

import android.util.Log;

import com.zhanghui.metric.HTTPTransaction;
import com.zhanghui.metric.HTTPTransactionType;
import com.zhanghui.metric.IHTTPTransaction;
import com.zhanghui.metric.ITCPConnection;
import com.zhanghui.metric.TCPConnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public abstract class AbstractChunk implements IDownloadableChunk {
    /*
    * Pure public abstract IChunk Interface
    */
    public abstract String    AbsoluteURI     ();
    public abstract String    Host            ();
    public abstract int          Port            ();
    public abstract String    Path            ();
    public abstract String    Range           ();
    public abstract int          StartByte       ();
    public abstract int          EndByte         ();
    public abstract boolean            HasByteRange    ();
    public abstract HTTPTransactionType GetType();
    /*
     * IDownloadableChunk Interface
     */
    public boolean    StartDownload           (IConnection connection){
        if(this.stateManager.State()!=DownloadState.NOT_STARTED)
            return false;

        ExternalDLThread=new DownloadExternalConnection();
        ExternalDLThread.setName("ExternalDLThread");
        ExternalDLThread.setChunk(this);
        ExternalDLThread.start();
        this.stateManager.State(DownloadState.IN_PROGRESS);
        this.connection=connection;
        return true;
    }

    public boolean    StartDownload           (){
        if(this.stateManager.State()!=DownloadState.NOT_STARTED)
            return false;

        try {
            //根据URL地址实例化一个URL对象，用于创建HttpURLConnection对象。
            URL url = new URL(this.AbsoluteURI());
            if(url != null){
                //openConnection获得当前URL的连接
                httpURLConnection=(HttpURLConnection)url.openConnection();
                //设置3秒的响应超时
                httpURLConnection.setConnectTimeout(3000);
                //设置允许输入
                httpURLConnection.setDoInput(true);
                //设置为GET方式请求数据
                httpURLConnection.setRequestMethod("GET");

                if(this.HasByteRange())
                    httpURLConnection.setRequestProperty("Range","bytes=" + this.Range());

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        HTTPTransaction httpTransaction = new HTTPTransaction();

        httpTransaction.SetOriginalUrl(this.AbsoluteURI());
        httpTransaction.SetRange(this.Range());
        httpTransaction.SetType(this.GetType());
        Date RequestDate=new Date();
        //TODO:是在这里计算请求时间吗？
        httpTransaction.SettRequest(RequestDate.toString());
        this.httpTransactions.add(httpTransaction);

        InternalDLThread=new DownloadInternalConnection();
        InternalDLThread.setName("InternalDLThrad");
        InternalDLThread.setChunk(this);
        InternalDLThread.start();
        this.stateManager.State(DownloadState.IN_PROGRESS);
        return true;
    }

    public void    AbortDownload           (){
        this.stateManager.CheckAndSet(DownloadState.IN_PROGRESS, DownloadState.REQUEST_ABORT);
        this.stateManager.CheckAndWait(DownloadState.REQUEST_ABORT, DownloadState.ABORTED);
    }
    //TODO:实现read和peek
    //public abstract int     Read                    (byte[] data, int len);
    //public abstract int     Peek                    (byte[] data, int len);
    public void    AttachDownloadObserver  (IDownloadObserver observer){
        this.observers.add(observer);
        this.stateManager.Attach(observer);
    }
    public void    DetachDownloadObserver  (IDownloadObserver observer){
        this.observers.remove(observer);
        this.stateManager.Detach(observer);
    }
    /*
     * Observer Notification
     */
    public void NotifyDownloadRateChanged (){
        for(int i=0;i<this.observers.size();i++){
            if(this.DownloadSize>0)
                this.observers.elementAt(i).OnDownloadRateChanged(this.DownloadSpeed, this.DownloadSize, this.DownloadTime);
        }
    }
    /*
    * IDASHMetrics
    */
    public Vector<TCPConnection>     GetTCPConnectionList    (){
        return this.tcpConnections;
    }
    public Vector<HTTPTransaction>   GetHTTPTransactionList  (){
        return this.httpTransactions;
    }


    private Vector<IDownloadObserver> observers;

    private class DownloadInternalConnection extends Thread{
        AbstractChunk chunk;

        public void setChunk(AbstractChunk chunk) {
            this.chunk = chunk;
        }

        public void run(){
            try {
                //TODO:到底什么时候算是连接开始
                httpURLConnection.connect();
                //获取连接响应码，200为成功，如果为其他，均表示有问题
                chunk.responseCode = (short)httpURLConnection.getResponseCode();
                Log.d(TAG, "Status code: " + chunk.responseCode);
                HTTPTransaction httpTransaction = chunk.httpTransactions.lastElement();
                Date ResponseDate=new Date();
                httpTransaction.SettResponse(ResponseDate.toString());
                httpTransaction.SetResponseCode(chunk.responseCode);
                if(chunk.responseCode/100==2) {
                    //getInputStream获取服务端返回的数据流。
                    chunk.blockStream = httpURLConnection.getInputStream();
                    chunk.bytesDownloaded=httpURLConnection.getContentLength();
                    chunk.bytesDownloaded+=bytesDownloaded;
                    //TODO:计算下载时间和下载速度
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            if(chunk.stateManager.State()==DownloadState.REQUEST_ABORT)
                chunk.stateManager.State(DownloadState.ABORTED);
            else {
                chunk.stateManager.State(DownloadState.COMPLETED);
                chunk.NotifyDownloadRateChanged();
            }
            //chunk->blockStream.SetEOS(true);
        }
    }

    private class DownloadExternalConnection extends Thread{
        AbstractChunk chunk;
        int  ret     = 0;
        byte []block=new byte[BLOCKSIZE];
        public void setChunk(AbstractChunk chunk) {
            this.chunk = chunk;
        }

        public void run(){
            //TODO:实现External run
            //chunk->blockStream.SetEOS(true);
        }
    }

    private static final String TAG = AbstractChunk.class.getSimpleName();
    DownloadInternalConnection InternalDLThread=null;
    DownloadExternalConnection ExternalDLThread=null;
    private IConnection                         connection;
    private InputStream                         blockStream;
    private HttpURLConnection                   httpURLConnection=null;
    private short                            responseCode;
    private int                            bytesDownloaded;
    private DownloadStateManager                stateManager;
    private Vector<TCPConnection>    tcpConnections;
    private Vector<HTTPTransaction>  httpTransactions;
    private static final int BLOCKSIZE=32768;
    private static double	DownloadSpeed=0;     // Defaults to bytes/second
    private static double	DownloadTime=0;
    private static double	DownloadSize=0;

    //private static int   CurlResponseCallback        (void *contents, size_t size, size_t nmemb, void *userp);
}
