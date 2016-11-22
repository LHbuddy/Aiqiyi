package com.jju.edu.aiqiyi.wode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.PageActivity;
import com.jju.edu.aiqiyi.PlayerActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.adapter.HistoryAdapter;
import com.jju.edu.aiqiyi.sqlite.MySqliteOperation;
import com.jju.edu.aiqiyi.util.HistoryUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;

/**
 * Created by 凌浩 on 2016/11/17.
 */

public class PlayHistoryActivity extends BaseActivity {
    private ImageView back;
    private List<HistoryUtil> list;
    private TextView history_text;
    private ListView history_list;
    private HistoryAdapter adapter;
    private TextView clear_all;
    private TextView text01;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_history_layout);
        back = (ImageView) findViewById(R.id.back);
        history_text = (TextView) findViewById(R.id.history_text);
        history_list = (ListView) findViewById(R.id.history_list);
        clear_all = (TextView) findViewById(R.id.clear_all);
        text01 = (TextView) findViewById(R.id.text01);

        back.setOnClickListener(new myonclick());
        clear_all.setOnClickListener(new myonclick());


        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                PlayHistoryActivity.this).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(configuration);


    }

    //列表点击事件
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(PlayHistoryActivity.this, PlayerActivity.class);
            intent.putExtra("path", list.get(position).getPath());
            startActivity(intent);
        }
    };
    //列表长按事件
    AdapterView.OnItemLongClickListener listener_long = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(PlayHistoryActivity.this);
            dialog.setTitle("提示");
            dialog.setMessage("确定删除该信息？");
            dialog.setNegativeButton("取消", null);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MySqliteOperation.history_delete_one(PageActivity.db, list.get(position).getPath(), uid);
                    adapter.notifyDataSetChanged();
                    history_list.setSelection(0);
                    onResume();
                    Toast.makeText(PlayHistoryActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
            return true;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        uid = LoginActivity.uid_get;
        //获取列表
        list = MySqliteOperation.history_get_all(PageActivity.db, uid);
        // Log.e("*************",""+list.size());
        adapter = new HistoryAdapter(list, PlayHistoryActivity.this);
        history_list.setAdapter(adapter);
        history_list.setOnItemClickListener(listener);
        history_list.setOnItemLongClickListener(listener_long);

        if (list.size() == 0) {
            history_text.setText("暂无播放记录");
            text01.setText("");
            clear_all.setText("");

        } else {
            history_text.setText("");
            text01.setText("播放历史");
            clear_all.setText("清除所有历史");

        }

    }

    class myonclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back:
                    finish();
                    break;
                case R.id.clear_all:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(PlayHistoryActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("确定删除所有信息？");
                    dialog.setNegativeButton("取消", null);
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MySqliteOperation.history_delete_all(PageActivity.db, uid);
                            adapter.notifyDataSetChanged();
                            history_list.setSelection(0);
                            onResume();
                            Toast.makeText(PlayHistoryActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                    break;

            }

        }
    }
}
