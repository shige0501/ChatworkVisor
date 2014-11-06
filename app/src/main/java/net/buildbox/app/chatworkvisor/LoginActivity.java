package net.buildbox.app.chatworkvisor;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    /**
     * ログイン時の実装
     *
     * @param view
     */
    public void onLoginEvent(View view) {
        EditText et_token = (EditText) findViewById(R.id.et_token);

        //チェックを行う。コールバック
        ChatworkConnection connection = new ChatworkConnection(this);
        connection.setCallbacks(mLoginCallbacks);
        connection.getStatus(et_token.getText().toString());


    }



    private ChatworkConnection.ChatworkConnectionCallbacks mLoginCallbacks = new ChatworkConnection.ChatworkConnectionCallbacks() {
        @Override
        public void onGetStatus(boolean result) {
            // 入力されたキーがログイン可能かどうかをチェック
            if (result) {
                // TODO: ログイン可能であれば、保存してメイン画面に戻る

                // APIトークンの保存
                EditText et_token = (EditText) findViewById(R.id.et_token);
                ApplicationPreference.saveApiToken(getApplicationContext(), et_token.getText().toString());

                startMainActiity();
            } else {
                // TODO: 余力があれば、表示するToastをモダンなものにする（ライブラリ使って）
                Toast.makeText(getApplicationContext(), R.string.login_check_error, Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * メイン画面の呼び出し
     */
    private void startMainActiity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }
}
