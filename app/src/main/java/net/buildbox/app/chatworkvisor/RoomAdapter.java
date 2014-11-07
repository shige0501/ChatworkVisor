package net.buildbox.app.chatworkvisor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shige on 11/7/14.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder>{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private static ArrayList<RoomData> mDataList;

    public RoomAdapter(Context context, ArrayList<RoomData> dataList) {
        super();
        mContext = context;
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

        Picasso.with(mContext)
                .load(mDataList.get(position).getRoomImage())
                .into(viewHolder.iv_room);

        // チャットルーム名の設定
        String data = (String) mDataList.get(position).getRoomName();
        viewHolder.tv_roomname.setText(data);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
                    implements View.OnClickListener {
        ImageView iv_room;
        TextView tv_roomname;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            iv_room = (ImageView) v.findViewById(R.id.iv_room);
            tv_roomname = (TextView) v.findViewById(R.id.tv_roomname);
        }

        @Override
        public void onClick(View view) {
            startMessageActivity(view, getPosition());
        }

        private void startMessageActivity(View view, int position) {
            Intent messageIntent = new Intent(view.getContext(), MessageActivity.class);
            messageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // オプション情報の設定
            messageIntent.putExtra(ApplicationPreference.KEY_ROOM_ID, mDataList.get(position).getRoomId());

            view.getContext().startActivity(messageIntent);
        }
    }
}
