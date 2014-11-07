package net.buildbox.app.chatworkvisor;

import android.graphics.Bitmap;

/**
 * チャットルーム用のカスタムView
 *
 * Created by shige on 11/7/14.
 */
public class RoomData {
    private Bitmap roomImage;
    private String roomName;

    /**
     * チャットルーム画像の設定
     *
     * @param image     チャットルーム画像
     */
    public void setRoomImage(Bitmap image) {
        roomImage = image;
    }

    public Bitmap getRoomImage() {
        return roomImage;
    }

    public void setRoomName(String name) {
        roomName = name;
    }

    public String getRoomName() {
        return roomName;
    }
}
