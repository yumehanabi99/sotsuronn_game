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

	// ����ѧϰ�ĵ���
	static String word[] = { "����", "����", "ĥ��", "Ԓ��", "����", "�㏊����", "����", "����", "�褦", "ʳ�٤�", "�𤭤�", "����", "�[��", "�",
			"����", "�Ф�" };
	static String wordx[] = { "������������", "������������", "ĥ����ĥ����", "Ԓ����Ԓ����", "���������", "�㏊������㏊����", "���ġ����ä�", "��������ä�",
			"�褦����ä�", "ʳ�٤��ʳ�٤�", "�𤭤���𤭤�", "���������", "�[�֡��[���", "����", "���̡������", "�Ф����Фä�" };
	static String imi[] = { "�����»�����Ʒ��д", "�죬�����ӿ죬�Ͻ����ż�", "ˢ����������ĥ��", "˵������̸����������˵��������", "�ɣ�������", "�ù�ѧϰ���ù����飬ѧϰ֪ʶ�����۾���",
			"�ȣ��ȴ����Ⱥ�", "��ȥ����ȥ", "��", "��", "����������������������", "��������", "�棬����", "�ȣ��ʣ���", "��������", "ȥ����" };
	static String yomikata[] = { "������������", "����������������", "�ߤ������ߤ�����", "�Ϥʤ����Ϥʤ���", "���������", "�٤󤭤礦������٤󤭤礦����", "�ޤġ��ޤä�",
			"������������ä�", "�������������ä�", "���٤�����٤�", "�������������", "���������", "�����֡��������", "�Τ���Τ��", "���̡������", "���������ä�" };
	static String r[][] = { { "���ޤ����ƕ����Ƥ���", "�ˤ��ּ�������Ƥ���", "���~�������ӛ��ˤ���" },
			{ "�����Ǳˤ����׷��", "����򼱤��Ǥ���Ҫ�Ϥʤ�", "˽�ϼ����ǻ�����Фä�" }, { "�n��ĥ���Ƥ��ʤ�", "���ΰ����ϤԤ��Ԥ���ĥ���Ƥ���", "ʯ��ĥ����ƽ��ˤ���" },
			{ "�ˤ���ǰ����Ԓ���Ƥ��ޤ���", "��Ů���T��������Ԓ���Ƥߤ褦", "Ԓ���Ƥ��뤳�Ȥ��֤���ʤ�" }, { "�ä��ˤ���", "�Τ��Ƥ��", "��������" },
			{ "�B���ä��㏊���Ƥ�������", "˽���й��Z���㏊���Ƥ��ޤ�", "�㏊���Ƥ���r�Ϥ��Ф���ʳ�¤������" }, { "�����˴��ä�", "���Ϥ������äƉӤ�", "�פ��L�����ƴ��äƤ��ޤ�" },
			{ "���ä��ޤ�", "�Ҥˎ��äƤ���", "�|���ˎ��äƤ���" }, { "һ�w�˸�äƣ�", "˽�ϸ�äƤ���", "�i���ʤ���Ǹ���äƤ���" },
			{ "ʳ�٤Ƹ��򉲤���", "ʳ�٤Ƥߤ�", "��ä���ʳ�٤Ƥ�������" }, { "�ӹ��Ϥޤ��𤭤Ƥ����", "�𤭤Ƥ����", "һ�����𤭤Ƥ���" },
			{ "�ޤ����Ƥ��ޤ���", "�Ҥ�����", "�ߤ������" }, { "����̨���[��Ǥ���", "�ӹ�������ɰ交��[����X��", "˽���[��ǤФ��ꤤ��" },
			{ "һ���Ǥ⤤��", "���ޤ�", "�����ɤ����¤���" }, { "˽��è������Ǥ���2���¤��^����", "��������Ǥ������h��", "�ˤα��������Ǥ���" },
			{ "�������ФäƤߤ���", "ˮ���I�����ФäƤ��ޤ�", "�ҤȤ���ФäƤ�����" } };
	static String z[][] = { { "���ܵ�д", "����д��", "��������" }, { "�Ͻ�׷����", "��û��Ҫ�������Ǹ�", "�Ҽ���ææȥ�˹�˾" },
			{ "��û��ˢ��", "��ѵ�ĥ�����", "��ʯͷĥƽ��" }, { "������ǰ����˵", "����Լ����̸̸��", "��֪����˵ʲô" }, { "����ϲ��", "�ڸ���", "���˵�" },
			{ "��Ŭ��ѧϰ", "����ѧϰ����", "�ù���ʱ�򳣳����ǳԷ�" }, { "ǧ������", "���۶���", "�����Դ�" }, { "��ȥ˯��", "�ص�����", "�ص��˶���" },
			{ "һ�𳪣�", "���ڳ���", "���߱ߺ��Ÿ�" }, { "�Ѷ��ӳԻ���", "�ԳԿ�", "����������" }, { "���ӻ�������", "������", "��ҹ��˯" },
			{ "��û��", "���Ҽ�", "��ҹ���" }, { "�滬��", "��������ɳ̲������", "��ÿ�춼����" }, { "���Ժ�һ����", "���˾�˯", "�뾡���" },
			{ "�ҵ�è������2������", "��ʹ���������Ҳ������", "������ɫʮ�ִ���" }, { "��ȥ���￴��", "��ȥ��ˮ", "����һ����ȥ" } };

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
				// TODO �Զ����ɵķ������
				if (isChecked) {
					isbgm = 1;
				} else {
					isbgm = 0;
				}
				new MainActivity().saveSet();
			}
		});

		if (step != 1) {
			b1.setText("����ѧϰ");
		}

		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				wrongMode = 1;
				Intent intent = new Intent(MainActivity.this, StudyActivity.class);
				startActivity(intent);
			}
		});

		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				step = 1;
				b1.setText("��ʼѧϰ");
				new MainActivity().saveSet();
				Toast.makeText(MainActivity.this, "���óɹ���", Toast.LENGTH_SHORT).show();
			}
		});

		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				File wb = new File(Environment.getExternalStorageDirectory(), "wb002");
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					if (wb.exists()) {// Ҫ����ļ��Ƿ�������
						wrongMode = 0;
						Intent intent = new Intent(MainActivity.this, StudyActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(MainActivity.this, "����û�м���ʻ�Ŷ~", Toast.LENGTH_SHORT).show();
					}

				}
			}
		});

		b4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
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
