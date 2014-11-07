package net.buildbox.app.chatworkvisor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
        public void onGetStatus(ArrayList<RoomData> result);
    }
    private ChatworkRoomsCallbacks _ChatworkRoomsCallBacks;
    public void setCallbacks(ChatworkRoomsCallbacks callbacks) {
        _ChatworkRoomsCallBacks = callbacks;
    }

    public void getRooms(String apiToken) {
        // AsyncTaskの開始
        new ChatworkRoomsTask().execute(apiToken);
    }

    private class ChatworkRoomsTask extends AsyncTask<String, Integer, ArrayList<RoomData>> {

        @Override
        protected ArrayList<RoomData> doInBackground(String... params) {
            // チャットルームの取得
            return fetchRoomList(params[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(ArrayList<RoomData> result) {
            super.onPostExecute(result);

            // 自分が持つステータスの取得結果を返す
            if (result != null) {
                _ChatworkRoomsCallBacks.onGetStatus(result);
            } else {
                _ChatworkRoomsCallBacks.onGetStatus(null);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    // チャットルーム情報の取得
    private ArrayList<RoomData> fetchRoomList(String apiToken) {
        ArrayList<RoomData> retStatus = null;

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
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                resHttp.getEntity().writeTo(outputStream);
                String jsonData = outputStream.toString();

                // チャットルーム一覧の取得
                retStatus = extractRoomInfo(jsonData);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(HttpStatus.SC_UNAUTHORIZED == status) {
            //　認証に失敗した場合は、nullを返す
        }


        return retStatus;
    }

    // チャットルーム一覧のJSONデータからルーム画像とルーム名の取得
    private ArrayList<RoomData> extractRoomInfo(String jsonData) {
        ArrayList<RoomData> roomList = new ArrayList<RoomData>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                RoomData roomData = new RoomData();
                roomData.setRoomId(String.valueOf(jsonObj.getInt("room_id")));
//                roomData.setRoomImage(BitmapFactory.decodeResource()
                roomData.setRoomName(jsonObj.getString("name"));
                roomList.add(roomData);
            }
        } catch (JSONException e) {
            // TODO: 解析失敗時の対応は、後で検討
            e.printStackTrace();
        }

        return roomList;
    }
}
