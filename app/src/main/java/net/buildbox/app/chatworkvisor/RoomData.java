package net.buildbox.app.chatworkvisor;

import android.graphics.Bitmap;

/**
 * チャットルーム用のカスタムView
 *
 * Created by shige on 11/7/14.
 */
public class RoomData {
    private String roomId;
    private String roomImage;
    private String roomName;

    /**
     * ルームIDの設定
     *
     * @param id    ルームID
     */
    public void setRoomId(String id) {
        roomId = id;
    }

    /**
     * ルームIDの取得
     *
     * @return  ルームID
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * チャットルーム画像の設定
     *
     * @param image     チャットルーム画像
     */
    public void setRoomImage(String image) {
        roomImage = image;
    }

    /**
     * チャットルーム画像の取得
     *
     * @return  チャットルーム画像
     */
    public String getRoomImage() {
        return roomImage;
    }

    /**
     * チャットルーム名の設定
     *
     * @param name  チャットルーム名
     */
    public void setRoomName(String name) {
        roomName = name;
    }

    /**
     * チャットルーム名の取得
     *
     * @return  チャットルーム名
     */
    public String getRoomName() {
        return roomName;
    }
}
