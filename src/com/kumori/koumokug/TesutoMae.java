package com.kumori.koumokug;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TesutoMae extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tesuto_mae);

		CloseActivityReceiver closeReceiver = new CloseActivityReceiver();
		IntentFilter intentFilter = new IntentFilter("com.kumori.koumokug.TesutoMae");
		registerReceiver(closeReceiver, intentFilter);

		Button ok = (Button) findViewById(R.id.ok);
		Button back = (Button) findViewById(R.id.back);
		final EditText pw = (EditText) findViewById(R.id.editText);
		TextView message = (TextView) findViewById(R.id.message);
		message.setText("����˵��" + "\n" + "�����Թ���30��ѡ����" + "\n" + "ÿ�⽫��������ԭ��" + "\n" + "��ѡ����ȷ�ĥ��ζ���");

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				String s = pw.getText() + "";
				if (s.equals("666888")) {// ��������
					Intent intent = new Intent(TesutoMae.this, TesutoActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(TesutoMae.this, "�������", Toast.LENGTH_SHORT).show();
				}
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent2 = new Intent();
				intent2.setAction("com.kumori.koumokug.TesutoMae");
				sendBroadcast(intent2);
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent2 = new Intent();
			intent2.setAction("com.kumori.koumokug.TesutoMae");
			sendBroadcast(intent2);
		}
		return super.onKeyDown(keyCode, event);
	}

	public class CloseActivityReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			TesutoMae.this.finish();
		}
	}
}
