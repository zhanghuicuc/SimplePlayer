package com.zhanghui.mpd;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/19.
 */
public class ContentComponent extends AbstractMPDElement implements IContentComponent,Serializable {
    public ContentComponent() {
        id=0;
        lang="";
        contentType="";
        par="";
        accessibility=new Vector<Descriptor>();
        role=new Vector<Descriptor>();
        rating=new Vector<Descriptor>();
        viewpoint=new Vector<Descriptor>();
    }

    @Override
    public Vector<Descriptor> GetAccessibility() {
        return accessibility;
    }

    public void AddAccessibility(Descriptor accessibility) {
        this.accessibility.add(accessibility);
    }

    @Override
    public Vector<Descriptor> GetRole() {
        return role;
    }

    public void AddRole(Descriptor role) {
        this.role.add(role);
    }

    @Override
    public Vector<Descriptor> GetRating() {
        return rating;
    }

    public void AddRating(Descriptor rating) {
        this.rating.add(rating);
    }

    @Override
    public Vector<Descriptor> GetViewpoint() {
        return viewpoint;
    }

    public void AddViewpoint(Descriptor viewpoint) {
        this.viewpoint.add(viewpoint);
    }

    @Override
    public int GetId() {
        return id;
    }

    public void SetId(int id) {
        this.id = id;
    }

    @Override
    public String GetLang() {
        return lang;
    }

    public void SetLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String GetContentType() {
        return contentType;
    }

    public void SetContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String GetPar() {
        return par;
    }

    public void SetPar(String par) {
        this.par = par;
    }

    private Vector<Descriptor> accessibility;
    private Vector<Descriptor>   role;
    private Vector<Descriptor>   rating;
    private Vector<Descriptor>   viewpoint;
    private int                    id;
    private String                 lang;
    private String                 contentType;
    private String                 par;
}
