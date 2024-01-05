package com.kumori.koumokug;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	// 用于学习的单词
	static String word[] = { "書く", "急ぐ", "磨く", "話す", "する", "勉強する", "待つ", "帰る", "歌う", "食べる", "起きる", "来る", "遊ぶ", "飲む",
			"死ぬ", "行く" };
	static String wordx[] = { "書く→書いて", "急ぐ→急いで", "磨く→磨いて", "話す→話して", "する→して", "勉強する→勉強して", "待つ→待って", "帰る→帰って",
			"歌う→歌って", "食べる→食べて", "起きる→起きて", "来る→来て", "遊ぶ→遊んで", "飲む→飲んで", "死ぬ→死んで", "行く→行って" };
	static String imi[] = { "做文章或创作作品，写", "快，急，加快，赶紧，着急", "刷净，擦亮，磨光", "说，讲；谈话，商量；说明，告诉", "干；做；办", "用功学习，用功读书，学习知识，积累经验",
			"等，等待，等候", "回去，归去", "唱", "吃", "起，起来，立起来；坐起来", "来，到来", "玩，游玩", "喝，咽，吃", "死，死亡", "去，走" };
	static String yomikata[] = { "かく→かいて", "いそぐ→いそいで", "みがく→みがいて", "はなす→はなして", "する→して", "べんきょうする→べんきょうして", "まつ→まって",
			"かえる→かえって", "うたう→うたって", "たべる→たべて", "おきる→おきて", "くる→きて", "あそぶ→あそんで", "のむ→のんで", "しぬ→しんで", "いく→いって" };
	static String r[][] = { { "ごまかして書いている", "彼は手紙を書いている", "言葉を書いて記念にする" },
			{ "急いで彼の後を追う", "それを急いでやる必要はない", "私は急いで会社に行った" }, { "歯を磨いていない", "その包丁はぴかぴかに磨いてある", "石を磨いて平らにした" },
			{ "彼は以前から話していました", "彼女を誘い出して話してみよう", "話していることが分からない" }, { "好きにして", "何してんの", "安くして" },
			{ "頑張って勉強してください", "私は中国語を勉強しています", "勉強している時はしばしば食事を忘れる" }, { "待ちに待って", "値上がりを待って売る", "首を長くして待っています" },
			{ "帰って寝る", "家に帰ってきた", "東京に帰ってきた" }, { "一緒に歌って！", "私は歌っている", "歩きながら鼻歌を歌っている" },
			{ "食べて腹を壊した", "食べてみて", "ゆっくり食べてください" }, { "子供はまだ起きているよ", "起きているの", "一晩中起きている" },
			{ "まだ来ていません", "家に来て", "みんな来て" }, { "滑り台で遊んでいる", "子供たちは砂浜で遊んで騒ぐ", "私は遊んでばかりいる" },
			{ "一杯飲んでもいい", "飲んで寝た", "ご自由に飲んで下さい" }, { "私の猫が死んでから2か月が過ぎた", "俺が死んでも魂は永遠さ", "彼の表情は死んでいる" },
			{ "そこに行ってみたい", "水を買いに行ってきます", "ひとりで行っていいよ" } };
	static String z[][] = { { "敷衍地写", "他在写信", "题字留念" }, { "赶紧追上他", "你没必要急着做那个", "我急急忙忙去了公司" },
			{ "我没有刷牙", "这把刀磨得锃亮", "把石头磨平了" }, { "他从以前就在说", "把她约出来谈谈吧", "不知道在说什么" }, { "随你喜欢", "在干嘛", "便宜点" },
			{ "请努力学习", "我在学习中文", "用功的时候常常忘记吃饭" }, { "千盼万盼", "待价而沽", "翘首以待" }, { "回去睡觉", "回到家了", "回到了东京" },
			{ "一起唱！", "我在唱歌", "边走边哼着歌" }, { "把肚子吃坏了", "吃吃看", "请慢慢享用" }, { "孩子还醒着呢", "醒着吗", "成夜不睡" },
			{ "还没来", "来我家", "大家过来" }, { "玩滑梯", "孩子们在沙滩上嬉闹", "我每天都在玩" }, { "可以喝一杯吗", "喝了就睡", "请尽情喝" },
			{ "我的猫死了有2个月了", "即使我死了灵魂也会永存", "他的神色十分呆滞" }, { "想去那里看看", "我去买水", "可以一个人去" } };

	static int step = 1, isbgm = 1, wrongMode = 1;
	static Button b1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		final CheckBox bgm = (CheckBox) findViewById(R.id.checkbox);

		String settings = new MainActivity().readSet();
		if (settings != null) {
			step = Integer.valueOf(settings.substring(0, 1));
			isbgm = Integer.valueOf(settings.substring(1, 2));
		}
		if (isbgm == 1) {
			bgm.setChecked(true);
		}

		bgm.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO 自动生成的方法存根
				if (isChecked) {
					isbgm = 1;
				} else {
					isbgm = 0;
				}
				new MainActivity().saveSet();
			}
		});

		if (step != 1) {
			b1.setText("继续学习");
		}

		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				wrongMode = 1;
				Intent intent = new Intent(MainActivity.this, StudyActivity.class);
				startActivity(intent);
			}
		});

		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				step = 1;
				b1.setText("开始学习");
				new MainActivity().saveSet();
				Toast.makeText(MainActivity.this, "重置成功！", Toast.LENGTH_SHORT).show();
			}
		});

		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				File wb = new File(Environment.getExternalStorageDirectory(), "wb002");
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					if (wb.exists()) {// 要检测文件是否有内容
						wrongMode = 0;
						Intent intent = new Intent(MainActivity.this, StudyActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(MainActivity.this, "您还没有加入词汇哦~", Toast.LENGTH_SHORT).show();
					}

				}
			}
		});

		b4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(MainActivity.this, TesutoMae.class);
				startActivity(intent);
			}
		});
	}

	void saveSet() {
		File set = new File(Environment.getExternalStorageDirectory(), "koumokug");
		BufferedWriter bw;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			if (!(set.exists())) {
				try {
					bw = new BufferedWriter(new FileWriter(set, true));
					bw.write("");
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				bw = new BufferedWriter(new FileWriter(set, false));
				bw.write(step + "" + isbgm);
				bw.flush();
				bw.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	String readSet() {
		String lineTxt = null;
		File set = new File(Environment.getExternalStorageDirectory(), "koumokug");
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(set)));
				lineTxt = br.readLine();
				br.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return lineTxt;
	}
}
