package com.alo.alomobile.app;

import com.android.volley.VolleyError;

import org.json.JSONArray;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 07/03/2017
 */

public interface IStatusGetConnection {
    public void notifySuccess(JSONArray response);
    public void notifyError(VolleyError error);

}
