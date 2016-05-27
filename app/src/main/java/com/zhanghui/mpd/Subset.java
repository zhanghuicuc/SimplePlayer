package com.zhanghui.mpd;

import com.zhanghui.helper.myString;

import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class Subset extends AbstractMPDElement implements ISubset {
    private Vector<Integer> contains;
    private String id;

    public Subset() {
        contains=new Vector<Integer>();
    }

    @Override
    public Vector<Integer> GetContains() {
        return contains;
    }

    public void SetContains(String contains) {
        myString.Split(contains,this.contains);
    }

    @Override
    public String GetId() {
        return id;
    }

    public void SetId(String id) {
        this.id = id;
    }
}
