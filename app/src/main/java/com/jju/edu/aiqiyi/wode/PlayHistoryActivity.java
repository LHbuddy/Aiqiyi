package com.jju.edu.aiqiyi.wode;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.R;

/**
 * Created by 凌浩 on 2016/11/17.
 */

public class PlayHistoryActivity extends BaseActivity {
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_history_layout);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
