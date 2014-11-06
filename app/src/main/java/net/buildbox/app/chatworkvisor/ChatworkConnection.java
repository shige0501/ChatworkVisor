package net.buildbox.app.chatworkvisor;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by shige on 11/6/14.
 */
public class ChatworkConnection {
    // APIトークンでログイン可能かどうかを/my/statusを使って確認する
    private static final String REQUEST_URL = "https://api.chatwork.com/v1/my/status";

    private Context mContext = null;

    ChatworkConnection(Context context) {
        mContext = context;
    }

    /**
     * コールバック処理の実装
     */
    public interface ChatworkConnectionCallbacks {
        public void onGetStatus(boolean result);
    }
    private ChatworkConnectionCallbacks _ChatworkConnectionCallBacks;
    public void setCallbacks(ChatworkConnectionCallbacks callbacks) {
        _ChatworkConnectionCallBacks = callbacks;
    }

    public void getStatus(String apiToken) {
        // AsyncTaskの開始
        new ChatworkStatusTask().execute(apiToken);
    }

    private class ChatworkStatusTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            // 自分が持つステータスの取得
           return fetchMyStatus(params[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // 自分が持つステータスの取得結果を返す
            if (result == Boolean.TRUE) {
                _ChatworkConnectionCallBacks.onGetStatus(true);
            } else {
                _ChatworkConnectionCallBacks.onGetStatus(false);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    // 自分が持つステータスの取得
    private Boolean fetchMyStatus(String apiToken) {
        Boolean retStatus = false;

        // HTTPの要求
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet reqHttp = new HttpGet(REQUEST_URL);
        reqHttp.setHeader("X-ChatWorkToken", apiToken);
        HttpResponse resHttp = null;

        try {
            // GET要求の実行
            resHttp = httpClient.execute(reqHttp);
        } catch (ClientProtocolException e) {
            // TODO: エラー処理の検討
            e.printStackTrace();
        } catch (IOException e) {
            // TODO: エラー処理の検討
            e.printStackTrace();
        }

        // データの取得
        int status = resHttp.getStatusLine().getStatusCode();
        if (HttpStatus.SC_OK == status) {
            // 認証成功
            retStatus = Boolean.TRUE;
        } else if(HttpStatus.SC_UNAUTHORIZED == status) {
            //　認証に失敗した場合は、falseを返す
            retStatus = Boolean.FALSE;
        }


        return retStatus;
    }
}
