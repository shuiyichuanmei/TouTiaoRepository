package com.shuiyi.app.toutiao.Server;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wang on 2016/6/19.
 */
public class KuaicanServer {
    public static KuaiCanBean AnalyzeToObj(JSONObject jo) {
        KuaiCanBean kc = new KuaiCanBean();
        try {
            kc.setId(jo.getString("id"));
            kc.setBiaoti(jo.getString("mingcheng"));
            kc.setImageUrl(jo.getString("imgxiao"));
            kc.settxText(jo.getString("songcantixing"));
            kc.setxfText(jo.getString("cankao"));
        } catch (Exception ex) {
        }
        return kc;
    }

    public static ArrayList<KuaiCanBean> AnalyzeToList(JSONArray response) {
        ArrayList<KuaiCanBean> kcList = new ArrayList<KuaiCanBean>();
        for (int i = 0; i < response.length(); i++) {
            kcList.add(AnalyzeToObj(response.optJSONObject(i)));
        }
        return kcList;
    }
}
