package com.jju.edu.aiqiyi.wode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_history_layout);
        back = (ImageView) findViewById(R.id.back);
        history_text = (TextView) findViewById(R.id.history_text);
        history_list = (ListView) findViewById(R.id.history_list);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list =  MySqliteOperation.history_get_all(PageActivity.db);
       // Log.e("*************",""+list.size());


        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                PlayHistoryActivity.this).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(configuration);

        adapter = new HistoryAdapter(list,PlayHistoryActivity.this);
        history_list.setAdapter(adapter);
        history_list.setOnItemClickListener(listener);


    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(PlayHistoryActivity.this, PlayerActivity.class);
            intent.putExtra("path",list.get(position).getPath());
            startActivity(intent);

        }
    };
}
