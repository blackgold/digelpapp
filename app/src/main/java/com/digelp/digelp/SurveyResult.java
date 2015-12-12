package com.digelp.digelp;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SurveyResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private  List<KVP> result = new ArrayList<>();
    public void add(String k, String v){
        Log.e("MAIN", "SR" + k + "  " + v);
        result.add(new KVP(k,v));
    }

    public String getJsonSTring() {
        // "{\"BusinessId\" : \"102\", \"Timestamp\" : \"1412838697\", \"Food\" : \"1\", \"Ambiance\" : \"1\", \"Service\" : \"1\" }";
        // {"BusinessId" : "102", "Timestamp" : "1412838699", "Survey": { "Food" : "1", "Ambiance" : "1", "Service" : "1"} }
        JSONObject out = new JSONObject();
        JSONObject in = new JSONObject();
        try {
            for(KVP values : result) {
                if(values.getKey().equals("BusinessId") || values.getKey().equals("Timestamp")) {
                    out.put(values.getKey(), values.getValue());
                }
                else  {
                    in.put(values.getKey(), values.getValue());
                }
            }
            out.put("Survey",in);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.e("MAIN", "SR" + out.toString());
        return out.toString();
    }
}