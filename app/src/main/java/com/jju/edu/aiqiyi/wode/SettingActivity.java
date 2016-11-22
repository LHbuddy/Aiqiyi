package com.jju.edu.aiqiyi.wode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.wode.settings.UserManagerActivity;

/**
 * Created by 凌浩 on 2016/11/22.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_user_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        initView();
    }

    private void initView() {
        rl_user_manager = (RelativeLayout) findViewById(R.id.rl_user_manager);

        rl_user_manager.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_user_manager:
                startActivity(new Intent(SettingActivity.this, UserManagerActivity.class));
                break;
            default:
                break;
        }
    }
}
