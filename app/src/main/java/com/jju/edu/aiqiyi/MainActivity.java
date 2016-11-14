package com.jju.edu.aiqiyi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    private RelativeLayout lin;
    private LinearLayout but;
    private TextView time;
    private int count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lin = (RelativeLayout) findViewById(R.id.lin);
        but = (LinearLayout) findViewById(R.id.but);
        time = (TextView) findViewById(R.id.time);

        lin.setBackgroundResource(R.drawable.phone_welcome_bg);

        handler.sendEmptyMessage(0x123);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                handler.sendEmptyMessage(0x123);
            }
        });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0x123){
                if (count==0){
                    startActivity(new Intent(MainActivity.this,PageActivity.class));
                    handler.removeMessages(0x123);
                    finish();
                }else {
                    count--;
                    time.setText(count+"");
                    handler.sendEmptyMessageDelayed(0x123,1000);
                }
            }
        }
    };
}
