package com.zhanghui.mpd;

import java.sql.Array;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public interface ISubset extends IMPDElement {
    /**
     *  Returns a reference to a vector of unsigned integers specifying the Adaptation Sets contained in a Subset by providing
     *  the \c \@id values of the contained Adaptation Sets.
     *  @return     a reference to a vector of unsigned integers
     */
    public Vector<Integer> GetContains ();

    /**
     *  Returns a reference to a string that specifies a unique identifier for this Subset.
     */
    public String				GetId();
}
