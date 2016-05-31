package com.zhanghui.mpd;

import java.io.Serializable;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class Range implements IRange,Serializable {
    private String starttime;
    private String duration;

    public Range() {
        starttime="";
        duration="";
    }

    @Override
    public String GetStarttime() {
        return starttime;
    }

    public void SetStarttime(String starttime) {
        this.starttime = starttime;
    }

    @Override
    public String GetDuration() {
        return duration;
    }

    public void SetDuration(String duration) {
        this.duration = duration;
    }
}
