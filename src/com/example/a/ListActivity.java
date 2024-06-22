package com.example.a;


import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ListActivity extends Activity{

	private TextView listname;
	private TextView listtime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		listname = (TextView) findViewById(R.id.listname);
		listtime = (TextView) findViewById(R.id.listtime);
		//��savetempt���getUserInfo����������Ϣ�����ø�textview
		Map<String, String> userInfo = SaveTempt.getUserInfo(this);
		if(userInfo != null){
			listname.setText(userInfo.get("name"));
			listtime.setText(userInfo.get("time"));
		}
		else{
			listname.setText("������ս�߳ɹ���");
		}
	}
	//�˵�����
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.list, menu);
    	return true;
    }
	//�˵�ѡ��ʱ�Ĵ���ѡ��
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case R.id.menu_pref:
    		startActivity(new Intent(this, MainActivity.class));
    		return true;
		case R.id.menu_exit:
			finish();
			return true;
		default: return false;
    	}	
    }
}