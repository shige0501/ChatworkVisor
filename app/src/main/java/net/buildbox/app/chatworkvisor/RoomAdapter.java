package net.buildbox.app.chatworkvisor;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shige on 11/7/14.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    private ArrayList<RoomData> mDataList;

    public RoomAdapter(Context context, ArrayList<RoomData> dataList) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        mDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.room_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // チャットルーム画像の設定
        viewHolder.iv_room.setImageBitmap(
                mDataList.get(position).getRoomImage());

        // チャットルーム名の設定
        String data = (String) mDataList.get(position).getRoomName();
        viewHolder.tv_roomname.setText(data);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_room;
        TextView tv_roomname;

        public ViewHolder(View v) {
            super(v);
            iv_room = (ImageView) v.findViewById(R.id.iv_room);
            tv_roomname = (TextView) v.findViewById(R.id.tv_roomname);
        }
    }
}
