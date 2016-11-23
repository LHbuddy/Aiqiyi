package com.jju.edu.aiqiyi.wode.settings;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jju.edu.aiqiyi.R;

/**
 * Created by Administrator on 2016/11/22.
 */

public class PlayAndDownloadActivity extends Activity {

    private ImageView iv_back;
    private LinearLayout ll_ever, ll_one;
    private ImageView iv_ever,iv_one;
    private RelativeLayout rl_playanddownload_play;
    private RelativeLayout rl_playanddownload_wifi;
    private ToggleButton tb_playanddownload_play;
    private ToggleButton tb_playanddownload_wifi;
    private LinearLayout ll_size;
    private ImageView iv_size;
    private boolean isChecked_play,isChecked_wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wode_playanddownload_activity_layout);
        initView();
        initEnvents();
    }

    private void initEnvents() {
        iv_back.setOnClickListener(onClick);
        ll_ever.setOnClickListener(onClick);
        ll_one.setOnClickListener(onClick);
        rl_playanddownload_play.setOnClickListener(onClick);
        rl_playanddownload_wifi.setOnClickListener(onClick);
        tb_playanddownload_play.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked_play = b;
                if (b){
                    tb_playanddownload_play.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                    );
                }else {
                    tb_playanddownload_play.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                    );
                }
            }
        });
        tb_playanddownload_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked_wifi = b;
                if (b){
                    tb_playanddownload_wifi.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                    );
                }else {
                    tb_playanddownload_wifi.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                    );
                }
            }
        });
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.ll_ever:
                    if (iv_ever.getVisibility() == View.VISIBLE){
                    }else {
                        iv_ever.setVisibility(View.VISIBLE);
                    }
                    iv_one.setVisibility(View.INVISIBLE);
                    break;
                case R.id.ll_one:
                    if (iv_one.getVisibility() == View.VISIBLE){
                    }else {
                        iv_one.setVisibility(View.VISIBLE);
                    }
                    iv_ever.setVisibility(View.INVISIBLE);
                    break;
                case R.id.rl_playanddownload_play:
                    if (isChecked_play){
                    tb_playanddownload_play.setChecked(false);
                    tb_playanddownload_play.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                    );
                }else {
                    tb_playanddownload_play.setChecked(true);
                    tb_playanddownload_play.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                    );
                }
                    break;
                case R.id.rl_playanddownload_wifi:
                    if (isChecked_wifi){
                        tb_playanddownload_wifi.setChecked(false);
                        tb_playanddownload_wifi.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                        );
                    }else {
                        tb_playanddownload_wifi.setChecked(true);
                        tb_playanddownload_wifi.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                        );
                    }
                    break;
            }
        }
    };

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        ll_ever = (LinearLayout) findViewById(R.id.ll_ever);
        ll_one = (LinearLayout) findViewById(R.id.ll_one);
        iv_ever = (ImageView) findViewById(R.id.iv_ever);
        iv_one = (ImageView) findViewById(R.id.iv_one);
        iv_one.setVisibility(View.INVISIBLE);
        rl_playanddownload_play = (RelativeLayout) findViewById(R.id.rl_playanddownload_play);
        rl_playanddownload_wifi = (RelativeLayout) findViewById(R.id.rl_playanddownload_wifi);
        tb_playanddownload_play = (ToggleButton) findViewById(R.id.tb_playanddownload_play);
        tb_playanddownload_wifi = (ToggleButton) findViewById(R.id.tb_playanddownload_wifi);
        ll_size = (LinearLayout) findViewById(R.id.ll_size);
        iv_size = (ImageView) findViewById(R.id.iv_size);
    }

}
