package com.zhanghui.mpd;

import org.w3c.dom.Node;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/16.
 */
public interface IMPDElement {
    /**
     *  This method returns a vector of pointers to dash::xml::INode objects which correspond to additional <em>XML Elements</em> of certain
     *  MPD elements. These <em>XML Elements</em> are not specified in <em>ISO/IEC 23009-1, Part 1, 2012</em>. \n
     *  See the example in the class description for details.
     *  @return     a vector of pointers to dash::xml::INode objects
     */
    public Vector<Node> GetAdditionalSubNodes();

    /**
     *  This method returns a map with key values and mapped values of type std::string of all <em>XML Attributes</em> of certain MPD elements. \n
     *  Some of these <em>XML Attributes</em> are not specified in <em>ISO/IEC 23009-1, Part 1, 2012</em>. \n
     *  See the example in the class description for details.
     *  @return     a map with key values and mapped values, both of type std::string
     */
    public HashMap<String, String> GetRawAttributes();
}
