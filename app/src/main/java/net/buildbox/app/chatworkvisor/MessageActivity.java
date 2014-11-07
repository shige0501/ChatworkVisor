package net.buildbox.app.chatworkvisor;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MessageActivity extends ActionBarActivity {
    private String mRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // IntentからルームID取得
        Intent intent = getIntent();
        mRoomId = intent.getExtras().getString(ApplicationPreference.KEY_ROOM_ID, "");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSendEvent(View view) {
        EditText et_message = (EditText) findViewById(R.id.et_message);

        ChatworkMessage chatworkMessage = new ChatworkMessage(this);
        chatworkMessage.setCallbacks(mMessageCallbacks);
        chatworkMessage.sendMessage(

                ApplicationPreference.loadApiToken(this),
                mRoomId,
                et_message.getText().toString());

    }

    private ChatworkMessage.ChatworkMessageCallbacks mMessageCallbacks = new ChatworkMessage.ChatworkMessageCallbacks() {
        @Override
        public void onResult(boolean result) {
            // 送信結果を表示
            if (result) {
                Toast.makeText(getApplicationContext(), R.string.message_send_positive, Toast.LENGTH_SHORT).show();
                // 画面の終了
                finish();
            } else {
                Toast.makeText(getApplicationContext(), R.string.message_send_negative, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
