package com.shuiyi.app.toutiao.Server;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.shuiyi.app.toutiao.bean.TouTiaoBean;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wang on 2016/6/12.
 */
public class TouTiaoJsonHttpResponseHandler extends JsonHttpResponseHandler
{
    public TouTiaoJsonHttpResponseHandler()
    {
    }
    @Override
    public final void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
        ArrayList<TouTiaoBean> ttList=new ArrayList<TouTiaoBean>();
        for (int i = 0; i < response.length(); i++) {
            TouTiaoBean tt = new TouTiaoBean();
            try {
                JSONObject jo = response.optJSONObject(i);
                tt.setId(jo.getString("id"));
                tt.setCreateDate(jo.getString("createDate"));
                tt.setJianJie(jo.getString("jianJie"));
                tt.setTitleImg(jo.getString("titleImg"));
                tt.setTypeId(jo.getString("typeId"));
                tt.setZuoZhe(jo.getString("zuoZhe"));
                tt.setBiaoTi(jo.getString("biaoTi"));
            } catch(Exception ex) { }
            ttList.add(tt);
        }
        OnSuccess(ttList);
    }
    public void OnSuccess(ArrayList<TouTiaoBean> ttlist)
    {
    }
}
