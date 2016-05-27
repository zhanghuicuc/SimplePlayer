package com.zhanghui.mpd;

/**
 * Created by zhanghui on 2016/5/17.
 */
public class ProgramInformation extends AbstractMPDElement implements IProgramInformation {
    public ProgramInformation() {
        title="";
        source="";
        copyright="";
        lang="";
        moreInformationURL="";
    }

    public String GetTitle() {
        return title;
    }

    public void SetTitle(String title) {
        this.title = title;
    }

    public String GetSource() {
        return source;
    }

    public void SetSource(String source) {
        this.source = source;
    }

    public String GetCopyright() {
        return copyright;
    }

    public void SetCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String GetLang() {
        return lang;
    }

    public void SetLang(String lang) {
        this.lang = lang;
    }

    public String GetMoreInformationURL() {
        return moreInformationURL;
    }

    public void SetMoreInformationURL(String moreInformationURL) {
        this.moreInformationURL = moreInformationURL;
    }

    private String title;
    private String source;
    private String copyright;
    private String lang;
    private String moreInformationURL;
}
