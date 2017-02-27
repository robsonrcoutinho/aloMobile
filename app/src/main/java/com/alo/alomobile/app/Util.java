package com.alo.alomobile.app;

import android.content.SharedPreferences;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 26/02/2017
 */

public class Util {

    public static boolean existeToken(SharedPreferences preferences) {

        if(preferences.getString(Consts.TOKEN, "")=="") {
            return false;
        }
        return true;
    }
}
