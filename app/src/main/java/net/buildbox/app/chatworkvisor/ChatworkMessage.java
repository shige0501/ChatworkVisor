package net.buildbox.app.chatworkvisor;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * チャットワークにメッセージ送信
 *
 * Created by shige on 11/7/14.
 */
public class ChatworkMessage {
    private static final String REQUEST_URL = "https://api.chatwork.com/v1/rooms/";
    private static final String REQUEST_URL_END = "/messages";
    private static final String PARAM_BODY = "body";

    private Context mContext = null;

    ChatworkMessage(Context context) {
        mContext = context;
    }

    /**
     * コールバック処理の実装
     */
    public interface ChatworkMessageCallbacks {
        public void onResult(boolean result);
    }
    private ChatworkMessageCallbacks _ChatworkMessageCallBacks;
    public void setCallbacks(ChatworkMessageCallbacks callbacks) {
        _ChatworkMessageCallBacks = callbacks;
    }

    public void sendMessage(String apiToken, String id, String message) {
        // AsyncTaskの開始
        new ChatworkMessageTask().execute(apiToken, id, message);
    }

    private class ChatworkMessageTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            // メッセージの送信
            return sendChatworkMessage(params[0], params[1], params[2]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // メッセージの送信結果を返す
            if (result == Boolean.TRUE) {
                _ChatworkMessageCallBacks.onResult(true);
            } else {
                _ChatworkMessageCallBacks.onResult(false);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    // 自分が持つステータスの取得
    private Boolean sendChatworkMessage(String apiToken, String id, String message) {
        Boolean retStatus = false;

        // HTTPの要求
        HttpClient httpClient = new DefaultHttpClient();

        // 要求URLの生成
        String url = REQUEST_URL + id + REQUEST_URL_END;

        // Bodyの準備
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("body", message));

        HttpPost reqHttp = new HttpPost(url);
        HttpResponse resHttp = null;
        try {
            reqHttp.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            reqHttp.setHeader("X-ChatWorkToken", apiToken);

            // POST要求の実行
            resHttp = httpClient.execute(reqHttp);
        } catch (UnsupportedEncodingException e) {
            // TODO: エラー処理の検討
            e.printStackTrace();
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
            // 送信成功
            retStatus = Boolean.TRUE;
        } else if(HttpStatus.SC_UNAUTHORIZED == status) {
            //　送信に失敗した場合は、falseを返す
            retStatus = Boolean.FALSE;
        }


        return retStatus;
    }
}
