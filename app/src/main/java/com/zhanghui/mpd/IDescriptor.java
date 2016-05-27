package com.zhanghui.mpd;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface IDescriptor extends IMPDElement {
    /**
     *  Returns a reference to a string that specifies a URI to identify the scheme. \n
     *  The semantics of this element are specific to the scheme specified by this attribute.
     *  The \c \@schemeIdUri may be a URN or URL. When a URL is used, it should also contain a month-date in the form
     *  mmyyyy; the assignment of the URL must have been authorized by the owner of the domain name in that URL on
     *  or very close to that date, to avoid problems when domain names change ownership.
     *  @return     a reference to a string
     */
    public String      GetSchemeIdUri  ();

    /**
     *  Returns a reference to a string that specifies the value for the descriptor element. \n
     *  The value space and semantics must be defined by the owners of the scheme identified in the \c \@schemeIdUri attribute.
     *  @return     a reference to a string
     */
    public String      GetValue        ();

    /**
     *  Returns a reference to a string that specifies an identifier for the descriptor. \n
     *  Descriptors with identical values for this attribute shall be synonymous, i.e. the processing of one of the descriptors with an identical \c \@value is sufficient.
     *  @return     a reference to a string
     */
    public String      GetId();
}
