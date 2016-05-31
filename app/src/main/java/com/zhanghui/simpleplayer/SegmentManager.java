/*
 * SegmentManager类
 * Segment双端队列，队列中存储的是每个Segment的相对URL
 * - segments：private final ArrayDeque<String>
 * - baseURL: String
 *
 * + SegmentManager()
 * + setBaseURL(String baseURL)
 * + addSegment(String segmentRelativeURL)：boolean
 * + isEmpty()：boolean
 * + getSegment()：String
 * */

package com.zhanghui.simpleplayer;

import java.io.Serializable;
import java.util.ArrayDeque;

public class SegmentManager implements Serializable{
	private final ArrayDeque<String> segments = new ArrayDeque<String>();
	private final ArrayDeque<String> mediaRanges=new ArrayDeque<String>();
	private String baseURL;
	private String initURL;
    private String initRange;
    public String getInitRange() {
        return initRange;
    }

    public void setInitRange(String initRange) {
        this.initRange = initRange;
    }


	public String getInitURL() {
		return initURL;
	}

	public void setInitURL(String initURL) {
		this.initURL = initURL;
	}

	public SegmentManager() {
	}
	
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	
	public String getBaseURL() {
		return baseURL;
	}
	
	public boolean addSegment(String segmentRelativeURL) {
		if (segmentRelativeURL == null) {
			return false;
		}
		return segments.add(segmentRelativeURL);
	}

    public boolean addRanges(String mediaRange) {
        if (mediaRange == null) {
            return false;
        }
        return mediaRanges.add(mediaRange);
    }
	
	public boolean isEmpty() {
		return segments.isEmpty();
	}
	
	public String getSegment() {
		return segments.pollFirst();
	}

	public String getRange(){return mediaRanges.pollFirst();}

}
