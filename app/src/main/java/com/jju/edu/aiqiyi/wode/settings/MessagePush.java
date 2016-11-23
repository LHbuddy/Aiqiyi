package com.jju.edu.aiqiyi.wode.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.R;

public class MessagePush extends BaseActivity {
    private RelativeLayout relative1, relative2, relative3;
    private ToggleButton togbtn1, togbtn2, togbtn3;
    private boolean ischecked1, ischecked2, ischecked3;
    private ImageView img_message_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_push_layout);
        init_view();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.relative_message_push:
                    if (ischecked1) {
                        togbtn1.setChecked(false);
                        togbtn1.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                        );
                    } else {
                        togbtn1.setChecked(true);
                        togbtn1.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                        );
                    }
                    break;
                case R.id.relative_message_remind:
                    if (ischecked2) {
                        togbtn2.setChecked(false);
                        togbtn2.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                        );
                    } else {
                        togbtn2.setChecked(true);
                        togbtn2.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                        );
                    }
                    break;
                case R.id.relative_message_tv:
                    if (ischecked3) {
                        togbtn3.setChecked(false);
                        togbtn3.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                        );
                    } else {
                        togbtn3.setChecked(true);
                        togbtn3.setBackground(
                                getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                        );
                    }
                    break;
                case R.id.img_message_back:
                    finish();
                    break;
            }

        }
    };


    private void init_view() {
        relative1 = (RelativeLayout) findViewById(R.id.relative_message_push);
        relative2 = (RelativeLayout) findViewById(R.id.relative_message_remind);
        relative3 = (RelativeLayout) findViewById(R.id.relative_message_tv);
        togbtn1 = (ToggleButton) findViewById(R.id.togglebtn1);
        togbtn2 = (ToggleButton) findViewById(R.id.togglebtn2);
        togbtn3 = (ToggleButton) findViewById(R.id.togglebtn3);
        img_message_back = (ImageView) findViewById(R.id.img_message_back);
        img_message_back.setOnClickListener(listener);

        relative1.setOnClickListener(listener);
        relative2.setOnClickListener(listener);
        relative3.setOnClickListener(listener);
        togbtn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ischecked1 = isChecked;
                if (isChecked) {
                    togbtn1.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new)
                    );
                } else {
                    togbtn1.setBackground(
                            getResources().getDrawable(R.drawable.phone_my_setting_switch_new)
                    );
                }
            }
        });
        togbtn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ischecked2 = isChecked;
                if (isChecked) {
                    togbtn2.setBackground(getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new));
                } else {
                    togbtn2.setBackground(getResources().getDrawable(R.drawable.phone_my_setting_switch_new));
                }
            }
        });
        togbtn3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ischecked3 = isChecked;
                if (isChecked) {
                    togbtn3.setBackground(getResources().getDrawable(R.drawable.phone_my_setting_switch_selected_new));
                } else {
                    togbtn3.setBackground(getResources().getDrawable(R.drawable.phone_my_setting_switch_new));
                }
            }
        });
    }
}
