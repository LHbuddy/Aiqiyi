package com.jju.edu.aiqiyi.wode;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.R;

/**
 * Created by 凌浩 on 2016/11/17.
 */

public class LocalVideoActivity extends BaseActivity {

    private ImageView back,update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_video_layout);

        back = (ImageView) findViewById(R.id.back);
        update = (ImageView) findViewById(R.id.update);
        back.setOnClickListener(new myonclick());
        update.setOnClickListener(new myonclick());
    }
    //图片按钮监听事件
    class myonclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    finish();
                    break;
            }
        }
    }
}
