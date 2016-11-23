package com.jju.edu.aiqiyi.wode;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.R;

public class HelpBack_Activity extends BaseActivity {
    private ImageView img_back;
    private RadioGroup radio_1, radio_2;
    private RadioButton radiobtn_1, radiobtn_2, radiobtn_3, radiobtn_4, radiobtn_5, radiobtn_6, radiobtn_7, radiobtn_8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.help_back_layout);
        img_back = (ImageView) findViewById(R.id.img_help_back);
        radio_1 = (RadioGroup) findViewById(R.id.radio_1);
        radio_2 = (RadioGroup) findViewById(R.id.radio_2);
        radiobtn_1 = (RadioButton) findViewById(R.id.radiobtn_1);
        radiobtn_2 = (RadioButton) findViewById(R.id.radiobtn_2);
        radiobtn_3 = (RadioButton) findViewById(R.id.radiobtn_3);
        radiobtn_4 = (RadioButton) findViewById(R.id.radiobtn_4);
        radiobtn_5 = (RadioButton) findViewById(R.id.radiobtn_5);
        radiobtn_6 = (RadioButton) findViewById(R.id.radiobtn_6);
        radiobtn_7 = (RadioButton) findViewById(R.id.radiobtn_7);
        radiobtn_8 = (RadioButton) findViewById(R.id.radiobtn_8);
        radio_2.setOnCheckedChangeListener(listener);
        radio_1.setOnCheckedChangeListener(listener);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.radiobtn_1:
                    if (radiobtn_1.isChecked()) {
                        radio_2.clearCheck();

                    }
                    break;
                case R.id.radiobtn_2:
                    if (radiobtn_2.isChecked()) {
                        radio_2.clearCheck();

                    }
                    break;
                case R.id.radiobtn_3:
                    if (radiobtn_3.isChecked()) {
                        radio_2.clearCheck();

                    }
                    break;
                case R.id.radiobtn_4:
                    if (radiobtn_4.isChecked()) {
                        radio_2.clearCheck();

                    }
                    break;
                case R.id.radiobtn_5:
                    if (radiobtn_5.isChecked()) {
                        radio_1.clearCheck();

                    }
                    break;
                case R.id.radiobtn_6:
                    if (radiobtn_6.isChecked()) {
                        radio_1.clearCheck();

                    }
                    break;
                case R.id.radiobtn_7:
                    if (radiobtn_7.isChecked()) {
                        radio_1.clearCheck();

                    }
                    break;
                case R.id.radiobtn_8:
                    if (radiobtn_8.isChecked()) {
                        radio_1.clearCheck();

                    }
                    break;
                default:
                    break;
            }
        }
    };

}


