package com.alo.alomobile.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * @Author Robson Coutinho
 * @version 1.0
 * @since  22/02/2017.
 */

public class PedidoActivity extends AppCompatActivity {
    private static String TAG = PedidoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, ".onCreate()");
        super.onCreate(savedInstanceState);
    }
}
