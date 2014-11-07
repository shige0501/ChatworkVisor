package net.buildbox.app.chatworkvisor;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // APIトークンの有無をチェックし、なければログイン画面を呼び出す
        if (!ApplicationPreference.isApiToken(this)) {
            startLoginActivity();
            // 画面は終了する
            finish();
        } else {
            Toast.makeText(this, R.string.login_ok, Toast.LENGTH_SHORT).show();
        }

        // RecyclerViewの設定
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.roomview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // アダプタの設定
        RoomData roomData = new RoomData();
        roomData.setRoomImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        roomData.setRoomName("チャットルーム");

        ArrayList<RoomData> roomList = new ArrayList<RoomData>();
        roomList.add(roomData);
        recyclerView.setAdapter(new RoomAdapter(this, roomList));
    }

    /**
     * ログイン画面の呼び出し
     */
    private void startLoginActivity() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            // ログイン画面の呼び出しを行い、ログアウトする
            // TODO: APIトークンのクリア用メソッド用意したほうがいいかも？後で検討する
            ApplicationPreference.saveApiToken(this, "");
            startLoginActivity();

            // メイン画面は終了する
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
