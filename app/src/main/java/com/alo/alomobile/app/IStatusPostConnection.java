package com.alo.alomobile.app;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 02/03/2017
 */

public interface IStatusPostConnection {
    public void notifySuccess(JSONObject response);
    public void notifyError(VolleyError error);

}
