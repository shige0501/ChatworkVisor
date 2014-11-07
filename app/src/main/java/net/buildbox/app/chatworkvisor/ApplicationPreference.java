package net.buildbox.app.chatworkvisor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

/**
 * アプリケーションの共通情報の格納
 *
 * Created by shige on 11/6/14.
 */
public class ApplicationPreference {
    // APIトークン格納用のキー
    private static final String KEY_API_TOKEN = "api_token";

    // メッセージ送信時のチャットルームID格納キー
    public static final String KEY_ROOM_ID = "room_id";

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
        editor.commit();
    }

    /**
     * APIトークンの取得
     *
     * @param context   コンテキスト
     * @return          APIトークン
     */
    public static String loadApiToken(Context context) {
        // TODO: 取得時の暗号復号化

        // APIトークンの取得
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(KEY_API_TOKEN, "");
    }

    /**
     * APIトークンの有無のチェック
     *
     * @param context   コンテキスト
     * @return          true: トークンあり false: トークンなし
     */
    public static Boolean isApiToken(Context context) {
        // APIトークンの取得
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String apiToken = pref.getString(KEY_API_TOKEN, "");

        // トークンの状態を見て、格納されていたらTrueを返す
        if (!apiToken.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
