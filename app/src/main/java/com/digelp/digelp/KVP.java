package com.digelp.digelp;


import android.util.Log;

import java.io.Serializable;

class KVP implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mKey;
    private String mValue;
    KVP(String key,String value){
        Log.e("MAIN", "KVP" + key + "  " + value);
        mKey = key;
        mValue = value;
    }
    public String getKey() {
        return mKey;
    }

    public String getValue() {
        return mValue;
    }
}