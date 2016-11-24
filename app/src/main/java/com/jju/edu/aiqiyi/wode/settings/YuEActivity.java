package com.jju.edu.aiqiyi.wode.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.WoDeActivity;

/**
 * Created by Administrator on 2016/11/23.
 */

public class YuEActivity extends Activity {
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balancelayout);
        img = (ImageView) findViewById(R.id.balance_back);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YuEActivity.this, WoDeActivity.class));
            }
        });
    }
}
