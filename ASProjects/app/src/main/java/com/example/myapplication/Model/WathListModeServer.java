package com.example.myapplication.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.modal.WatchListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WathListModeServer {
    private static String Watchlist = "";

    public static List<WatchlistModel> GetWatchlist(FragmentActivity f) {

        if (TextUtils.isEmpty(Watchlist)) {
            SharedPreferences userInfo = f.getSharedPreferences("Watchlist", Context.MODE_PRIVATE);
            String res = userInfo.getString("Watchlist", null);//读取username
            if (!TextUtils.isEmpty(res)) {
                Watchlist = res;
            }

        }

        List<WatchlistModel> WatchlistModel = new ArrayList<>();
        JSONArray jsonArray = null;
        try {

            if (TextUtils.isEmpty(Watchlist)) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(Watchlist);
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;

                jsonObject = jsonArray.getJSONObject(i);
                WatchlistModel.add(new WatchlistModel(
                        jsonObject.getString("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("type"),
                        jsonObject.getString("path")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return WatchlistModel;

    }

    public static boolean Remove(String id, FragmentActivity f) {

        try {
            List<WatchlistModel> Watchlist = GetWatchlist(f);
            int index = -1;
            for (int i = 0; i < Watchlist.size(); i++) {

                if (Watchlist.get(i).getId().equals(id)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                Watchlist.remove(index);


                JSONArray jsonArray = new JSONArray();
                for (WatchlistModel item : Watchlist) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", item.getId());
                    jsonObject.put("name", item.getName());
                    jsonObject.put("type", item.getType());
                    jsonObject.put("path", item.getPath());
                    jsonArray.put(jsonObject);
                }
                if (jsonArray.length() < 1) {
                    WathListModeServer.Watchlist = "";
                } else {
                    WathListModeServer.Watchlist = jsonArray.toString();
                }

            }

            SharedPreferences userInfo = f.getSharedPreferences("Watchlist", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userInfo.edit();//获取Editor
            editor.putString("Watchlist", WathListModeServer.Watchlist);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;

    }

    public static boolean Add(WatchlistModel watchlistModel, FragmentActivity f) {

        try {

            if (TextUtils.isEmpty(Watchlist)) {
                SharedPreferences userInfo = f.getSharedPreferences("Watchlist", Context.MODE_PRIVATE);
                String res = userInfo.getString("Watchlist", null);//读取username
                if (!TextUtils.isEmpty(res)) {
                    Watchlist = res;
                }

            }
            JSONArray jsonArray;

            if (TextUtils.isEmpty(Watchlist)) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(Watchlist);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", watchlistModel.getId());
            jsonObject.put("name", watchlistModel.getName());
            jsonObject.put("type", watchlistModel.getType());
            jsonObject.put("path", watchlistModel.getPath());
            jsonArray.put(jsonObject);

            WathListModeServer.Watchlist = jsonArray.toString();

            SharedPreferences userInfo = f.getSharedPreferences("Watchlist", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userInfo.edit();//获取Editor
            editor.putString("Watchlist", WathListModeServer.Watchlist);
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;

    }

    public static boolean IsExist(String id, FragmentActivity f) {
        List<WatchlistModel> List = GetWatchlist(f);

        for (WatchlistModel item : List
        ) {
            if (item.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    public static boolean ClearData(FragmentActivity f) {
        SharedPreferences userInfo = f.getSharedPreferences("Watchlist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();//获取Editor
        editor.putString("Watchlist", null);
        editor.commit();
        return true;
    }


    public static boolean ResetData(List<WatchListItem> watchListItems, FragmentActivity f) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (WatchListItem item : watchListItems) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", item.getId());
                jsonObject.put("name", item.getName());
                jsonObject.put("type", item.getType());
                jsonObject.put("path", item.getPath());
                jsonArray.put(jsonObject);
            }
            if (jsonArray.length() < 1) {
                WathListModeServer.Watchlist = "";
            } else {
                WathListModeServer.Watchlist = jsonArray.toString();
            }

            SharedPreferences userInfo = f.getSharedPreferences("Watchlist", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userInfo.edit();//获取Editor
            editor.putString("Watchlist", WathListModeServer.Watchlist);
            editor.commit();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}