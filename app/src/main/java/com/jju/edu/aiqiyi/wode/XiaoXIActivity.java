package com.jju.edu.aiqiyi.wode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.jju.edu.aiqiyi.R;

/**
 * Created by Administrator on 2016/11/22.
 */
public class XiaoXIActivity extends Activity{

    private ImageView iv_xiaoxi_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wode_xiaoxi_activity_layout);
        iv_xiaoxi_back = (ImageView) findViewById(R.id.iv_xiaoxi_back);
        iv_xiaoxi_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
