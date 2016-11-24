package com.jju.edu.aiqiyi.wode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.adapter.WallPaperAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */

public class WallPaperActivity extends Activity {

    private List<Bitmap> list = new ArrayList<Bitmap>();
    private WallPaperAdapter adapter;
    private ImageView iv_aiqiyi;
    private ImageView search,history,outline,more;
    private GridView gv_wallpaper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wallpaper_activity_layout);
        initView();
        initData();

        //初始化适配器
        adapter = new WallPaperAdapter(WallPaperActivity.this,list);
        //给GridView添加适配器
        gv_wallpaper.setAdapter(adapter);
        //设置爱奇艺点击事件
        iv_aiqiyi.setOnClickListener(onClick);
        //设置GridView的Item点击事件
        gv_wallpaper.setOnItemLongClickListener(onItemLongClick);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * GridView的Item长按事件
     */
    private AdapterView.OnItemLongClickListener onItemLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WallPaperActivity.this);
            builder.setTitle("设置为壁纸");
            builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        WallpaperManager manager = WallpaperManager.getInstance(WallPaperActivity.this);
                        manager.setBitmap(list.get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("取消",null);
            builder.show();
            return true;
        }
    };

    /**
     * onClick点击事件处理
     */
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            WallPaperActivity.this.finish();
        }
    };

    private void initData() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            Intent intent_getData = new Intent();
            intent_getData.addCategory(Intent.CATEGORY_OPENABLE);
            intent_getData.setType("image/*");
            intent_getData.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent_getData,1);
        }else {
            Toast.makeText(WallPaperActivity.this, "未检测到SD卡...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null){
            Toast.makeText(WallPaperActivity.this, "您未做出选择，无法设置壁纸！", Toast.LENGTH_SHORT).show();
        }else {
            if (requestCode==1){
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(
                            Uri.parse(uri.toString())
                    ));
                    list.add(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initView() {
        iv_aiqiyi = (ImageView) findViewById(R.id.iv_aiqiyi);
        search = (ImageView) findViewById(R.id.search);
        history = (ImageView) findViewById(R.id.history);
        outline = (ImageView) findViewById(R.id.outline);
        more = (ImageView) findViewById(R.id.more);
        gv_wallpaper = (GridView) findViewById(R.id.gv_wallpaper);

        search.setVisibility(View.INVISIBLE);
        history.setVisibility(View.INVISIBLE);
        outline.setVisibility(View.INVISIBLE);
        more.setVisibility(View.INVISIBLE);
    }
}
