package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;

import com.zhanghui.mpd.AdaptationSet;
import com.zhanghui.mpd.ContentComponent;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/21.
 */
public class ContentComponentNode implements ElementListener {
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";
    AdaptationSet adaptationSet;
    ContentComponent contentComponent;
    Element contentcomponent;
    public ContentComponentNode(AdaptationSet adaptationSet,Element contentcomponent) {
        this.adaptationSet=adaptationSet;
        this.contentcomponent=contentcomponent;
    }

    public void start(Attributes attributes) {
        contentComponent=new ContentComponent();
        if (attributes.getIndex("id") != -1) {
            contentComponent.SetId(Integer.parseInt(attributes.getValue(attributes.getIndex("id"))));
        }
        if (attributes.getIndex("lang") != -1) {
            contentComponent.SetLang(attributes.getValue(attributes.getIndex("lang")));
        }
        if (attributes.getIndex("contentType") != -1) {
            contentComponent.SetContentType(attributes.getValue(attributes.getIndex("contentType")));
        }
        if (attributes.getIndex("par") != -1) {
            contentComponent.SetPar(attributes.getValue(attributes.getIndex("par")));
        }
        //Get ContentComponent.Accessibility
        Element accessibility=contentcomponent.getChild(NAMESPACE,"Accessibility");
        DescriptorNode toAccessibility=new DescriptorNode(null,contentComponent,null,null,null,"Accessibility");
        accessibility.setElementListener(toAccessibility);
        //Get ContentComponent.Role
        Element role=contentcomponent.getChild(NAMESPACE,"Role");
        DescriptorNode toRole=new DescriptorNode(null,contentComponent,null,null,null,"Role");
        accessibility.setElementListener(toRole);
        //Get ContentComponent.Rating
        Element rating=contentcomponent.getChild(NAMESPACE,"Rating");
        DescriptorNode toRating=new DescriptorNode(null,contentComponent,null,null,null,"Rating");
        accessibility.setElementListener(toRating);
        //Get ContentComponent.Viewpoint
        Element viewpoint=contentcomponent.getChild(NAMESPACE,"Viewpoint");
        DescriptorNode toViewpoint=new DescriptorNode(null,contentComponent,null,null,null,"Viewpoint");
        accessibility.setElementListener(toViewpoint);
    }

    public void end() {

        adaptationSet.AddContentComponent(contentComponent);

    }
}
