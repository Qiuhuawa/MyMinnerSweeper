package com.example.a;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SelfActivity extends Activity implements OnClickListener{

	private TextView name1;
	private TextView minenum;
	private Button bt1;
	private Intent intent3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_self);
		name1 = (TextView) findViewById(R.id.name);
		minenum = (TextView) findViewById(R.id.Mnum);
		bt1 = (Button) findViewById(R.id.btconfrom);
		
		bt1.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		try{
			intent3 = new Intent(this, GameActivity.class);
			String name = name1.getText().toString().trim();;
			int minum = Integer.parseInt(minenum.getText().toString().trim());
			if(minum < 0 && minum >= 150){
				Toast.makeText(this, "你输入的数据非法，请重新输入！", Toast.LENGTH_SHORT).show();
				finish();
			}
			intent3.putExtra("name", name);
			intent3.putExtra("level", minum);
		}catch(Exception e){
			e.printStackTrace();
			Toast.makeText(this, "请输入一个整数！", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
		startActivity(intent3);
		finish();
	}
}