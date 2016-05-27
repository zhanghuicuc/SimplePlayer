package com.zhanghui.mpd;

import com.zhanghui.helper.myString;

import java.util.InputMismatchException;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class SubRepresentation extends RepresentationBase implements ISubRepresentation{
    public SubRepresentation() {
        this.dependencyLevel = new Vector<Integer>();
        this.contentComponent = new Vector<String>();
    }

    @Override
    public int GetLevel() {
        return level;
    }

    public void SetLevel(int level) {
        this.level = level;
    }

    @Override
    public Vector<Integer> GetDependencyLevel() {
        return dependencyLevel;
    }

    public void SetDependencyLevel(String dependencyLevel) {
        myString.Split(dependencyLevel,this.dependencyLevel);
    }

    @Override
    public int GetBandWidth() {
        return bandWidth;
    }

    public void SetBandWidth(int bandWidth) {
        this.bandWidth = bandWidth;
    }

    @Override
    public Vector<String> GetContentComponent() {
        return contentComponent;
    }

    public void SetContentComponent(String contentComponent) {
        myString.Split(contentComponent," ",this.contentComponent);
    }

    protected int                    level;
    protected Vector<Integer> dependencyLevel;
    protected int                    bandWidth;
    protected Vector<String>    contentComponent;
}
