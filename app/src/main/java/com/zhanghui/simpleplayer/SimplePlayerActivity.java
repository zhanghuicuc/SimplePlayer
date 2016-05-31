package com.zhanghui.simpleplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.VideoView;

import com.zhanghui.mpd.BaseUrl;
import com.zhanghui.mpd.IRepresentation;
import com.zhanghui.mpd.ISegment;
import com.zhanghui.mpd.MPD;
import com.zhanghui.mpd.Representation;
import com.zhanghui.mpd.SegmentBase;
import com.zhanghui.mpd.URLType;

import java.util.Vector;

public class SimplePlayerActivity extends Activity {
    //String mpdurl="http://222.31.64.171/DASH/BigBuckBunnyNonSeg_1s.mpd";
    String mpdurl="http://222.31.64.171/DASH/bunny_2s_480p_only/bunny_Desktop.mpd";
    //String mpdurl="http://www-itec.uni-klu.ac.at/ftp/datasets/mmsys12/BigBuckBunny/MPDs/BigBuckBunnyNonSeg_2s_isoffmain_DIS_23009_1_v_2_1c2_2011_08_30.mpd";
    //String mpdurl="http://www-itec.uni-klu.ac.at/ftp/datasets/mmsys12/BigBuckBunny/bunny_1s/BigBuckBunnyNonSeg_1s_isoffmain_DIS_23009_1_v_2_1c2_2011_08_30.mpd";
    int maxcapacity=15;
    MediaObjectBuffer   buffer=new MediaObjectBuffer(this.maxcapacity);
    MPDParser parser;
    private VideoView videoView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_player);

        parser  = new MPDParser(mpdurl);

		((Button) findViewById(R.id.button_parse)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				parser.parse();
			}
		});

		((Button) findViewById(R.id.button_play)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), VideoPlayer.class);
                //startActivity(intent);
                DoBuffering bufferingThread=new DoBuffering();
                bufferingThread.start();
                videoView=(VideoView)findViewById(R.id.videoView2);
                Play();
                //videoView.setVideoPath("sth");
                //if(!videoView.isPlaying())
                //    videoView.start();
			}
		});
	}

    public synchronized void Play(){
        while(buffer.Length()<4){
            try{
                wait();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        while(buffer.Length()>0){
            if(!videoView.isPlaying()){
                String path=buffer.Front().GetBufferfile().getAbsolutePath();
                videoView.setVideoPath(path);
                if(!videoView.isPlaying())
                    videoView.start();
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        buffer.Pop();
                    }
                });
            }
        }

    }

    class DoBuffering extends Thread{
        int number = 0;
        double download_time = 0;
        Representation lowestRep = parser.GetMpd().GetPeriods().elementAt(0).GetAdaptationSets().elementAt(0).GetRepresentation().elementAt(0);
        com.zhanghui.mpd.Segment seg = lowestRep.GetSegmentBase().GetInitialization().ToSegment(parser.GetMpd().GetBaseUrls());
        MediaObject media = new MediaObject(seg, lowestRep);

        public void run(){
            while(media != null)
            {
                media.StartDownload();

                media.WaitFinished();

                buffer.Push(media);
                number++;

                download_time = media.GetLastDownloadTime();

                if (download_time < 0)
                    download_time = 0;

                if (number >= lowestRep.GetSegmentList().GetSegmentURLs().size() + 1)
                    break;
                seg=lowestRep.GetSegmentList().GetSegmentURLs().elementAt(number - 1).ToMediaSegment(parser.GetMpd().GetBaseUrls());
                media = new MediaObject(seg, lowestRep);
            }
            buffer.SetEOS(true);
            return;
        }
    }

    protected void onDestroy(){
    super.onDestroy();
    if(videoView!=null)
        videoView.suspend();
    }
}