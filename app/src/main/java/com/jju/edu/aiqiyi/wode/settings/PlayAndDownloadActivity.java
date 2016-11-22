package com.jju.edu.aiqiyi.wode.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.jju.edu.aiqiyi.R;

/**
 * Created by Administrator on 2016/11/22.
 */

public class PlayAndDownloadActivity extends Activity {

    private RadioGroup rg_playanddown;
    private RelativeLayout rl_playanddownload_play;
    private RelativeLayout rl_playanddownload_wifi;
    private ToggleButton tb_playanddownload_play;
    private ToggleButton tb_playanddownload_wifi;
    private LinearLayout ll_size;
    private ImageView iv_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wode_playanddownload_activity_layout);
        initView();
        initEnvents();
    }

    private void initEnvents() {

    }

    private void initView() {
        rg_playanddown = (RadioGroup) findViewById(R.id.rg_playanddown);
        rl_playanddownload_play = (RelativeLayout) findViewById(R.id.rl_playanddownload_play);
        rl_playanddownload_wifi = (RelativeLayout) findViewById(R.id.rl_playanddownload_wifi);
        tb_playanddownload_play = (ToggleButton) findViewById(R.id.tb_playanddownload_play);
        tb_playanddownload_wifi = (ToggleButton) findViewById(R.id.tb_playanddownload_wifi);
        ll_size = (LinearLayout) findViewById(R.id.ll_size);
        iv_size = (ImageView) findViewById(R.id.iv_size);
    }

}
