package net.buildbox.app.chatworkvisor;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by shige on 11/7/14.
 */
public class ChatworkRooms {
    // APIトークンでログイン可能かどうかを/my/statusを使って確認する
    private static final String REQUEST_URL = "https://api.chatwork.com/v1/rooms";

    private Context mContext = null;

    ChatworkRooms(Context context) {
        mContext = context;
    }

    /**
     * コールバック処理の実装
     */
    public interface ChatworkRoomsCallbacks {
        public void onGetStatus(boolean result);
    }
    private ChatworkRoomsCallbacks _ChatworkRoomsCallBacks;
    public void setCallbacks(ChatworkRoomsCallbacks callbacks) {
        _ChatworkRoomsCallBacks = callbacks;
    }

    public void getRooms(String apiToken) {
        // AsyncTaskの開始
        new ChatworkRoomsTask().execute(apiToken);
    }

    private class ChatworkRoomsTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            // チャットルームの取得
            return fetchRoomList(params[0]);
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
                _ChatworkRoomsCallBacks.onGetStatus(true);
            } else {
                _ChatworkRoomsCallBacks.onGetStatus(false);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    // 自分が持つステータスの取得
    private Boolean fetchRoomList(String apiToken) {
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
