package com.jju.edu.aiqiyi;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class WoDeActivity extends BaseActivity{
    private ImageView search,plus;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wode_layout);
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
                    Toast.makeText(WoDeActivity.this,"",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.plus:
                    View view = getLayoutInflater().inflate(R.layout.popupwindow_layout, null);
                    popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置背景
                    popupWindow.setBackgroundDrawable(new ColorDrawable());
                    //设置为可点击
                    popupWindow.setFocusable(true);
                    popupWindow.setContentView(view);
                    //设置出现在当前点击控件的正下方
                    popupWindow.showAsDropDown(plus);
                    break;

            }

        }
    }
}
