package com.zhanghui.mpd;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IProgramInformation extends IMPDElement {
    /**
     *  Returns a reference to a string that specifies the title for the Media Presentation.
     *  @return     a reference to a string
     */
    public String  GetTitle                ();

    /**
     *  Returns a reference to a string that specifies information about the original source (for example content provider) of the Media Presentation.
     *  @return     a reference to a string
     */
    public String  GetSource               ();

    /**
     *  Returns a reference to a string that specifies a copyright statement for the Media Presentation, usually starting with the copyright symbol, unicode \c U+00A9.
     *  @return     a reference to a string
     */
    public String  GetCopyright            ();

    /**
     *  Returns a reference to a string that declares the language code(s) for this Program Information. The syntax and semantics according to <em>IETF RFC 5646</em> shall be applied.
     *  @return     a reference to a string
     */
    public String  GetLang                 ();

    /**
     *  Returns a reference to a string that specifies an absolute URL which provides more information about the Media Presentation.
     *  @return     a reference to a string
     */
    public String  GetMoreInformationURL   ();
}
