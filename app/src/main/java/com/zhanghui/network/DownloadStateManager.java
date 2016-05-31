package com.zhanghui.network;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/18.
 */
public class DownloadStateManager implements Serializable {
    private DownloadState state;
    private Vector<IDownloadObserver> observers;

    public DownloadStateManager() {
        this.observers=new Vector<IDownloadObserver>();
        this.state=DownloadState.NOT_STARTED;
    }

    public synchronized DownloadState State         (){
        return this.state;
    }
    public synchronized void            State         (DownloadState state){
        this.state = state;
        Notify();
    }
    public void            Notify        ()
    {
        for(int i = 0; i < this.observers.size(); i++)
            this.observers.elementAt(i).OnDownloadStateChanged(this.state);
    }
    public synchronized void            WaitState     (DownloadState state){
        while(this.state != state){
            try{
                wait();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public synchronized void            CheckAndWait  (DownloadState check, DownloadState wait){
        if(this.state == check){
            while(this.state != wait){
                try{
                    wait();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void            Attach        (IDownloadObserver observer){
        this.observers.add(observer);
    }

    public synchronized void            Detach        (IDownloadObserver observer){
        this.observers.remove(observer);
    }

    public synchronized void            CheckAndSet   (DownloadState check, DownloadState set){
          if(this.state == check)
            this.state = set;
    }
}
