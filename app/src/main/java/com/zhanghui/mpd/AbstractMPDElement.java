package com.zhanghui.mpd;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by zhanghui on 2016/5/17.
 */
public abstract class AbstractMPDElement implements IMPDElement{
    public AbstractMPDElement() {
    }

    public Vector<Node> GetAdditionalSubNodes() {
        return AdditionalSubNodes;
    }

    public void SetAdditionalSubNodes(Node additionalSubNode) {
        this.AdditionalSubNodes.add(additionalSubNode);
    }

    private Vector<Node> AdditionalSubNodes;

    public HashMap<String, String> GetRawAttributes() {
        return rawAttributes;
    }

    public void SetRawAttributes(HashMap<String, String> rawAttributes) {
        this.rawAttributes = rawAttributes;
    }

    private HashMap<String, String>  rawAttributes;
}
