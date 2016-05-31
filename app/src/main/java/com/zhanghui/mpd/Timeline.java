package com.zhanghui.mpd;

import java.io.Serializable;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class Timeline extends AbstractMPDElement implements ITimeline,Serializable {
    private int startTime;
    private int duration;
    private int repeatCount;

    public Timeline() {
        startTime=0;
        duration=0;
        repeatCount=0;
    }

    @Override
    public int GetStartTime() {
        return startTime;
    }

    public void SetStartTime(int startTime) {
        this.startTime = startTime;
    }

    @Override
    public int GetDuration() {
        return duration;
    }

    public void SetDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int GetRepeatCount() {
        return repeatCount;
    }

    public void SetRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }
}
