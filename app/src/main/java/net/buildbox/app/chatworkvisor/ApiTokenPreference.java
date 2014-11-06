package net.buildbox.app.chatworkvisor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

/**
 * APIトークンの格納
 *
 * Created by shige on 11/6/14.
 */
public class ApiTokenPreference {
    // APIトークン格納用のキー
    private static final String KEY_API_TOKEN = "api_token";

    /**
     * APIトークンの格納
     * @param context   コンテキスト
     * @param apiToken  格納するAPIトークン
     */
    public static void saveApiToken(Context context, String apiToken) {
        // TODO: 格納時の暗号化

        // APIトークンの格納
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_API_TOKEN, apiToken);
    }

    public static String loadApiToken(Context context) {
        // TODO: 取得時の暗号復号化

        // APIトークンの取得
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(KEY_API_TOKEN, "");
    }
}
