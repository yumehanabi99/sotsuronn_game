package com.kumori.koumokug;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OwariActivity extends Activity {

	int total, score, hit, rightnohit;
	String w1, w2, r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.owari);

		new MainActivity().saveSet();

		CloseActivityReceiver closeReceiver = new CloseActivityReceiver();
		IntentFilter intentFilter = new IntentFilter("com.kumori.koumokug.OwariActivity");
		registerReceiver(closeReceiver, intentFilter);

		final Button again = (Button) findViewById(R.id.again);
		Button back = (Button) findViewById(R.id.back);
		TextView tv = (TextView) findViewById(R.id.text);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		total = bundle.getInt("total");
		score = bundle.getInt("score");
		hit = bundle.getInt("hit");
		rightnohit = bundle.getInt("rightnohit");

		if (bundle.getString("wronghit").length() > 2) {
			w1 = bundle.getString("wronghit").substring(1, bundle.getString("wronghit").length() - 1);
		} else {
			w1 = "��";
		}

		if (bundle.getString("wrongnohit").length() > 2) {
			w2 = bundle.getString("wrongnohit").substring(1, bundle.getString("wrongnohit").length() - 1);
		} else {
			w2 = "��";
		}

		if (w1.equals("��") && w2.equals("��")) {
			tv.setText("��ȷ�ʣ�100%" + "\n" + "������" + score + "\n" + "���˵Ĵʣ�" + w1 + "\n" + "δ���еĴʣ�" + w2);

			if (MainActivity.step == 2) {
				MainActivity.step = 3;
			}
			if (MainActivity.step == 1) {
				MainActivity.step = 2;
			}
			MainActivity.b1.setText("����ѧϰ");

		} else {
			tv.setText("��ȷ�ʣ�" + txfloat(rightnohit + hit, total).substring(2, 4) + "."
					+ txfloat(hit, total).substring(4, 6) + "%" + "\n" + "������" + score + "\n" + "���˵Ĵʣ�" + w1 + "\n"
					+ "δ���еĴʣ�" + w2);
			if (Integer.valueOf(txfloat(rightnohit + hit, total).substring(2, 4)).intValue() > 60) {

				if (MainActivity.step == 2) {
					MainActivity.step = 3;
				}
				if (MainActivity.step == 1) {
					MainActivity.step = 2;
				}
				MainActivity.b1.setText("����ѧϰ");

			} else {
				again.setText("���汾��");
			}
		}

		again.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(OwariActivity.this, StudyActivity.class);
				startActivity(intent);
				Intent intent2 = new Intent();
				intent2.setAction("com.kumori.koumokug.OwariActivity");
				sendBroadcast(intent2);
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(OwariActivity.this, MainActivity.class);
				startActivity(intent);
				Intent intent2 = new Intent();
				intent2.setAction("com.kumori.koumokug.OwariActivity");
				sendBroadcast(intent2);
			}
		});

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent2 = new Intent();
			intent2.setAction("com.kumori.koumokug.OwariActivity");
			sendBroadcast(intent2);
		}
		return super.onKeyDown(keyCode, event);
	}

	public static String txfloat(int a, int b) {
		// TODO �Զ����ɵķ������
		DecimalFormat df = new DecimalFormat("0.0000");// ���ñ���λ��
		return df.format((float) a / b);
	}

	public class CloseActivityReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			OwariActivity.this.finish();
		}
	}
}