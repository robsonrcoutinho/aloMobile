package com.alo.alomobile.app;
import org.json.JSONObject;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 02/03/2017
 */

public interface IStatusRespostaConnection {
    public boolean statusConnectionPost(boolean status, JSONObject resposta);
}
