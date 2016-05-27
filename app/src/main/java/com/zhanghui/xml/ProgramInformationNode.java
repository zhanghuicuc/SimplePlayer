package com.zhanghui.xml;

import android.sax.Element;
import android.sax.ElementListener;
import android.sax.EndTextElementListener;

import com.zhanghui.mpd.MPD;
import com.zhanghui.mpd.ProgramInformation;

import org.xml.sax.Attributes;

/**
 * Created by zhanghui on 2016/5/20.
 */
public class ProgramInformationNode implements ElementListener {
    private static final String NAMESPACE = "urn:mpeg:DASH:schema:MPD:2011";
    MPD mpd;
    ProgramInformation programInformation;
    Element programinformation;

    public ProgramInformationNode(MPD mpd, Element programinformation) {
        this.mpd=mpd;
        this.programinformation=programinformation;
    }

    @Override
    public void start(Attributes attributes) {
        programInformation=new ProgramInformation();
        if (attributes.getIndex("lang") != -1) {
            programInformation.SetLang(attributes.getValue(attributes.getIndex("lang")));
        }
        if (attributes.getIndex("moreInformationURL") != -1) {
            programInformation.SetMoreInformationURL(attributes.getValue(attributes.getIndex("moreInformationURL")));
        }
        //Get ProgramInformation.Title
        Element title=programinformation.getChild(NAMESPACE,"Title");
        title.setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                programInformation.SetTitle(body);
            }
        });
        //Get ProgramInformation.Source
        Element source=programinformation.getChild(NAMESPACE,"Source");
        source.setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                programInformation.SetSource(body);
            }
        });
        //Get ProgramInformation.Copyright
        Element copyright=programinformation.getChild(NAMESPACE,"Copyright");
        copyright.setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                programInformation.SetCopyright(body);
            }
        });
    }

    @Override
    public void end() {
        mpd.AddProgramInformations(programInformation);

    }
}
