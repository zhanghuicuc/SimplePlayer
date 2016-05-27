package com.zhanghui.mpd;

import java.sql.Array;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface ISubRepresentation extends IRepresentationBase {
    /**
     *  Returns an integer that specifies the Sub-Representation level. If \c \@level attribute is present and for media formats used in this Part of ISO/IEC 23009,
     *  a Subsegment Index as defined in section 6.3.2.4 of <em>ISO/IEC 23009-1, Part 1, 2012</em> shall be available for each Media Segment in the containing Representation.
     *  @return     an unsigned integer
     */
    public int                            GetLevel            ();

    /**
     *  Returns a reference to a vector of unsigned integers that specifies the set of Sub-Representations within this Representation that this Sub-Representation depends on
     *  in the decoding and/or presentation process as a list of \c \@level values.\n
     *  If not present, the Sub-Representation can be decoded and presented independently of any other Representation.
     *  @return     a reference to a vector of unsigned integers
     */
    public Vector<Integer> GetDependencyLevel  ();

    /**
     *  Returns an integer that is identical to the \c \@bandwidth definition in Representation, but applied to this Sub-Representation.
     *  This attribute shall be present if the \c \@level attribute is present.
     *  @return     an unsigned integer
     */
    public int                            GetBandWidth        ();

    /**
     *  Returns a reference to a vector of strings that specifies the set of all media content components 
     *  that are contained in this Sub-Representation as <tt><b>ContentComponent</b>\@id</tt> values. \n
     *  If not present, the Sub-Representation is not assigned to a media content component. 
     *  @return     a reference to a vector of strings
     */
    public Vector<String> GetContentComponent ();
}
