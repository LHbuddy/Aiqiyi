package com.jju.edu.aiqiyi.wode.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.jju.edu.aiqiyi.R;


/**
 * Created by Administrator on 2016/11/23.
 */

public class UsefulActivity extends Activity {

    private ImageView iv_useful_back;
    private RelativeLayout rl_useful_connect;
    private RelativeLayout rl_useful_open;
    private ToggleButton tb_useful_connect;
    private ToggleButton tb_useful_open;
    private LinearLayout ll_useful_list, ll_useful_other;
    private ImageView iv_useful_list,iv_useful_other;
    private LinearLayout ll_useful_default, ll_useful_user;
    private ImageView iv_useful_default,iv_useful_user;
    private RelativeLayout rl_useful_notify;
    private ToggleButton tb_useful_notify;

    private boolean isChecked_connect,isChecked_open,isChecked_notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wode_setting_useful_activity_layout);
        initView();
        initEnvents();
    }

    private void initEnvents() {
        iv_useful_back.setOnClickListener(onClick);
        rl_useful_connect.setOnClickListener(onClick);
        rl_useful_open.setOnClickListener(onClick);
        tb_useful_connect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked_connect = b;
                if (b){
                    tb_useful_connect.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                    );
                }else {
                    tb_useful_connect.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                    );
                }
            }
        });
        tb_useful_open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked_open = b;
                if (b){
                    tb_useful_open.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                    );
                }else {
                    tb_useful_open.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                    );
                }
            }
        });
        ll_useful_list.setOnClickListener(onClick);
        ll_useful_other.setOnClickListener(onClick);
        ll_useful_default.setOnClickListener(onClick);
        ll_useful_user.setOnClickListener(onClick);
        rl_useful_notify.setOnClickListener(onClick);
        tb_useful_notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked_notify = b;
                if (b){
                    tb_useful_notify.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                    );
                }else {
                    tb_useful_notify.setBackground(
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
                case R.id.rl_useful_help_connect:  //连接后自动接收文件
                    if (isChecked_connect){
                        tb_useful_connect.setChecked(false);
                        tb_useful_connect.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                        );
                    }else {
                        tb_useful_connect.setChecked(true);
                        tb_useful_connect.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                        );
                    }
                    break;
                case R.id.rl_useful_help_open:   //打开热点分享流量
                    if (isChecked_open){
                        tb_useful_open.setChecked(false);
                        tb_useful_open.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                        );
                    }else {
                        tb_useful_open.setChecked(true);
                        tb_useful_open.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                        );
                    }
                    break;
                case R.id.iv_useful_title_back:
                    finish();
                    break;
                case R.id.ll_useful_pager_list:
                    if (iv_useful_list.getVisibility() == View.VISIBLE){
                    }else {
                        iv_useful_list.setVisibility(View.VISIBLE);
                    }
                    iv_useful_other.setVisibility(View.INVISIBLE);
                    break;
                case R.id.ll_useful_pager_other:
                    if (iv_useful_other.getVisibility() == View.VISIBLE){
                    }else {
                        iv_useful_other.setVisibility(View.VISIBLE);
                    }
                    iv_useful_list.setVisibility(View.INVISIBLE);
                    break;
                case R.id.ll_useful_service_default:
                    if (iv_useful_default.getVisibility() == View.VISIBLE){
                    }else {
                        iv_useful_default.setVisibility(View.VISIBLE);
                    }
                    iv_useful_user.setVisibility(View.INVISIBLE);
                    break;
                case R.id.ll_useful_service_user:
                    if (iv_useful_user.getVisibility() == View.VISIBLE){
                    }else {
                        iv_useful_user.setVisibility(View.VISIBLE);
                    }
                    iv_useful_default.setVisibility(View.INVISIBLE);
                    break;
                case R.id.rl_useful_notify:  //连接后自动接收文件
                    if (isChecked_notify){
                        tb_useful_notify.setChecked(false);
                        tb_useful_notify.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                        );
                    }else {
                        tb_useful_notify.setChecked(true);
                        tb_useful_notify.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                        );
                    }
                    break;
            }
        }
    };

    private void initView() {
        iv_useful_back = (ImageView) findViewById(R.id.iv_useful_title_back);
        rl_useful_connect = (RelativeLayout) findViewById(R.id.rl_useful_help_connect);
        rl_useful_open = (RelativeLayout) findViewById(R.id.rl_useful_help_open);
        tb_useful_connect = (ToggleButton) findViewById(R.id.tb_useful_help_connect);
        tb_useful_open = (ToggleButton) findViewById(R.id.tb_useful_help_open);
        ll_useful_list = (LinearLayout) findViewById(R.id.ll_useful_pager_list);
        ll_useful_other = (LinearLayout) findViewById(R.id.ll_useful_pager_other);
        iv_useful_list = (ImageView) findViewById(R.id.iv_useful_pager_list);
        iv_useful_other = (ImageView) findViewById(R.id.iv_useful_pager_other);
        iv_useful_other.setVisibility(View.INVISIBLE);
        ll_useful_default = (LinearLayout) findViewById(R.id.ll_useful_service_default);
        ll_useful_user = (LinearLayout) findViewById(R.id.ll_useful_service_user);
        iv_useful_default = (ImageView) findViewById(R.id.iv_useful_service_default);
        iv_useful_user = (ImageView) findViewById(R.id.iv_useful_service_user);
        iv_useful_user.setVisibility(View.INVISIBLE);
        rl_useful_notify = (RelativeLayout) findViewById(R.id.rl_useful_notify);
        tb_useful_notify = (ToggleButton) findViewById(R.id.tb_useful_notify);
    }
}
