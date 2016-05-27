/*
 * Segment类
 * 包含
 * - id：int
 * - startByte起始字节，用于byte-range request:int
 * - endByte终止字节，用于byte-range request:int
 * - content内容:InputStream
 * 
 * + Segment(int id, int startByte, int endByte)
 * + getContent():InputStream
 * + setContent(InputStream content)
 * + getId():int
 * + getStartByte():int
 * + getEndByte():int
 * */
package com.zhanghui.simpleplayer;

import java.io.InputStream;

public class Segment {
	private int id;
	private int startByte;
	private int endByte;
	/*
	 * 面向字节的输入流都是InputStream类的子类
	 */
	private InputStream content;
	
	public Segment(int id, int startByte, int endByte) {
		this.id = id;
		this.startByte = startByte;
		this.endByte = endByte;
	}

	public InputStream getContent() {
		return content;
	}

	public void setContent(InputStream content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public int getStartByte() {
		return startByte;
	}

	public int getEndByte() {
		return endByte;
	}
}
