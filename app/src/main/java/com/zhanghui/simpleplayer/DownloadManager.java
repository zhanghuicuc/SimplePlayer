/*
 * DownloadManager类
 * 下载管理
 * - baseURL：String
 * - listener：DownloadManagerCallback
 *
 * + DownloadManager(DownloadManagerCallback listener, String baseURL)
 * + downloadSegmentContent(Segment segment)开启线程下载segment
 * - retreiveSegmentContent(String specificURL, int startByte, int endByte)下载内容写入segment.content
 *
 * - DownloadThread类
 * 下载线程
 * 		- segment：Segment
 * 		+ DownloadThread(Segment segment)
 * 		+ run()开始下载，先获取segment.content，再调用回调函数进行下一步处理（播放等）
 * */
package com.zhanghui.simpleplayer;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class DownloadManager {
	/*
	 * 在 Java 中，声明类、变量和方法时，可使用关键字 final 来修饰。final 所修饰的数据具有“终态”的特征，表示“最终的”意思。
	 * 具体规定如下：
	 * final 修饰的类不能被继承。
	 * final 修饰的方法不能被子类重写。
	 * final 修饰的变量（成员变量或局部变量）即成为常量，只能赋值一次。
	 * final 修饰的成员变量必须在声明的同时赋值，如果在声明的时候没有赋值，那么只有一次赋值的机会，
	 * 而且只能在构造方法中显式赋值，然后才能使用。
	 * final 修饰的局部变量可以只声明不赋值，然后再进行一次性的赋值。
	 *
	 * final 一般用于修饰那些通用性的功能、实现方式或取值不能随意被改变的数据，以避免被误用
	 * */
	private static final String TAG = DownloadManager.class.getSimpleName();
	
	private String baseURL;
	private DownloadManagerCallback listener;
	
	public DownloadManager(DownloadManagerCallback listener, String baseURL) {
		this.listener = listener;
		this.baseURL = baseURL;
	}
	
	public void downloadSegmentContent(Segment segment) {
		new DownloadThread(segment).start();
	}
	
	private InputStream retreiveSegmentContent(String specificURL, int startByte, int endByte) {
		HttpURLConnection httpURLConnection = null;
		InputStream content = null;
		try {
			//根据URL地址实例化一个URL对象，用于创建HttpURLConnection对象。
			URL url = new URL(baseURL+specificURL);
			if(url != null){
				//openConnection获得当前URL的连接
				httpURLConnection=(HttpURLConnection)url.openConnection();
				//设置3秒的响应超时
				httpURLConnection.setConnectTimeout(3000);
				//设置允许输入
				httpURLConnection.setDoInput(true);
				//设置为GET方式请求数据
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.setRequestProperty("Range","bytes=" + startByte + "-" + endByte);
				//获取连接响应码，200为成功，如果为其他，均表示有问题
				int responseCode=httpURLConnection.getResponseCode();
				Log.d(TAG, "Status code: " + responseCode);
				//if(responseCode==200){
					//getInputStream获取服务端返回的数据流。
					content=httpURLConnection.getInputStream();
				//}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	private class DownloadThread extends Thread {
		
		private Segment segment;
		
		public DownloadThread(Segment segment) {
			this.segment = segment;
		}
		
		@Override
		public void run() {
			segment.setContent(retreiveSegmentContent("bunny_1s_300kbit/bunny_300kbit_dashNonSeg.mp4", segment.getStartByte(), segment.getEndByte()));
			listener.segmentRetreived(segment);
		}
	}
}
