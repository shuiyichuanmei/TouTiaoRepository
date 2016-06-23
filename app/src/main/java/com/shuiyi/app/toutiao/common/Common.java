package com.shuiyi.app.toutiao.common;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

/**
 * Created by wang on 2016/6/22.
 */
public class Common {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void SendSms(String tel, String mubanid, String params) {

    }

    public static boolean isDenglu(Context context) {
        preferences = context.getSharedPreferences("toutiao", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        String tel = preferences.getString("tel", "");
        if (tel == null ||"".equals(tel)) {
            return false;
        } else {
            return true;
        }
    }
}
