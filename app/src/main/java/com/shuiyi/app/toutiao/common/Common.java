package com.shuiyi.app.toutiao.common;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.shuiyi.app.toutiao.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * Created by wang on 2016/6/22.
 */
public class Common {

    public static final String APP_ID = "";
    public static IWXAPI wxapi;

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void WXFenxiang(Context context, String url, String title, String scene) {

        WXWebpageObject wbobj = new WXWebpageObject();
        wbobj.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = wbobj;
        msg.description = title;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon);
        msg.thumbData = Util.bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        if (scene.equals("朋友圈")) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (scene.equals("会话")) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        Common.wxapi.sendReq(req);
    }

    public static boolean isDenglu(Context context) {
        String tel = getSharedPreferences(context, "tel");
        if (tel == null || "".equals(tel)) {
            return false;
        } else {
            return true;
        }
    }

    public static void setSharedPreferences(Context context, String key, String value) {
        preferences = context.getSharedPreferences("toutiao", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSharedPreferences(Context context, String key) {
        preferences = context.getSharedPreferences("toutiao", Activity.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void removeSharedPreferences(Context context, String key) {
        preferences = context.getSharedPreferences("toutiao", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
