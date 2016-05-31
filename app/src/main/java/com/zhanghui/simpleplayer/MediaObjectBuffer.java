package com.zhanghui.simpleplayer;

import java.util.ArrayDeque;

/**
 * Created by zhanghui on 2016/5/30.
 */
public class MediaObjectBuffer {

    public MediaObjectBuffer             (int maxcapacity){
        this.mediaobjects=new ArrayDeque<MediaObject>();
        this.eos=false;
        this.maxcapacity=maxcapacity;
    }

    public synchronized  void            Push    (MediaObject media){
        while(this.mediaobjects.size() >= this.maxcapacity && !this.eos){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        if(this.mediaobjects.size() >= this.maxcapacity)
        {
            return;
        }

        this.mediaobjects.add(media);

        notifyAll();
    }
    public synchronized MediaObject    Front   (){
        while(this.mediaobjects.size() == 0 && !this.eos){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        if(this.mediaobjects.size() == 0)
        {
            return null;
        }

        return this.mediaobjects.pollFirst();
    }

    public synchronized void            Pop     (){
        this.mediaobjects.pop();
        notifyAll();
    }

    public synchronized void            SetEOS  (boolean value){
        this.eos = value;
        notifyAll();
    }
    public synchronized int        Length  (){
        int ret = this.mediaobjects.size();
        return ret;
    }

    private ArrayDeque<MediaObject>   mediaobjects;
    private boolean                        eos;
    private int                    maxcapacity;
}
