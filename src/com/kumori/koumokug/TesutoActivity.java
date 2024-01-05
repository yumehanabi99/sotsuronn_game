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

	String[] wordString = { "�Ȥ�", "���褰", "�Ϥ��餯", "���餰", "�Τ��Ĥ�", "������", "����ݤ���", "�Ϥ��ޤ�", "�錄��", "�Ф���", "�դʤ�", "����󤹤�",
			"����", "����", "�����", "�ߤ���", "��������", "�Ĥʤ�", "���廊��", "�������", "�Ҥ�����", "���礦����", "�Ƥ���", "���ߤ�", "���", "�ޤʤ�", "����",
			"�Τꤹ��", "���ޤ�", "�ޤ���" };
	String[] maruString = { "�Ȥ���", "���褤��", "�Ϥ��餤��", "���餤��", "�Τ��Ĥ���", "��������", "����ݤ���", "�Ϥ��ޤ���", "�錄����", "�Ф�����", "�դʤ���",
			"����󤷤�", "���ä�", "���ä�", "����ä�", "�ߤ��ä�", "�������ä�", "�Ĥʤä�", "���廊��", "�������", "�Ҥ�����", "���礦����", "�Ƥ���", "���ߤ�",
			"����", "�ޤʤ��", "�����", "�Τꤹ���", "���ޤ��", "�ޤ����" };
	String[][] batsuString = { { "�Ȥ���", "�Ȥ���", "�Ȥä�", "�Ȥ�", "�Ȥ��" }, { "���褤��", "���褷��", "����ä�", "�����", "������" },
			{ "�Ϥ��餤��", "�Ϥ��餷��", "�Ϥ���ä�", "�Ϥ����", "�Ϥ�����" }, { "���餤��", "���餷��", "����ä�", "�����", "������" },
			{ "�Τ��Ĥ���", "�Τ��Ĥ���", "�Τ��Ĥä�", "�Τ��Ĥ�", "�Τ��Ĥ��" }, { "��������", "��������", "�����ä�", "������", "�������" },
			{ "����ݤ���", "����ݤ���", "����ݤä�", "����ݤ�", "����ݤ��" }, { "�Ϥ��ޤ���", "�Ϥ��ޤ���", "�Ϥ��ޤä�", "�Ϥ��ޤ�", "�Ϥ��ޤ��" },
			{ "�錄����", "�錄����", "�錄�ä�", "�錄��", "�錄���" }, { "�Ф�����", "�Ф�����", "�Ф��ä�", "�Ф���", "�Ф����" },
			{ "�դʤ���", "�դʤ���", "�դʤä�", "�դʤ�", "�դʤ��" }, { "����󤤤�", "����󤤤�", "�����ä�", "������", "�������" },
			{ "������", "������", "������", "����", "�����" }, { "������", "������", "������", "����", "�����" },
			{ "���襤��", "���襤��", "���路��", "�����", "������" }, { "�ߤ�����", "�ߤ�����", "�ߤ�����", "�ߤ���", "�ߤ����" },
			{ "����������", "����������", "����������", "��������", "���������" }, { "�Ĥʤ���", "�Ĥʤ���", "�Ĥʤ���", "�Ĥʤ�", "�Ĥʤ��" },
			{ "���廊����", "���廊����", "���廊����", "���廊�ä�", "���廊���" }, { "���������", "���������", "���������", "������ä�", "��������" },
			{ "�Ҥ�������", "�Ҥ�������", "�Ҥ�������", "�Ҥ����ä�", "�Ҥ������" }, { "���礦������", "���礦������", "���礦������", "���礦���ä�", "���礦�����" },
			{ "�Ƥ�����", "�Ƥ�����", "�Ƥ�����", "�Ƥ��ä�", "�Ƥ����" }, { "���ߤ���", "���ߤ���", "���ߤ���", "���ߤä�", "���ߤ��" },
			{ "�褤��", "�褤��", "�褷��", "��ä�", "���" }, { "�ޤʤ���", "�ޤʤ���", "�ޤʤ���", "�ޤʤä�", "�ޤʤ�" },
			{ "������", "������", "������", "���ä�", "����" }, { "�Τꤹ����", "�Τꤹ����", "�Τꤹ����", "�Τꤹ�ä�", "�Τꤹ��" },
			{ "���ޤ���", "���ޤ���", "���ޤ���", "���ޤä�", "���ޤ�" }, { "�ޤ�����", "�ޤ�����", "�ޤ�����", "�ޤ��ä�", "�ޤ���" } };

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
		// TODO �Զ����ɵķ������
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
		upload.setText("��������������Ŀ" + "\n" + "ȷ�Ͻ���");

		final Spinner spinner = (Spinner) findViewById(R.id.spinner);

		ArrayAdapter adap = new ArrayAdapter<String>(this, R.layout.item_spinselect,
				new String[] { "��ѡ��Ҫ��ת������Ŀ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
						"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
						"30" });
		spinner.setAdapter(adap);
		adap.setDropDownViewResource(R.layout.item_spinselect);
		
		//����Ψһ��ʾ�ļ�
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
		
		// ��ʱ��
		th1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				while (!th1.currentThread().isInterrupted()) {
					if (time >= 999999) {
						time = 0;
					}
					time++;
					try {
						th1.sleep(10);
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}
			}
		});
		th1.start();

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO �Զ����ɵķ������
				if (msg.what == 0x101) {
					uploadFile();
				}
				super.handleMessage(msg);
			}
		};
		
		// ��ʱ�浵���ύ�Ծ�
		th2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				while (!th2.currentThread().isInterrupted()) {
					Message m = handler.obtainMessage();
					m.what = 0x101;
					handler.sendMessage(m);// ������Ϣ
					try {
						th2.sleep(3000);
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}
			}
		});
		th2.start();

		// ��ȡ���
		final int choose_num = 30;
		int choose[] = getEvenNum(choose_num, 30);
		for (int i = 0; i < choose.length; i++) {
			if (choose[i] < 10) {
				list.add("0" + choose[i] + "");
			} else {
				list.add(choose[i] + "");
			}
		}

		// ��ȡ���
		for (int i = 0; i < choose_num; i++) {
			String s = list.get(i);
			int wrong[] = getEvenNum(3, 5);
			int t0, t1, t2;
			t0 = wrong[0];
			t1 = wrong[1];
			t2 = wrong[2];
			int r[] = getEvenNum(1, 4);// �����Ϊ0123;
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

		// �û�����
		for (int i = 0; i < choose_num; i++) {
			String s = list.get(i);
			list.set(i, s + "00000");
		}

		// ����ʱ��
		for (int i = 0; i < choose_num; i++) {
			String s = list.get(i);
			list.set(i, s + "000000");
		}

		// ��ȡ���
		monndai.setText("���}" + getJapaneseNumber((banngo + 1) + "") + "\n" + wordString[getMonndai(banngo)]);
		// ��ȡѡ��
		getChoose(banngo);
		// �ڸı䵥ѡ��ť��ֵʱ��ȡ
		getAnswer(banngo);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO �Զ����ɵķ������

			}

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO �Զ����ɵķ������
				// setGravity(android.view.Gravity.CENTER_HORIZONTAL);
				if (!((parent.getItemAtPosition(position).toString().equals("��ѡ��Ҫ��ת������Ŀ")))) {

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
				// TODO �Զ����ɵķ������
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
				// TODO �Զ����ɵķ������
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

		// �ϴ��ļ�
		upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				String s0 = "";
				String s1 = "���������¼�����Ŀδ��ɣ�" + "\n";
				for (int i = 0; i < list.size(); i++) {
					int j;
					String s = list.get(i);
					j = i + 1;
					if ((s.substring(6, 7)).equals("0")) {
						s0 = s0 + j + "��";
					}
					j = 0;
				}
				
				if (s0.equals("")) {
					s1 = "";
				} else {
					s0 = s0.substring(0, s0.length() - 1);
				}

				new AlertDialog.Builder(TesutoActivity.this).setTitle("").setMessage("ȷ������ô��" + s1 + s0)
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								ifUpload = 0;
								th2.interrupt();
								uploadFile();
								Toast.makeText(TesutoActivity.this, "�ϴ��У��������ֶ��˳�����", Toast.LENGTH_SHORT).show();
							}
						}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {

							}
						}).create().show();
			}
		});

	}

	public static int[] getEvenNum(int num,int max) {//num����max���ֵ
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
	        //����Сд��ĸ,�����뼯��
	        for(int i = begin; i < begin + 26; i++) {
	            strList.add((char)i + "");
	        }
	        //���ɴ�д��ĸ,�����뼯��
	        begin = 65;
	        for(int i = begin; i < begin + 26; i++) {
	            strList.add((char)i + "");
	        }
	        //��0-9�����ּ��뼯��
	        for(int i = 0; i < 10; i++) {
	            strList.add(i + "");
	        }
	    }
	    
	}
	
	// ��ȡ����
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

	// ��ȡѡ��
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

	// ��ȡ�û�����
	void getAnswer(final int i) {
		answer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO �Զ����ɵķ������
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

	// ��ȡ�û�����ʱ��
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

	// ����������ת��������
	private static String getJapaneseNumber(String str) {
		String[] s2 = { "��", "һ", "��", "��", "��", "��", "��", "��", "��", "��" };
		StringBuffer sb = new StringBuffer();
		for (char c : str.toCharArray()) {
			sb.append(s2[Integer.parseInt(String.valueOf(c))]);
		}
		return sb.toString();
	}

	// ��ť�����û�ѡ���ѡ��
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
			// �������
			try {
				answer.clearCheck();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	// ��ʱ�浵���ϴ���¼�ļ�
	void uploadFile() {
		// ������¼���ݵ��ļ�
		File record = new File(Environment.getExternalStorageDirectory(), "recordA_" + key000);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(record, false));
			bw.write(list.toString());
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		new Cos().cosUpdate(TesutoActivity.this, "recordA_" + key000);
	}
}
