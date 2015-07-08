package com.sj.news35;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.sj.news35.utils.SPUtils;

import java.lang.ref.WeakReference;


/**
 * 欢迎界面 Splash
 */
public class WelcomeActivity extends Activity {
    SharedPreferences sp;

    private MyHandler handler = new MyHandler(this);

    private static class MyHandler extends Handler {

        private WeakReference<WelcomeActivity> activity;

        public MyHandler(WelcomeActivity activity) {
            this.activity = new WeakReference<WelcomeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (!SPUtils.getBoolean(activity.get(), "is_first", false)) {
                Intent intent = new Intent(activity.get(), GuidActivity.class);
                activity.get().startActivity(intent);
                SPUtils.saveBoolean(activity.get(), "is_first", true);
                activity.get().finish();
            } else {
                Intent intent = new Intent(activity.get(), MainActivity.class);
                activity.get().startActivity(intent);
                activity.get().finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        handler.sendEmptyMessageDelayed(0, 400);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
