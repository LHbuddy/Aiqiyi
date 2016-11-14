package com.jju.edu.aiqiyi;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

import com.jju.edu.aiqiyi.app.MyApplication;

/**
 * Created by Administrator on 2016/11/14.
 */

public class BaseFragmentActivity extends FragmentActivity {

    public MyApplication application;
    private BaseFragmentActivity context;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (application == null) {
            application = (MyApplication) getApplication();
        }
        context = this;
        addActivity();
    }

    private void addActivity() {
        application.addActivity(context);
    }

    public void removeAllActivity() {
        application.removeAllActivity();
    }

    public void show_toast_short(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public void show_toast_long(String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
