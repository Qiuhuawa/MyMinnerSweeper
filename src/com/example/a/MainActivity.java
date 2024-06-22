package com.example.a;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
    
	private Button easy;
	private Button normal;
	private Button hard;
	private Button self;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		easy = (Button)findViewById(R.id.buttonEasy);
		normal = (Button)findViewById(R.id.buttonNormal);
		hard = (Button)findViewById(R.id.buttonHard);
		self = (Button)findViewById(R.id.buttonSelf);
		
		//四个按键以及监听
		easy.setOnClickListener(this);
		normal.setOnClickListener(this);
		hard.setOnClickListener(this);
		
		self.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intentself = new Intent(MainActivity.this, SelfActivity.class);
				startActivity(intentself);
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent1 = new Intent(this, GameActivity.class);
		switch (v.getId()){
			case R.id.buttonEasy:
			{
				intent1.putExtra("level", 15);//简单
				break;
			}
			case R.id.buttonNormal:	
			{
				intent1.putExtra("level", 25);//普通
				break;
			}
			case R.id.buttonHard: 
			{
				intent1.putExtra("level", 35);//困难
				break;
			}
		}
		startActivity(intent1);
		finish();
	}
}
