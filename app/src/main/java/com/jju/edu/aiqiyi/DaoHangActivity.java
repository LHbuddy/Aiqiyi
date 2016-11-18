package com.jju.edu.aiqiyi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.jju.edu.aiqiyi.adapter.GridAdapter;
import com.jju.edu.aiqiyi.entity.DaohangUtil;
import com.jju.edu.aiqiyi.tuijian.DianShiJuFragment;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.Toast;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class DaoHangActivity extends BaseActivity {
    private ImageView search;
    private GridView gridView;
    private List<DaohangUtil> olist = new ArrayList<>();
    private GridAdapter adapter;

    private String[] names = new String[]{"娱乐", "体育", "咨询", "风云榜", "直播中心", "全网影视", "电影", "片花", "网络电影", "电视剧", "综艺", "脱口秀", "动漫", "少儿", "游戏", "生活", "母婴", "健康", "音乐", "时尚", "旅游", "搞笑", "原创", "拍客", "财经", "科技", "汽车", "军事", "教育", "纪录片", "大头频道", "爱频道", "热点", "公开课", "公益", "广告", "订阅", "猜你喜欢", "附近人在看", "杜比专区"};
    private int[] imags = new int[]{R.drawable.cate_7, R.drawable.cate_17, R.drawable.cate_25, R.drawable.cate_8192, R.drawable.cate_1017, R.drawable.cate_8191, R.drawable.cate_1, R.drawable.cate_10, R.drawable.cate_16, R.drawable.cate_2, R.drawable.cate_6, R.drawable.cate_31, R.drawable.cate_4, R.drawable.cate_15, R.drawable.cate_8, R.drawable.cate_21, R.drawable.cate_29, R.drawable.cate_32, R.drawable.cate_5, R.drawable.cate_13, R.drawable.cate_9, R.drawable.cate_22, R.drawable.cate_27, R.drawable.cate_1012, R.drawable.cate_24, R.drawable.cate_30, R.drawable.cate_26, R.drawable.cate_28, R.drawable.cate_11, R.drawable.cate_3, R.drawable.cate_15, R.drawable.cate_8195, R.drawable.cate_8193, R.drawable.cate_11, R.drawable.cate_33, R.drawable.cate_20, R.drawable.cate_1024, R.drawable.cate_8194, R.drawable.cate_1023, R.drawable.cate_1015};
    private String[] paths = new String[]{"http://so.tv.sohu.com/list_p1106_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html", "http://so.tv.sohu.com/list_p1115_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html?lcode=AAAAXI_-1SzNcuUrjz4IX80Fsov7PkQ-_9HcfhP5CqXXjp0R8XfYZkr4y7lONiSf97pAsyIIUrQWfRQxqQVNGmCbWAMSePteYrBsqLZ_EWa-JV8681rfKbWwtZkwmTubY1aVEA..cf6&lqd=10051", "http://so.tv.sohu.com/list_p1101_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html", "http://so.tv.sohu.com/list_p1100_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html", "http://so.tv.sohu.com/list_p1133_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daohang_layout);
        init_view();
    }


    private void init_view() {
        search = (ImageView) findViewById(R.id.search);
        gridView = (GridView) findViewById(R.id.gridview);
        for (int i = 0; i < 5; i++) {
            DaohangUtil util = new DaohangUtil();
            util.setName(names[i]);
            util.setImage(imags[i]);
            util.setPath(paths[i]);
            olist.add(util);
        }

        adapter = new GridAdapter(olist, DaoHangActivity.this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(onItemClick);


        //设置监听
        search.setOnClickListener(new myclick());

    }

    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(DaoHangActivity.this, VideoActivity.class);
            intent.putExtra("path", olist.get(position).getPath());
            startActivity(intent);

            Toast.makeText(DaoHangActivity.this, olist.get(position).getName(), Toast.LENGTH_SHORT).show();
        }
    };

    //标题按钮监听事件
    class myclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search:
                    Toast.makeText(DaoHangActivity.this, "", Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    }
}
