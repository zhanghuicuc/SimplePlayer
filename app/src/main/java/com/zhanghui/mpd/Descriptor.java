package com.zhanghui.mpd;

import java.io.Serializable;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class Descriptor extends AbstractMPDElement implements IDescriptor,Serializable {
    private String schemeIdUri;
    private String value;
    private String id;

    @Override
    public String GetSchemeIdUri() {
        return schemeIdUri;
    }

    public void SetSchemeIdUri(String schemeIdUri) {
        this.schemeIdUri = schemeIdUri;
    }

    @Override
    public String GetValue() {
        return value;
    }

    public void SetValue(String value) {
        this.value = value;
    }

    @Override
    public String GetId() {
        return id;
    }

    public void SetId(String id) {
        this.id = id;
    }
}
