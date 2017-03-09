package com.alo.alomobile.app;

import com.android.volley.VolleyError;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 09/03/2017
 */

public interface IStatusGetStringConnection {
    public void notifySuccess(String response);
    public void notifyError(VolleyError error);

}
