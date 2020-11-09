package com.taole.toolkit.util.excel;

import com.taole.cache.redis.redisClient.RedisAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XlsObject implements Serializable {
    private String[] xlsTitles;
    private static final int MEM_TIME_OUT = 60 * 5;
    private List<String[]> rowList;

    public String[] getXlsTitles() {
        return xlsTitles;
    }

    public void setXlsTitles(String[] xlsTitles) {
        this.xlsTitles = xlsTitles;
    }

    public List<String[]> getRowList() {
        return rowList;
    }

    public void setRowList(List<String[]> rowList) {
        this.rowList = rowList;
    }
    public static void toStorage(String key, XlsObject xlsObject) throws JSONException {
        List<String[]> rows = xlsObject.getRowList();
        String[] strings = xlsObject.getXlsTitles();
        JSONArray mains = new JSONArray();
        JSONArray mains_title = new JSONArray();
        for (String[] r : rows) {
            JSONArray jsonArray = new JSONArray();
            for (String v : r)
                jsonArray.put(v);
            mains.put(jsonArray);
        }

        for (String title : strings) {
            mains_title.put(title);
        }
        JSONObject result = new JSONObject();
        result.put("rows", mains);
        result.put("strings", mains_title);
        RedisAPI.setEx(key, MEM_TIME_OUT, result.toString());
    }

    public static XlsObject getXlsObject4Storage(String key) {
        String content = RedisAPI.get(key);
        XlsObject xlsObject = new XlsObject();
        List<String[]> rowsS = new ArrayList<>();
        String[] strings = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(content);
            JSONArray rows = jsonObject.getJSONArray("rows");
            JSONArray titles = jsonObject.getJSONArray("strings");
            for (int i = 0; i < rows.length(); i++) {
                JSONArray jsonArray = rows.getJSONArray(i);
                List<String> list_rows = new ArrayList<>();
                for (int j = 0; j < jsonArray.length(); j++) {
                    list_rows.add(jsonArray.getString(j));
                }
                String[] temps = new String[list_rows.size() + 1];
                for (int l = 0; l < list_rows.size(); l++) {
                    temps[l] = list_rows.get(l);
                }
                rowsS.add(temps);
            }
            List<String> list_title = new ArrayList<>();
            strings = new String[titles.length() + 1];
            for (int i = 0; i < titles.length(); i++) {
                strings[i] = titles.getString(i);
            }
            xlsObject.setRowList(rowsS);
            xlsObject.setXlsTitles(strings);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return xlsObject;
    }

}
