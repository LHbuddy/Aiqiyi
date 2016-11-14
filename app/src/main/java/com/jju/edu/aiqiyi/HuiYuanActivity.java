package com.jju.edu.aiqiyi;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class HuiYuanActivity extends BaseActivity{
    private ImageView search,plus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huiyuan_layout);
        search = (ImageView) findViewById(R.id.search);
        plus = (ImageView) findViewById(R.id.plus);
        //开始监听
        search.setOnClickListener(new myclick());
        plus.setOnClickListener(new myclick());
    }
    //标题按钮监听事件
    class myclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search:
                    Toast.makeText(HuiYuanActivity.this,"",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.plus:
                    Toast.makeText(HuiYuanActivity.this,"",Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    }
}
