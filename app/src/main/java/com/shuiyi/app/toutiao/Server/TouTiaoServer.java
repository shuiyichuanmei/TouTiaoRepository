package com.shuiyi.app.toutiao.Server;

import com.shuiyi.app.toutiao.bean.TouTiaoBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wang on 2016/6/12.
 */
public class TouTiaoServer {
    public static TouTiaoBean AnalyzeToObj(JSONObject jo) {
        TouTiaoBean item = new TouTiaoBean();
        try {
            item.setId(jo.getString("id"));
            item.setCreateDate(jo.getString("createDate"));
            item.setJianJie(jo.getString("jianJie"));
            item.setTitleImg(jo.getString("titleImg"));
            item.setTypeId(jo.getString("typeId"));
            item.setZuoZhe(jo.getString("zuoZhe"));
            item.setBiaoTi(jo.getString("biaoTi"));
        } catch (Exception ex) {
        }
        return item;
    }

    public static ArrayList<TouTiaoBean> AnalyzeToList(JSONArray response) {
        ArrayList<TouTiaoBean> itemList = new ArrayList<TouTiaoBean>();
        for (int i = 0; i < response.length(); i++) {
            itemList.add(AnalyzeToObj(response.optJSONObject(i)));
        }
        return itemList;
    }
}
