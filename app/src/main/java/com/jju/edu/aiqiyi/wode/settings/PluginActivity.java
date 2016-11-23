package com.jju.edu.aiqiyi.wode.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jju.edu.aiqiyi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2016/11/23.
 */

public class PluginActivity extends Activity {

    private ImageView iv_plugin_back;
    private ListView lv_plugin;
    private SimpleAdapter adapter;
    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    private String[] desc = new String[]{"电影票","视频转码","视频编辑","路由器","传片助手","投屏助手",
            "语音搜索","啪啪奇","分享","爱奇艺文学","交友"};
    private int[] image = new int[]{R.drawable.plugin_org_qiyi_android_tickets,
            R.drawable.plugin_com_qiyi_module_plugin_ppq,
            R.drawable.plugin_com_iqiyi_share_sdk_videoedit,
            R.drawable.plugin_com_qiyi_routerplugin,
            R.drawable.plugin_org_qiyi_videotransfer,
            R.drawable.plugin_com_qiyi_plugin_qimo,
            R.drawable.plugin_com_qiyi_module_voice,
            R.drawable.plugin_com_iqiyi_plug_papaqi,
            R.drawable.plugin_com_iqiyi_share,
            R.drawable.plugin_com_qiyi_video_reader,
            R.drawable.plugin_com_iqiyi_paopao};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wode_setting_plugin_activity_layout);
        initView();
        initData();
        iv_plugin_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new SimpleAdapter(PluginActivity.this,list,
                R.layout.wode_setting_plugin_item_layout,
                new String[]{"image","desc"},
                new int[]{R.id.iv_plugin_item,R.id.tv_plugin_item});
        lv_plugin.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image",image[i]);
            map.put("desc",desc[i]);
            list.add(map);
        }
    }

    private void initView() {
        iv_plugin_back = (ImageView) findViewById(R.id.iv_plugin_title_back);
        lv_plugin = (ListView) findViewById(R.id.lv_plugin);
    }
}
