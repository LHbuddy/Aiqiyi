package com.jju.edu.aiqiyi.wode;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.R;

public class HelpBack_Activity extends BaseActivity {
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.help_back_layout);
        img_back = (ImageView) findViewById(R.id.img_help_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
