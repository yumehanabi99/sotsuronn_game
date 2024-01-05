package com.kumori.koumokug;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StudyActivity extends Activity {

	String word[] = MainActivity.word;
	String wordx[] = MainActivity.wordx;
	String imi[] = MainActivity.imi;
	String yomikata[] = MainActivity.yomikata;
	String r[][] = MainActivity.r;
	String z[][] = MainActivity.z;

	List<String> wblist = new ArrayList<String>();
	List<String> delword = new ArrayList<String>();
	List<String> delid = new ArrayList<String>();
	Button[] button;
	LinearLayout linear1, linear2, linear3;
	TextView t1, t2, t3, t4, rei, t5, imir;
	OnClickListener c1, c2, c3, c4,c;

	int ran = 0, soundi = 99;
	SoundPool soundpool;
	HashMap<Integer, Integer> soundmap = new HashMap<Integer, Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.study);

		CloseActivityReceiver closeReceiver = new CloseActivityReceiver();
		IntentFilter intentFilter = new IntentFilter("com.kumori.koumokug.StudyActivity");
		registerReceiver(closeReceiver, intentFilter);

		ran = new Random().nextInt(3);

		if (soundpool == null) {
			// 5.0 �� ֮��
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
				AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
						.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
				soundpool = new SoundPool.Builder().setMaxStreams(16).setAudioAttributes(audioAttributes).build();
			} else { // 5.0 ��ǰ
				soundpool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0); // ����SoundPool
			}
		}

		// ��Ҫ���ŵ���Ƶ�����浽HashMap������
		soundmap.put(1, soundpool.load(this, R.raw.kaku, 1));
		soundmap.put(2, soundpool.load(this, R.raw.isogu, 1));
		soundmap.put(3, soundpool.load(this, R.raw.migaku, 1));
		soundmap.put(4, soundpool.load(this, R.raw.hanasu, 1));
		soundmap.put(5, soundpool.load(this, R.raw.suru, 1));
		soundmap.put(6, soundpool.load(this, R.raw.bennkyousuru, 1));
		soundmap.put(7, soundpool.load(this, R.raw.matsu, 1));
		soundmap.put(8, soundpool.load(this, R.raw.kaeru, 1));
		soundmap.put(9, soundpool.load(this, R.raw.utau, 1));
		soundmap.put(10, soundpool.load(this, R.raw.taberu, 1));
		soundmap.put(11, soundpool.load(this, R.raw.okiru, 1));
		soundmap.put(12, soundpool.load(this, R.raw.kuru, 1));
		soundmap.put(13, soundpool.load(this, R.raw.asobu, 1));
		soundmap.put(14, soundpool.load(this, R.raw.nomu, 1));
		soundmap.put(15, soundpool.load(this, R.raw.shinu, 1));
		soundmap.put(16, soundpool.load(this, R.raw.iku, 1));

		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		t1 = (TextView) findViewById(R.id.t1);
		t2 = (TextView) findViewById(R.id.t2);
		t3 = (TextView) findViewById(R.id.t3);
		t4 = (TextView) findViewById(R.id.t4);
		t5 = (TextView) findViewById(R.id.t5);
		rei = (TextView) findViewById(R.id.rei);
		imir = (TextView) findViewById(R.id.imir);
		linear1.setPadding(50, 50, 50, 50);

		// ��ȡ���Ȿ
		try {
			File wb = new File(Environment.getExternalStorageDirectory(), "wb002");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(wb)));
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				wblist.add(lineTxt);
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		ImageView sound = (ImageView) findViewById(R.id.sound);
		OnClickListener soundc = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				if (soundi != 99) {
					soundpool.play(soundmap.get(soundi + 1), 1, 1, 0, 0, 1);
				}
			}
		};
		sound.setOnClickListener(soundc);

		OnClickListener c1 = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				
				Intent intent = new Intent(StudyActivity.this, GameActivity.class);
				startActivity(intent);
				
				Intent intent2 = new Intent();
				intent2.setAction("com.kumori.koumokug.StudyActivity");
				sendBroadcast(intent2);

			}
		};
		if(MainActivity.wrongMode==1) {
			t5.setText("����Ϸ��Ϊ�����ؿ�"+"\n"+"ÿ���ؿ���ʼ֮ǰ�������ʱ�临ϰ����"+"\n"+"��ĳ�ؿ���ȷ�ʵ���60%ʱ���ܽ�����һ��");
			Button b1 = new Button(this);
			b1.setText("������Ϸ");
			b1.setBackgroundResource(R.drawable.button_shape);
			b1.setTextColor(android.graphics.Color.parseColor("#1cb0f6"));
			linear1.addView(b1);
			setMargins(b1, 0, 15, 0, 15);
			b1.setOnClickListener(c1);
		}else {
			wrongBook();
		}
		
		c = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������\
				Button b = (Button) v;
				for (int i = 0; i < word.length; i++) {
					if (word[i].equals(b.getText().toString())) {
						t1.setText(wordx[i]);
						t2.setText(yomikata[i]);
						t5.setText(imi[i]);
						soundi = i;
						t3.setText(r[i][ran]);
						t4.setText(z[i][ran]);
						if (ran < 2) {
							ran++;
						} else {
							ran = 0;
						}
					}
				}
				rei.setText("����");
				imir.setText("��ζ");
			}
		};
		c4 = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent2 = new Intent();
				intent2.setAction("com.kumori.koumokug.StudyActivity");
				sendBroadcast(intent2);
			}
		};

		// ��ӵ��ʰ�ť
		int len=0;
		if(MainActivity.step==1&&MainActivity.wrongMode==1) {
			len=word.length;
		}else {
			t1.setText("�ٿ�һ�����Ĵʰ�~");
			t5.setText("");
			len=wblist.size();
		}
		button = new Button[len];
		for (int i = 0; i < len; i++) {
			button[i] = new Button(this);
			
			if(MainActivity.step==1&&MainActivity.wrongMode==1) {
				button[i].setText(word[i]);
			}else {
				button[i].setText(wblist.get(i));
				button[i].setId(i);
			}
			
			button[i].setOnClickListener(c);
			button[i].setBackgroundResource(R.drawable.button_shape);
			button[i].setTextColor(android.graphics.Color.parseColor("#1cb0f6"));
			linear1.addView(button[i]);
		}

		for (int i = 0; i < button.length; i++) {
			setMargins(button[i], 0, 15, 0, 15);
		}
	}

	void wrongBook() {
		t1.setText("");
		t2.setText("");
		imir.setText("");
		t5.setText("");
		
		Button b1 = new Button(this);
		final Button b2 = new Button(this);
		b1.setText("�༭");
		b2.setText("����");
		
		final OnClickListener d = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Button b = (Button) v;
				delword.add(b.getText().toString());
				delid.add(b.getId() + "");
				linear1.removeView(b);
			}
		};
		
		c1 = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Button b = (Button) v;
				b.setText("��ɱ༭");
				b2.setText("����ɾ��");
				b.setOnClickListener(c2);
				b2.setOnClickListener(c3);
				for (int i = 0; i < button.length; i++) {
					button[i].setOnClickListener(d);
				}

			}
		};
		c2 = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Button b = (Button) v;
				b.setText("�༭");
				b2.setText("����");
				b.setOnClickListener(c1);
				b2.setOnClickListener(c4);
				for (int i = 0; i < button.length; i++) {
					button[i].setOnClickListener(c);
				}
				// д���ļ�
				wblist.removeAll(delword);
				File wb = new File(Environment.getExternalStorageDirectory(), "wb002");
				BufferedWriter bw;
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					try {
						bw = new BufferedWriter(new FileWriter(wb, false));
						String s = "";
						for (int i = 0; i < wblist.size(); i++) {
							s = s + wblist.get(i) + "\n";
						}
						bw.write(s);
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				delword.clear();
				delid.clear();
			}
		};
		c3 = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				if (delword.size() > 0 && delid.size() > 0) {
					linear1.addView(button[Integer.valueOf(delid.get(delid.size() - 1))]);

					Iterator<String> iterator1 = delid.iterator();
					while (iterator1.hasNext()) {
						String next = iterator1.next();
						if (delid.get(delid.size() - 1).equals(next)) {
							iterator1.remove();
						}
					}
					Iterator<String> iterator2 = delword.iterator();
					while (iterator2.hasNext()) {
						String next = iterator2.next();
						if (delword.get(delword.size() - 1).equals(next)) {
							iterator2.remove();
						}
					}

				}
				Intent intent2 = new Intent();
				intent2.setAction("com.kumori.koumokug.StudyActivity");
				sendBroadcast(intent2);
			}
		};
		b1.setOnClickListener(c1);
		b2.setOnClickListener(c4);
		b1.setBackgroundResource(R.drawable.button_shape);
		b2.setBackgroundResource(R.drawable.button_shape);
		b1.setTextColor(android.graphics.Color.parseColor("#1cb0f6"));
		b2.setTextColor(android.graphics.Color.parseColor("#1cb0f6"));
		linear1.addView(b1);
		linear1.addView(b2);
		setMargins(b1, 0, 15, 0, 15);
		setMargins(b2, 0, 15, 0, 15);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent2 = new Intent();
			intent2.setAction("com.kumori.koumokug.StudyActivity");
			sendBroadcast(intent2);
		}
		return super.onKeyDown(keyCode, event);
	}

	public static void setMargins(View v, int l, int t, int r, int b) {
		if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
			p.setMargins(l, t, r, b);
			v.requestLayout();
		}
	}

	public class CloseActivityReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			StudyActivity.this.finish();
		}
	}
}
