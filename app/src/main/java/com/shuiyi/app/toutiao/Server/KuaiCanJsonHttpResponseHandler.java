package com.shuiyi.app.toutiao.Server;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.bean.TouTiaoBean;


import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hey on 2016/6/18.
 */
public class KuaiCanJsonHttpResponseHandler extends JsonHttpResponseHandler {
    public KuaiCanJsonHttpResponseHandler(){

    }
    @Override
    public final void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
        ArrayList<KuaiCanBean> kcList=new ArrayList<KuaiCanBean>();
        for (int i = 0; i < response.length(); i++) {
            KuaiCanBean kc = new KuaiCanBean();
            try {
                JSONObject jo = response.optJSONObject(i);
                kc.setId(jo.getString("id"));
                kc.setBiaoti(jo.getString("mingcheng"));
                kc.setImageUrl(jo.getString("imgxiao"));
                kc.settxText(jo.getString("songcantixing"));
                kc.setxfText(jo.getString("cankao"));
            } catch(Exception ex) { }
            kcList.add(kc);
        }
        OnSuccess(kcList);
    }
    public void OnSuccess(ArrayList<KuaiCanBean> kclist)
    {
    }
}
