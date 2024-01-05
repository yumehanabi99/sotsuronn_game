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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends Activity {

	int i = 0, j = 0, k = 0, total = 0, rightnohit = 0, hardLevel = 1, gameTime = 600, sleepTime = 3000; // k分数,i正确击中次数

	static ImageView mouse1, mouse2, mouse3, mouse4, mouse5, mouse6, mouse7, mouse8, mouse9;
	static TextView t1, t2, t3, t4, t5, t6;

	TextView score, hit, showGameTime, hanndann;

	int displayWidth, displayHeight;
	final int[] tf = { 0, 1 };// 正确0,错误1
	Handler handlerTime;

	static int x1 = 0, x2 = 0, x3 = 0, y1 = 0, y2 = 0, y3 = 0, temp1 = 0, temp2 = 0, temp3 = 0, tempx1 = 0, tempx2 = 0,
			tempx3 = 0, tempy1 = 0, tempy2 = 0, tempy3 = 0, iWordM_1, iWordM_2, iWordM_3, iWordB_1, iWordB_2, iWordB_3,
			ifBatsu1, ifBatsu2, ifBatsu3, sound_1, sound_2, sound_3;

	static Thread th1, th2, th3, time;

	String wordString[] = { "書く", "急ぐ", "磨く", "話す", "する", "勉強する", "待つ", "帰る", "歌う", "食べる", "起きる", "来る", "遊ぶ", "飲む",
			"死ぬ", "行く" };
	String[] maruString = { "書いて", "急いで", "磨いて", "話して", "して", "勉強して", "待って", "帰って", "歌って", "食べて", "起きて", "来て", "遊んで",
			"飲んで", "死んで", "行って" };
	String[][] batsuString = { { "書いで", "書して", "書って", "書て", "書んで" }, { "急いて", "急して", "急って", "急て", "急んで" },
			{ "磨いで", "磨して", "磨って", "磨て", "磨んで" }, { "話いて", "話いで", "話って", "話て", "話んで" }, { "いて", "いで", "って", "て", "んで" },
			{ "勉強いて", "勉強いで", "勉強って", "勉強て", "勉強んで" }, { "待いて", "待いで", "待して", "待て", "待んで" },
			{ "帰いて", "帰いで", "帰して", "帰て", "帰んで" }, { "歌いて", "歌いで", "歌して", "歌て", "歌んで" },
			{ "食べいて", "食べいで", "食べして", "食べって", "食べんで" }, { "起きいて", "起きいで", "起きして", "起きって", "起きんで" },
			{ "来いて", "来いで", "来して", "来って", "来んで" }, { "遊いて", "遊いで", "遊して", "遊って", "遊て" },
			{ "飲いて", "飲いで", "飲して", "飲って", "飲て" }, { "死いて", "死いで", "死して", "死って", "死て" },
			{ "行いて", "行いで", "行して", "行て", "行んで" } };
	static boolean th1_ctrl = true, th2_ctrl = true, th3_ctrl = true, time_ctrl = true;
	SoundPool soundpool;
	HashMap<Integer, Integer> soundmap = new HashMap<Integer, Integer>();
	List<String> right = new ArrayList<String>();
	List<String> wronghit = new ArrayList<String>();
	List<String> wrongnohit = new ArrayList<String>();
	List<String> wblist = new ArrayList<String>();
	List<String> wrong = new ArrayList<String>();
	List<String> wbliste = new ArrayList<String>();
	MediaPlayer mediaplayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		CloseActivityReceiver closeReceiver = new CloseActivityReceiver();
		IntentFilter intentFilter = new IntentFilter("com.kumori.koumokug.GameActivity");
		registerReceiver(closeReceiver, intentFilter);

		// 读取错题本
		try {
			File wb = new File(Environment.getExternalStorageDirectory(), "wb002");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(wb)));
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				wbliste.add(lineTxt);
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// 获取手机显示屏大小
		DisplayMetrics dm = getResources().getDisplayMetrics();
		displayWidth = dm.widthPixels - 100;
		displayHeight = dm.heightPixels - 120;

		// 加载音频资源
		mediaplayer = MediaPlayer.create(this, R.raw.bgm);
		if (soundpool == null) {
			// 5.0 及 之后
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
				AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
						.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
				soundpool = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(audioAttributes).build();
			} else { // 5.0 以前
				soundpool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0); // 创建SoundPool
			}
		}

		// 将要播放的音频流保存到HashMap对象中
		soundmap.put(1, soundpool.load(this, R.raw.up, 1));
		soundmap.put(2, soundpool.load(this, R.raw.hit, 1));
		soundmap.put(3, soundpool.load(this, R.raw.click, 1));

		final ImageButton start = (ImageButton) findViewById(R.id.start);
		final Button messagebutton = (Button) findViewById(R.id.messagebutton);
		messagebutton.setText("第 " + MainActivity.step + " 关" + "\n" + "打动词变形出错的地鼠" + "\n" + "连续正确击中难度将上升");

		final LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
		linear.setVisibility(View.INVISIBLE);

		mouse1 = (ImageView) findViewById(R.id.imageView1);
		mouse2 = (ImageView) findViewById(R.id.imageView2);
		mouse3 = (ImageView) findViewById(R.id.imageView3);
		mouse1.setVisibility(View.INVISIBLE);
		mouse2.setVisibility(View.INVISIBLE);
		mouse3.setVisibility(View.INVISIBLE);

		mouse4 = (ImageView) findViewById(R.id.imageView4);
		mouse5 = (ImageView) findViewById(R.id.imageView5);
		mouse6 = (ImageView) findViewById(R.id.imageView6);
		mouse4.setVisibility(View.INVISIBLE);
		mouse5.setVisibility(View.INVISIBLE);
		mouse6.setVisibility(View.INVISIBLE);

		mouse7 = (ImageView) findViewById(R.id.imageView7);
		mouse8 = (ImageView) findViewById(R.id.imageView8);
		mouse9 = (ImageView) findViewById(R.id.imageView9);
		mouse7.setVisibility(View.INVISIBLE);
		mouse8.setVisibility(View.INVISIBLE);
		mouse9.setVisibility(View.INVISIBLE);

		t1 = (TextView) findViewById(R.id.textView1);
		t2 = (TextView) findViewById(R.id.textView2);
		t1.setVisibility(View.INVISIBLE);
		t2.setVisibility(View.INVISIBLE);

		t3 = (TextView) findViewById(R.id.textView3);
		t4 = (TextView) findViewById(R.id.textView4);
		t3.setVisibility(View.INVISIBLE);
		t4.setVisibility(View.INVISIBLE);

		t5 = (TextView) findViewById(R.id.textView5);
		t6 = (TextView) findViewById(R.id.textView6);
		t5.setVisibility(View.INVISIBLE);
		t6.setVisibility(View.INVISIBLE);

		score = (TextView) findViewById(R.id.score);
		hit = (TextView) findViewById(R.id.hit);
		score.setVisibility(View.INVISIBLE);
		hit.setVisibility(View.INVISIBLE);
		showGameTime = (TextView) findViewById(R.id.showGameTime);
		showGameTime.setVisibility(View.INVISIBLE);

		hanndann = (TextView) findViewById(R.id.hanndann);
		hanndann.setVisibility(View.INVISIBLE);

		mouse2.setOnTouchListener(otl(1));
		mouse5.setOnTouchListener(otl(2));
		mouse8.setOnTouchListener(otl(3));

		handlerTime = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 0x994) {
					mediaplayer.stop();
					th1_ctrl = false;
					th2_ctrl = false;
					th3_ctrl = false;
					time_ctrl = false;

					Intent intent = new Intent(GameActivity.this, OwariActivity.class);
					Bundle bundle = new Bundle();
					removeDuplicate(wronghit);
					removeDuplicate(wrongnohit);
					removeDuplicate(right);
					right.removeAll(wronghit);
					right.removeAll(wrongnohit);
					bundle.putCharSequence("wronghit", wronghit.toString());
					bundle.putCharSequence("wrongnohit", wrongnohit.toString());
					bundle.putCharSequence("right", right.toString());
					bundle.putInt("total", total);
					bundle.putInt("hit", i);
					bundle.putInt("score", k);
					bundle.putInt("rightnohit", rightnohit);
					intent.putExtras(bundle);

					// 写入文件
					File wb = new File(Environment.getExternalStorageDirectory(), "wb002");
					BufferedWriter bw;
					if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
						if (!(wb.exists())) {
							try {
								bw = new BufferedWriter(new FileWriter(wb, true));
								bw.write("");
								bw.flush();
								bw.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						try {
							// 读取错题本
							BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(wb)));
							String lineTxt = null;
							while ((lineTxt = br.readLine()) != null) {
								wblist.add(lineTxt);
							}
							br.close();
							wrong.addAll(wronghit);
							wrong.addAll(wrongnohit);
							wrong.addAll(wblist);
							removeDuplicate(wrong);
							wrong.removeAll(right);
							String n = "";
							for (int i = 0; i < wrong.size(); i++) {
								n = n + wrong.get(i) + "\n";
							}
							bw = new BufferedWriter(new FileWriter(wb, false));
							bw.write(n);
							bw.flush();
							bw.close();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					startActivity(intent);
					Intent intent2 = new Intent();
					intent2.setAction("com.kumori.koumokug.GameActivity");
					sendBroadcast(intent2);
				}
				if (msg.what == 0x995) {
					score.setText("分数：" + k);
					hit.setText("正确击中次数：" + i);

					// 控制难度
					if (MainActivity.step == 1) {
						if (k >= 200) {
							sleepTime = 2000;
						}
						if (k >= 500) {
							hardLevel = 2;
							sleepTime = 3000;
						}
					}
					if (MainActivity.step == 2) {
						if (k >= 400) {
							sleepTime = 2000;
						}
						if (k >= 1000) {
							hardLevel = 3;
							sleepTime = 3000;
						}
					}
					if (MainActivity.step == 3) {
						if (k >= 1000) {
							sleepTime = 2000;
						}
					}

					// 显示剩余时间
					String s = "" + gameTime;
					if (s.length() == 3) {
						showGameTime.setText("剩余时间：" + s.substring(0, 2) + "." + s.substring(2, 3) + " s");
					}
					if (s.length() == 2) {
						showGameTime.setText("剩余时间：" + s.substring(0, 1) + "." + s.substring(1, 2) + " s");
					}
					if (s.length() == 1) {
						showGameTime.setText("剩余时间：" + "0." + s + " s");
					}

					// 游戏时用户按下home键
					boolean b = isBackground(GameActivity.this);
					if (b == true) {
						mediaplayer.stop();
						th1_ctrl = false;
						th2_ctrl = false;
						th3_ctrl = false;
						time_ctrl = false;
						Intent intent2 = new Intent();
						intent2.setAction("com.kumori.koumokug.GameActivity");
						sendBroadcast(intent2);
					}
				}
				super.handleMessage(msg);
			}
		};

		th1 = new Thread(Th(1, th1));
		th2 = new Thread(Th(2, th2));
		th3 = new Thread(Th(3, th3));

		time = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO 自动生成的方法存根
				Message mTime = handlerTime.obtainMessage();
				while (!Thread.currentThread().isInterrupted() && time_ctrl == true) {
					mTime = handlerTime.obtainMessage();
					mTime.what = 0x995;
					handlerTime.sendMessage(mTime);
					--gameTime;
					if (gameTime == 0) {
						mTime = handlerTime.obtainMessage();
						mTime.what = 0x994;
						handlerTime.sendMessage(mTime);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
					}
				}
			}
		});
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				soundpool.play(soundmap.get(3), 1, 1, 0, 0, 1);
				start.setVisibility(View.INVISIBLE);
				messagebutton.setVisibility(View.INVISIBLE);

				score.setVisibility(View.VISIBLE);
				hit.setVisibility(View.VISIBLE);
				hanndann.setVisibility(View.VISIBLE);
				showGameTime.setVisibility(View.VISIBLE);

				th1_ctrl = true;
				th2_ctrl = true;
				th3_ctrl = true;
				time_ctrl = true;

				// 初始化难度
				if (MainActivity.step == 1) {
					if (!th1.isAlive())
						th1.start();
					hardLevel = 1;
				}
				if (MainActivity.step == 2) {
					if (!th1.isAlive())
						th1.start();
					if (!th2.isAlive())
						th2.start();
					hardLevel = 2;
				}
				if (MainActivity.step == 3) {
					if (!th1.isAlive())
						th1.start();
					if (!th2.isAlive())
						th2.start();
					if (!th3.isAlive())
						th3.start();
					hardLevel = 3;
				}

				time.start();
				linear.setVisibility(View.VISIBLE);
				if (MainActivity.isbgm == 1) {
					mediaplayer.setLooping(true);
					mediaplayer.start();
				}
			}
		});
	}

	OnTouchListener otl(final int num) {

		final ImageView mouse3 = FindNum.findMouse3(num);
		final TextView t1 = FindNum.findT1(num), t2 = FindNum.findT2(num);

		OnTouchListener c = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				soundpool.play(soundmap.get(2), 1, 1, 0, 0, 1);
				FindNum.findTh(num).interrupt();
				SetNum.setTemp(num, 1);
				mouse3.setX(FindNum.findX(num));
				mouse3.setY(FindNum.findY(num));
				mouse3.setVisibility(View.VISIBLE);
				if (FindNum.findifBatsu(num) == 1) {
					j++;
					i++;// 正确击中
					k = j * 100;// 分数
					if (hardLevel == 2) {
						if (!th2.isAlive()) {
							th2.start();
						}
					}
					if (hardLevel == 3) {
						if (!th3.isAlive()) {
							th3.start();
						}
					}
					int iWordB = FindNum.findiWordB(num);
					hanndann.setText("你打对了！" + wordString[iWordB] + " 的正确的变形应为： " + maruString[iWordB]);
					right.add(wordString[iWordB]);
				} else {
					if (j != 0) {
						--j;
						k = j * 100;
					}
					hanndann.setText("你误伤了！");
					wronghit.add(wordString[FindNum.findiWordM(num)]);
				}
				v.setVisibility(View.INVISIBLE); // 设置地鼠不显示
				t1.setVisibility(View.INVISIBLE);
				t2.setVisibility(View.INVISIBLE);
				return false;
			}
		};
		return c;
	}

	Runnable Th(final int num, final Thread t) {

		final ImageView mouse1 = FindNum.findMouse1(num), mouse2 = FindNum.findMouse2(num),
				mouse3 = FindNum.findMouse3(num);
		final TextView t1 = FindNum.findT1(num), t2 = FindNum.findT2(num);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x101) {
					SetNum.setsound(num, soundpool.play(soundmap.get(1), 1, 1, 0, 0, 1));
					SetNum.setifBatsu(num, new Random().nextInt(tf.length));
					mouse3.setVisibility(View.INVISIBLE);
					mouse1.setX(FindNum.findX(num));
					mouse1.setY(FindNum.findY(num));
					mouse2.setX(FindNum.findX(num));
					mouse2.setY(FindNum.findY(num));
					mouse1.setVisibility(View.VISIBLE);
				}
				if (msg.what == 0x102) {
					total++;
					mouse1.setVisibility(View.INVISIBLE);
					mouse2.setVisibility(View.VISIBLE);
					Random random = new Random();
					if (FindNum.findifBatsu(num) == 1) {
						SetNum.setiWordB(num, random.nextInt(wordString.length));
						// 增大出错词出现概率
						SetNum.setiWordB(num, IncreaseWrongWord(wordString[FindNum.findiWordB(num)]));
						int iWordB = FindNum.findiWordB(num);
						String getWordB = batsuString[iWordB][random.nextInt(batsuString[iWordB].length)];
						// 对牌子中过长的字符进行换行
						String s = wordString[iWordB] + "→" + getWordB;
						String n = lineFeed(s, wordString[iWordB], getWordB);
						t2.setText(n);
						t2.setY(FindNum.findY(num));
						t2.setX(FindNum.findX(num));
						t2.setVisibility(View.VISIBLE);
						t1.setVisibility(View.INVISIBLE);
					} else {
						SetNum.setiWordM(num, random.nextInt(wordString.length));
						// 增大出错词出现概率
						SetNum.setiWordB(num, IncreaseWrongWord(wordString[FindNum.findiWordM(num)]));
						int iWordM = FindNum.findiWordM(num);
						String getWordM = maruString[iWordM];
						// 对牌子中过长的字符进行换行
						String s = wordString[iWordM] + "→" + getWordM;
						String n = lineFeed(s, wordString[iWordM], getWordM);
						t1.setText(n);
						t1.setY(FindNum.findY(num));
						t1.setX(FindNum.findX(num));
						t1.setVisibility(View.VISIBLE);
						t2.setVisibility(View.INVISIBLE);
					}
				}
				if (msg.what == 0x103) {
					mouse2.setVisibility(View.INVISIBLE);
					mouse1.setVisibility(View.VISIBLE);
					t1.setVisibility(View.INVISIBLE);
					t2.setVisibility(View.INVISIBLE);
					if (FindNum.findifBatsu(num) == 1) {
						wrongnohit.add(wordString[FindNum.findiWordB(num)]);
					} else {
						rightnohit++;
						right.add(wordString[FindNum.findiWordB(num)]);
					}
				}
				super.handleMessage(msg);
			}
		};

		Runnable r = new Runnable() {

			@Override
			public void run() {
				// TODO 自动生成的方法存根
				while (!Thread.currentThread().isInterrupted() && FindNum.findthCtrl(num) == true) {

					SetNum.setTemp(num, 0);
					located(num);// 出现位置

					Message m = handler.obtainMessage();
					m.what = 0x101;
					handler.sendMessage(m);

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {

					}

					m = handler.obtainMessage();
					m.what = 0x102;
					handler.sendMessage(m);

					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {

					}

					if (FindNum.findTemp(num) == 1) {
						try {
							Thread.sleep(400);
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块

						}
					} else {
						m = handler.obtainMessage();
						m.what = 0x103;
						handler.sendMessage(m);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {

						}
					}
					SetNum.setX(num, 0);
					SetNum.setY(num, 0);
				}
			}
		};
		return r;
	}

	void located(int num) {
		int x = FindNum.findX(num);
		int y = FindNum.findY(num);
		int tempx = FindNum.findTempX(num);
		int tempy = FindNum.findTempY(num);

		while ((x < x1 + 300 && x > x1 - 300 && y < y1 + 300 && y > y1 - 300)
				|| (x < x2 + 300 && x > x2 - 300 && y < y2 + 300 && y > y2 - 300)
				|| (x < x3 + 300 && x > x3 - 300 && y < y3 + 300 && y > y3 - 300) || (x == 0 && y == 0)
				|| (x == tempx && y == tempy)) {
			x = getEvenNum(200, displayWidth - 200);
			y = getEvenNum(displayHeight / 2, displayHeight - 200);
		}
		SetNum.setX(num, x);
		SetNum.setY(num, y);
	}

	public static int getEvenNum(int num1, int num2) {
		int s = (int) num1 + (int) (Math.random() * (num2 - num1));
		return s;
	}

	public static String lineFeed(String s, String s_1, String s_2) {
		String s0;
		if (s.length() >= 9) {
			s0 = s_1 + "→" + "\n" + s_2;
		} else {
			s0 = s;
		}
		return s0;
	}

	public static class FindNum {

		static Thread findTh(int num) {
			Thread b = null;
			if (num == 1)
				b = th1;
			if (num == 2)
				b = th2;
			if (num == 3)
				b = th3;
			return b;
		}

		static boolean findthCtrl(int num) {
			boolean b = true;
			if (num == 1)
				b = th1_ctrl;
			if (num == 2)
				b = th2_ctrl;
			if (num == 3)
				b = th3_ctrl;
			return b;
		}

		static int findiWordM(int num) {
			int b = 0;
			if (num == 1)
				b = iWordM_1;
			if (num == 2)
				b = iWordM_2;
			if (num == 3)
				b = iWordM_3;
			return b;
		}

		static int findiWordB(int num) {
			int b = 0;
			if (num == 1)
				b = iWordB_1;
			if (num == 2)
				b = iWordB_2;
			if (num == 3)
				b = iWordB_3;
			return b;
		}

		static int findifBatsu(int num) {
			int b = 0;
			if (num == 1)
				b = ifBatsu1;
			if (num == 2)
				b = ifBatsu2;
			if (num == 3)
				b = ifBatsu3;
			return b;
		}

		static TextView findT1(int num) {
			TextView b = null;
			if (num == 1)
				b = t1;
			if (num == 2)
				b = t3;
			if (num == 3)
				b = t5;
			return b;
		}

		static TextView findT2(int num) {
			TextView b = null;
			if (num == 1)
				b = t2;
			if (num == 2)
				b = t4;
			if (num == 3)
				b = t6;
			return b;
		}

		static ImageView findMouse1(int num) {
			ImageView b = null;
			if (num == 1)
				b = mouse1;
			if (num == 2)
				b = mouse4;
			if (num == 3)
				b = mouse7;
			return b;
		}

		static ImageView findMouse2(int num) {
			ImageView b = null;
			if (num == 1)
				b = mouse2;
			if (num == 2)
				b = mouse5;
			if (num == 3)
				b = mouse8;
			return b;
		}

		static ImageView findMouse3(int num) {
			ImageView b = null;
			if (num == 1)
				b = mouse3;
			if (num == 2)
				b = mouse6;
			if (num == 3)
				b = mouse9;
			return b;
		}

		static int findTemp(int num) {
			int b = 0;
			if (num == 1)
				b = temp1;
			if (num == 2)
				b = temp2;
			if (num == 3)
				b = temp3;
			return b;
		}

		static int findX(int num) {
			int b = 0;
			if (num == 1)
				b = x1;
			if (num == 2)
				b = x2;
			if (num == 3)
				b = x3;
			return b;
		}

		static int findY(int num) {
			int b = 0;
			if (num == 1)
				b = y1;
			if (num == 2)
				b = y2;
			if (num == 3)
				b = y3;
			return b;
		}

		static int findTempX(int num) {
			int b = 0;
			if (num == 1)
				b = tempx1;
			if (num == 2)
				b = tempx2;
			if (num == 3)
				b = tempx3;
			return b;
		}

		static int findTempY(int num) {
			int b = 0;
			if (num == 1)
				b = tempy1;
			if (num == 2)
				b = tempy2;
			if (num == 3)
				b = tempy3;
			return b;
		}
	}

	public static class SetNum {

		static void setsound(int num, int b) {
			if (num == 1)
				sound_1 = b;
			if (num == 2)
				sound_2 = b;
			if (num == 3)
				sound_3 = b;
		}

		static void setiWordM(int num, int b) {
			if (num == 1)
				iWordM_1 = b;
			if (num == 2)
				iWordM_2 = b;
			if (num == 3)
				iWordM_3 = b;
		}

		static void setiWordB(int num, int b) {
			if (num == 1)
				iWordB_1 = b;
			if (num == 2)
				iWordB_2 = b;
			if (num == 3)
				iWordB_3 = b;
		}

		static void setifBatsu(int num, int b) {
			if (num == 1)
				ifBatsu1 = b;
			if (num == 2)
				ifBatsu2 = b;
			if (num == 3)
				ifBatsu3 = b;
		}

		static void setTemp(int num, int b) {
			if (num == 1)
				temp1 = b;
			if (num == 2)
				temp2 = b;
			if (num == 3)
				temp3 = b;
		}

		static void setX(int num, int b) {
			if (num == 1)
				x1 = b;
			if (num == 2)
				x2 = b;
			if (num == 3)
				x3 = b;
		}

		static void setY(int num, int b) {
			if (num == 1)
				y1 = b;
			if (num == 2)
				y2 = b;
			if (num == 3)
				y3 = b;
		}
	}

	public static List removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					// 后台
					return true;
				} else {
					// 前台
					return false;
				}
			}
		}
		return false;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
			builder.setMessage("您确定退出游戏吗？");
			builder.setPositiveButton("退出游戏", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mediaplayer.stop();
					th1_ctrl = false;
					th2_ctrl = false;
					th3_ctrl = false;
					time_ctrl = false;
					Intent intent = new Intent();
					intent.setAction("com.kumori.koumokug.GameActivity");
					sendBroadcast(intent);
				}
			});
			builder.setNeutralButton("返回", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			builder.show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 增大错词出现概率
	public int IncreaseWrongWord(String word) {// BUG无错词会闪退
		int position = 999;
		int kakuritsu = 2;
		int iswrong = 0;// 判断该单词是否为错词
		Iterator iterator = wbliste.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(word)) {
				iswrong = 1;
				break;
			}
		}
		if (iswrong == 0) {// 不是错词的情况下
			kakuritsu = new Random().nextInt(10);// 这里可以调概率，现在是10%出现错词
			if (kakuritsu == 1) {
				String s = "";
				if (wbliste != null && !wbliste.isEmpty()) {
					s = wbliste.get(new Random().nextInt(wbliste.size()));
				}
				for (int i = 0; i < wordString.length; i++) {
					if (wordString[i].equals(s)) {
						position = i;
					}
				}
			}
		}
		if (position == 999) {// 999为当前的词就是错词或不改正，不用操作
			for (int i = 0; i < wordString.length; i++) {
				if (wordString[i].equals(word)) {
					position = i;
				}
			}
		}
		return position;
	}

	public class CloseActivityReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			GameActivity.this.finish();
		}
	}

}
