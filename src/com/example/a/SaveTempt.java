package com.example.a;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class SaveTempt {
	//比较时间，若新纪录比老记录的时间短，则返回true。
	public static boolean comparaTime(Context context, long Time){
		Map<String, String> userInfo = getUserInfo(context);
		if(userInfo != null) {
			long savedTime = Integer.parseInt(userInfo.get("time"));
			if(savedTime < Time){
				return false;
			}
		}
		return true;
	}
	//保存数据的方法，name是用户名，time是通关时间
	public static boolean saveUserInfo(Context context, String name,
            long time) {
		try {
			FileOutputStream f1 = context.openFileOutput("record.txt",
					Context.MODE_PRIVATE);
			f1.write((name + ":" + time).getBytes());
			f1.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	return false;
		}
}

// 从record.txt文件中获取存储的记录
public static Map<String, String> getUserInfo(Context context) {
	String content = "";
	try {
		FileInputStream fis = context.openFileInput("record.txt");
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		content = new String(buffer);
		Map<String, String> userMap = new HashMap<String, String>();
		String[] infos = content.split(":");
		userMap.put("name", infos[0]);
		userMap.put("time", infos[1]);
		fis.close();
		return userMap;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}