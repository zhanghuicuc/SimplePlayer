package com.zhanghui.mpd;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IContentComponent extends IMPDElement {
    /**
     *  @copydoc    dash::mpd::IAdaptationSet::GetAccessibility()
     */
    public Vector<Descriptor> GetAccessibility    ();

    /**
     *  @copydoc    dash::mpd::IAdaptationSet::GetRole()
     */
    public Vector<Descriptor>   GetRole             ();

    /**
     *  @copydoc    dash::mpd::IAdaptationSet::GetRating()
     */
    public Vector<Descriptor>   GetRating           ();

    /**
     *  @copydoc    dash::mpd::IAdaptationSet::GetViewpoint()
     */
    public Vector<Descriptor>   GetViewpoint        ();

    /**
     *  Returns an unsigned integer that specifies an identifier for this media component.
     *  The attribute shall be unique in the scope of the containing Adaptation Set.
     *  @return     an unsigned integer
     */
    public int                            GetId               ();

    /**
     *  @copydoc    dash::mpd::IAdaptationSet::GetLang()
     */
    public String                  GetLang             ();

    /**
     *  @copydoc    dash::mpd::IAdaptationSet::GetContentType()
     */
    public String                  GetContentType      ();

    /**
     *  @copydoc    dash::mpd::IAdaptationSet::GetPar()
     */
    public String                  GetPar              ();
}
