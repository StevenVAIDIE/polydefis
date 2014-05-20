package com.polydefisv4.testActivityMartin;

import android.util.Log;

public class Debug {
	public static String TAG = "PolyDefi";
	public static void Log(String log){
		Log.d(TAG, log);
	}
	public static void Error(String log){
		Log.e(TAG, log);
	}
}
	
