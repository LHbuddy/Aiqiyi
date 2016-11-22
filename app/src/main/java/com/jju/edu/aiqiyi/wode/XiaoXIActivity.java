package com.jju.edu.aiqiyi.wode;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.jju.edu.aiqiyi.R;

/**
 * Created by Administrator on 2016/11/22.
 */
public class XiaoXIActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wode_xiaoxi_activity_layout);
    }
}
