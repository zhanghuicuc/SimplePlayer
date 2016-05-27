package com.zhanghui.simpleplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;

public class SimplePlayerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_player);
		//final MPDParser parser = new MPDParser("http://www-itec.uni-klu.ac.at/ftp/datasets/mmsys12/BigBuckBunny/MPDs/BigBuckBunnyNonSeg_2s_isoffmain_DIS_23009_1_v_2_1c2_2011_08_30.mpd");
        final MPDParser parser = new MPDParser("http://222.31.64.171/DASH/BigBuckBunnyNonSeg_1s.mpd");
        //final MPDParser parser = new MPDParser("http://www-itec.uni-klu.ac.at/ftp/datasets/mmsys12/BigBuckBunny/bunny_1s/BigBuckBunnyNonSeg_1s_isoffmain_DIS_23009_1_v_2_1c2_2011_08_30.mpd");
		((Button) findViewById(R.id.button_helloworld)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				parser.parse();
			}
		});
		((Button) findViewById(R.id.button_helloworld)).setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				startActivity(new Intent(getApplicationContext(), VideoPlayer.class));
				return true;
			}
		});
	}
}