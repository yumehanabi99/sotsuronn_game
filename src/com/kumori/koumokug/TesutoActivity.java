package com.kumori.koumokug;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TesutoActivity extends Activity {

	String[] wordString = { "とく", "およぐ", "はたらく", "さらぐ", "のこつぐ", "かじく", "さんぽする", "はげます", "わたす", "ばこす", "ふなす", "たわんする",
			"あう", "かつ", "かわる", "みある", "たしかう", "つなつ", "くわえる", "もちいる", "ひかえる", "しょういる", "てきる", "こみる", "よむ", "まなぶ", "しぬ",
			"のりすむ", "たまむ", "まけぶ" };
	String[] maruString = { "といて", "およいで", "はたらいて", "さらいで", "のこついで", "かじいて", "さんぽして", "はげまして", "わたして", "ばこして", "ふなして",
			"たわんして", "あって", "かって", "かわって", "みあって", "たしかって", "つなって", "くわえて", "もちいて", "ひかえて", "しょういて", "てきて", "こみて",
			"よんで", "まなんで", "しんで", "のりすんで", "たまんで", "まけんで" };
	String[][] batsuString = { { "といで", "として", "とって", "とて", "とんで" }, { "およいて", "およして", "およって", "およて", "およんで" },
			{ "はたらいで", "はたらして", "はたらって", "はたらて", "はたらんで" }, { "さらいて", "さらして", "さらって", "さらて", "さらんで" },
			{ "のこついて", "のこつして", "のこつって", "のこつて", "のこつんで" }, { "かじいで", "かじして", "かじって", "かじて", "かじんで" },
			{ "さんぽいて", "さんぽいで", "さんぽって", "さんぽて", "さんぽんで" }, { "はげまいて", "はげまいで", "はげまって", "はげまて", "はげまんで" },
			{ "わたいて", "わたいで", "わたって", "わたて", "わたんで" }, { "ばこいて", "ばこいで", "ばこって", "ばこて", "ばこんで" },
			{ "ふないて", "ふないで", "ふなって", "ふなて", "ふなんで" }, { "たわんいて", "たわんいで", "たわんって", "たわんて", "たわんんで" },
			{ "あいて", "あいで", "あして", "あて", "あんで" }, { "かいて", "かいで", "かして", "かて", "かんで" },
			{ "かわいて", "かわいで", "かわして", "かわて", "かわんで" }, { "みあいて", "みあいで", "みあして", "みあて", "みあんで" },
			{ "たしかいて", "たしかいで", "たしかして", "たしかて", "たしかんで" }, { "つないて", "つないで", "つなして", "つなて", "つなんで" },
			{ "くわえいて", "くわえいで", "くわえして", "くわえって", "くわえんで" }, { "もちいいて", "もちいいで", "もちいして", "もちいって", "もちいんで" },
			{ "ひかえいて", "ひかえいで", "ひかえして", "ひかえって", "ひかえんで" }, { "しょういいて", "しょういいで", "しょういして", "しょういって", "しょういんで" },
			{ "てきいて", "てきいで", "てきして", "てきって", "てきんで" }, { "こみいて", "こみいで", "こみして", "こみって", "こみんで" },
			{ "よいて", "よいで", "よして", "よって", "よて" }, { "まないて", "まないで", "まなして", "まなって", "まなて" },
			{ "しいて", "しいで", "しして", "しって", "して" }, { "のりすいて", "のりすいで", "のりすして", "のりすって", "のりすて" },
			{ "たまいて", "たまいで", "たまして", "たまって", "たまて" }, { "まけいて", "まけいで", "まけして", "まけって", "まけて" } };

	List<String> list = new ArrayList<String>();
	int banngo = 0, maeBanngo = 0;
	RadioButton A, B, C, D;
	RadioGroup answer;
	TextView helpmessage;
	int time = 0, timeEnd = 0, ifUpload = 1, spinnerIfFirst = 1, checkedChangeL = 0;
	Thread th1, th2;

	File f;
	
	String kotaetamonndai = "01";
	LinearLayout linear1, linear;
	Handler handler;
	String key000=null;
	TextWatcher tw;
	String editTextTemp = "", maeCheckedChangeL = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 徭強伏撹議圭隈贋功
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tesuto);

		final TextView monndai = (TextView) findViewById(R.id.monndai);
		answer = (RadioGroup) findViewById(R.id.answer);
		A = (RadioButton) findViewById(R.id.A);
		B = (RadioButton) findViewById(R.id.B);
		C = (RadioButton) findViewById(R.id.C);
		D = (RadioButton) findViewById(R.id.D);
		Button back = (Button) findViewById(R.id.back);
		Button go = (Button) findViewById(R.id.go);

		Button upload = (Button) findViewById(R.id.upload);
		upload.setText("厘厮恂頼侭嗤籾朕" + "\n" + "鳩範住壌");

		final Spinner spinner = (Spinner) findViewById(R.id.spinner);

		ArrayAdapter adap = new ArrayAdapter<String>(this, R.layout.item_spinselect,
				new String[] { "萩僉夲勣柳廬欺議籾朕", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
						"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
						"30" });
		spinner.setAdapter(adap);
		adap.setDropDownViewResource(R.layout.item_spinselect);
		
		//幹秀率匯炎幣猟周
		File keygen = new File(Environment.getExternalStorageDirectory(), "keyg000");
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			if (!(keygen.exists())) {
				try {
					BufferedWriter keybw;
					keybw = new BufferedWriter(new FileWriter(keygen, true));
					keybw.write(CreatString.generateRandomStr(8));
					keybw.flush();
					keybw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(keygen)));
				key000=br.readLine();
				br.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		// 柴扮匂
		th1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO 徭強伏撹議圭隈贋功
				while (!th1.currentThread().isInterrupted()) {
					if (time >= 999999) {
						time = 0;
					}
					time++;
					try {
						th1.sleep(10);
					} catch (InterruptedException e) {
						// TODO 徭強伏撹議 catch 翠
						e.printStackTrace();
					}
				}
			}
		});
		th1.start();

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO 徭強伏撹議圭隈贋功
				if (msg.what == 0x101) {
					uploadFile();
				}
				super.handleMessage(msg);
			}
		};
		
		// 昧扮贋亀旺戻住編壌
		th2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO 徭強伏撹議圭隈贋功
				while (!th2.currentThread().isInterrupted()) {
					Message m = handler.obtainMessage();
					m.what = 0x101;
					handler.sendMessage(m);// 窟僕��連
					try {
						th2.sleep(3000);
					} catch (InterruptedException e) {
						// TODO 徭強伏撹議 catch 翠
						e.printStackTrace();
					}
				}
			}
		});
		th2.start();

		// 渇函籾催
		final int choose_num = 30;
		int choose[] = getEvenNum(choose_num, 30);
		for (int i = 0; i < choose.length; i++) {
			if (choose[i] < 10) {
				list.add("0" + choose[i] + "");
			} else {
				list.add(choose[i] + "");
			}
		}

		// 渇函危盾
		for (int i = 0; i < choose_num; i++) {
			String s = list.get(i);
			int wrong[] = getEvenNum(3, 5);
			int t0, t1, t2;
			t0 = wrong[0];
			t1 = wrong[1];
			t2 = wrong[2];
			int r[] = getEvenNum(1, 4);// 昧字方葎0123;
			if (r[0] == 0) {
				list.set(i, s + "9" + t0 + "" + t1 + "" + t2);
			}
			if (r[0] == 1) {
				list.set(i, s + t0 + "9" + t1 + "" + t2);
			}
			if (r[0] == 2) {
				list.set(i, s + t0 + "" + t1 + "9" + t2);
			}
			if (r[0] == 3) {
				list.set(i, s + t0 + "" + t1 + "" + t2 + "9");
			}
		}

		// 喘薩恬基
		for (int i = 0; i < choose_num; i++) {
			String s = list.get(i);
			list.set(i, s + "00000");
		}

		// 恬基扮寂
		for (int i = 0; i < choose_num; i++) {
			String s = list.get(i);
			list.set(i, s + "000000");
		}

		// 資函籾催
		monndai.setText("���}" + getJapaneseNumber((banngo + 1) + "") + "\n" + wordString[getMonndai(banngo)]);
		// 資函僉��
		getChoose(banngo);
		// 壓個延汽僉梓泥議峙扮資函
		getAnswer(banngo);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO 徭強伏撹議圭隈贋功

			}

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO 徭強伏撹議圭隈贋功
				// setGravity(android.view.Gravity.CENTER_HORIZONTAL);
				if (!((parent.getItemAtPosition(position).toString().equals("萩僉夲勣柳廬欺議籾朕")))) {

					maeBanngo = banngo;
					banngo = Integer.valueOf(parent.getItemAtPosition(position).toString()) - 1;
					if (!(banngo == 1 && spinnerIfFirst == 1)) {
						monndai.setText(
								"���}" + getJapaneseNumber((banngo + 1) + "") + "\n" + wordString[getMonndai(banngo)]);
						getChoose(banngo);
						getSelected(banngo);
						getAnswer(banngo);
						getTime(maeBanngo);
						spinnerIfFirst = 0;
						spinner.setSelection(0, true);
					}

					if (banngo < 9) {
						int i = banngo + 1;
						kotaetamonndai = kotaetamonndai + "0" + i;
					} else {
						int i = banngo + 1;
						kotaetamonndai = kotaetamonndai + i;
					}

				}
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 徭強伏撹議圭隈贋功
				maeBanngo = banngo;
				if (banngo > 0) {
					--banngo;
				} else {
					banngo = 0;
				}
				monndai.setText("���}" + getJapaneseNumber((banngo + 1) + "") + "\n" + wordString[getMonndai(banngo)]);

				getChoose(banngo);
				getSelected(banngo);
				getAnswer(banngo);

				getTime(maeBanngo);

				if (banngo < 9) {
					int i = banngo + 1;
					kotaetamonndai = kotaetamonndai + "0" + i;
				} else {
					int i = banngo + 1;
					kotaetamonndai = kotaetamonndai + i;
				}
			}
		});

		go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 徭強伏撹議圭隈贋功
				maeBanngo = banngo;
				if (banngo < choose_num - 1) {
					banngo++;
				} else {
					banngo = 29;
				}
				monndai.setText("���}" + getJapaneseNumber((banngo + 1) + "") + "\n" + wordString[getMonndai(banngo)]);

				getChoose(banngo);
				getSelected(banngo);
				getAnswer(banngo);

				getTime(maeBanngo);
				if (banngo < 9) {
					int i = banngo + 1;
					kotaetamonndai = kotaetamonndai + "0" + i;
				} else {
					int i = banngo + 1;
					kotaetamonndai = kotaetamonndai + i;
				}
			}
		});

		// 貧勧猟周
		upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 徭強伏撹議圭隈贋功
				String s0 = "";
				String s1 = "艇珊嗤參和叱祇籾朕隆頼撹��" + "\n";
				for (int i = 0; i < list.size(); i++) {
					int j;
					String s = list.get(i);
					j = i + 1;
					if ((s.substring(6, 7)).equals("0")) {
						s0 = s0 + j + "、";
					}
					j = 0;
				}
				
				if (s0.equals("")) {
					s1 = "";
				} else {
					s0 = s0.substring(0, s0.length() - 1);
				}

				new AlertDialog.Builder(TesutoActivity.this).setTitle("").setMessage("鳩協住壌担��" + s1 + s0)
						.setPositiveButton("鳩協", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								ifUpload = 0;
								th2.interrupt();
								uploadFile();
								Toast.makeText(TesutoActivity.this, "貧勧嶄��方昼朔辛返強曜竃殻會", Toast.LENGTH_SHORT).show();
							}
						}).setNegativeButton("函��", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {

							}
						}).create().show();
			}
		});

	}

	public static int[] getEvenNum(int num,int max) {//num倖方max恷寄峙
		int Random[] = new int[num];
		for (int i = 0; i < num; i++) {
			while (true) {
				int ran = (int) (max * Math.random());
				for (int j = 0; j < i; j++) {
					if (Random[j] == ran) {
						ran = -1;
						break;
					}
				}
				if (ran != -1) {
					Random[i] = ran;
					break;
				}
			}
		}
		return Random;
	}
	
	public static class CreatString {

	    public static ArrayList<String> strList = new ArrayList<String>();
	    public static Random random = new Random();
	    public static final int RANDOM_LENGTH1 = 256;
	    
	    static {
	        init();
	    }
	    
	    public static String generateRandomStr(int length) {
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < length; i++) {
	            int size = strList.size();
	            String randomStr = strList.get(random.nextInt(size));
	            sb.append(randomStr);
	        }
	        return sb.toString();
	    }
	    
	    public static void init() {
	        int begin = 97;
	        //伏撹弌亟忖銚,旺紗秘鹿栽
	        for(int i = begin; i < begin + 26; i++) {
	            strList.add((char)i + "");
	        }
	        //伏撹寄亟忖銚,旺紗秘鹿栽
	        begin = 65;
	        for(int i = begin; i < begin + 26; i++) {
	            strList.add((char)i + "");
	        }
	        //繍0-9議方忖紗秘鹿栽
	        for(int i = 0; i < 10; i++) {
	            strList.add(i + "");
	        }
	    }
	    
	}
	
	// 資函諒籾
	int getMonndai(int i) {
		String s = list.get(i).substring(0, 1);
		String s1;
		if (s.equals("0")) {
			s1 = list.get(i).substring(1, 2);
		} else {
			s1 = list.get(i).substring(0, 2);
		}
		int j = Integer.valueOf(s1);
		return j;
	}

	// 資函僉��
	void getChoose(int i) {
		int t = getMonndai(banngo);
		String s1 = list.get(i).substring(2, 3);
		String s2 = list.get(i).substring(3, 4);
		String s3 = list.get(i).substring(4, 5);
		String s4 = list.get(i).substring(5, 6);
		if (s1.equals("9")) {
			A.setText(maruString[t]);
			B.setText(batsuString[t][Integer.valueOf(s2)]);
			C.setText(batsuString[t][Integer.valueOf(s3)]);
			D.setText(batsuString[t][Integer.valueOf(s4)]);
		}
		if (s2.equals("9")) {
			A.setText(batsuString[t][Integer.valueOf(s1)]);
			B.setText(maruString[t]);
			C.setText(batsuString[t][Integer.valueOf(s3)]);
			D.setText(batsuString[t][Integer.valueOf(s4)]);
		}
		if (s3.equals("9")) {
			A.setText(batsuString[t][Integer.valueOf(s1)]);
			B.setText(batsuString[t][Integer.valueOf(s2)]);
			C.setText(maruString[t]);
			D.setText(batsuString[t][Integer.valueOf(s4)]);
		}
		if (s4.equals("9")) {
			A.setText(batsuString[t][Integer.valueOf(s1)]);
			B.setText(batsuString[t][Integer.valueOf(s2)]);
			C.setText(batsuString[t][Integer.valueOf(s3)]);
			D.setText(maruString[t]);
		}
	}

	// 資函喘薩恬基
	void getAnswer(final int i) {
		answer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO 徭強伏撹議圭隈贋功
				String moto = list.get(i);
				RadioButton r = (RadioButton) findViewById(checkedId);
				String s1 = "", s2 = "", s3 = "";
				s1 = moto.substring(0, 6);
				s2 = moto.substring(6, 11);
				s3 = moto.substring(11, moto.length());
				String s = "";
				if (r.getId() == R.id.A) {
					s = "1";
				}
				if (r.getId() == R.id.B) {
					s = "2";
				}
				if (r.getId() == R.id.C) {
					s = "3";
				}
				if (r.getId() == R.id.D) {
					s = "4";
				}
				if ((!(moto.substring(6, 7).equals(s))) && i == banngo) {
					list.set(i, s1 + s + s2.substring(0, 4) + s3);
				}
			}
		});
	}

	// 資函喘薩恬基扮寂
	void getTime(int i) {
		timeEnd = time;
		time = 0;
		String moto = list.get(i);
		String s2 = moto.substring(11, 17);
		String s3 = moto.substring(17, moto.length());
		int t;
		if (!s2.equals("000000")) {
			t = Integer.valueOf(s2) + timeEnd;
			timeEnd = t;
		}
		String s1 = moto.substring(0, 11);
		String s = "";
		if (timeEnd > 99999) {
			s = timeEnd + "";
		} else {
			if (timeEnd > 9999) {
				s = "0" + timeEnd;
			} else {
				if (timeEnd > 999) {
					s = "00" + timeEnd;
				} else {
					if (timeEnd > 99) {
						s = "000" + timeEnd;
					} else {
						if (timeEnd > 9) {
							s = "0000" + timeEnd;
						} else {
							if (timeEnd >= 0) {
								s = "00000" + timeEnd;
							}
						}
					}
				}
			}
		}
		list.set(i, s1 + s + s3);
	}

	// 唖性荻方忖廬晩猟方忖
	private static String getJapaneseNumber(String str) {
		String[] s2 = { "巣", "匯", "屈", "眉", "膨", "励", "鎗", "鈍", "伊", "湘" };
		StringBuffer sb = new StringBuffer();
		for (char c : str.toCharArray()) {
			sb.append(s2[Integer.parseInt(String.valueOf(c))]);
		}
		return sb.toString();
	}

	// 梓泥怏資誼喘薩僉夲議僉��
	void getSelected(int i) {
		String t = list.get(i).substring(6, 7);

		if (t.equals("1")) {
			A.setChecked(true);
		}
		if (t.equals("2")) {
			B.setChecked(true);
		}
		if (t.equals("3")) {
			C.setChecked(true);
		}
		if (t.equals("4")) {
			D.setChecked(true);
		}

		if (t.equals("0")) {
			A.setChecked(false);
			B.setChecked(false);
			C.setChecked(false);
			D.setChecked(false);
			// 賠茅醜泣
			try {
				answer.clearCheck();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	// 昧扮贋亀旺貧勧芝村猟周
	void uploadFile() {
		// 幹秀芝村方象議猟周
		File record = new File(Environment.getExternalStorageDirectory(), "recordA_" + key000);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(record, false));
			bw.write(list.toString());
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO 徭強伏撹議 catch 翠
			e.printStackTrace();
		}
		new Cos().cosUpdate(TesutoActivity.this, "recordA_" + key000);
	}
}
