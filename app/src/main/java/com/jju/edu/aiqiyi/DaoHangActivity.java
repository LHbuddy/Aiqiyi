package com.jju.edu.aiqiyi;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class DaoHangActivity extends BaseActivity{
    private ImageView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daohang_layout);
        search = (ImageView) findViewById(R.id.search);
        //设置监听
        search.setOnClickListener(new myclick());

    }
    //标题按钮监听事件
    class myclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search:
                    Toast.makeText(DaoHangActivity.this,"",Toast.LENGTH_SHORT).show();
                break;

            }

        }
    }
}
