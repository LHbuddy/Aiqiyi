package com.jju.edu.aiqiyi.wode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.VideoPlayer;
import com.jju.edu.aiqiyi.adapter.VideoAdapter;
import com.jju.edu.aiqiyi.entity.AbstructProvider;
import com.jju.edu.aiqiyi.entity.LoadedImage;
import com.jju.edu.aiqiyi.entity.Video;
import com.jju.edu.aiqiyi.entity.VideoProvider;

import java.util.List;

/**
 * Created by 凌浩 on 2016/11/17.
 */

public class LocalVideoActivity extends BaseActivity {
    private ListView mlv_list;
    public LocalVideoActivity instance = null;
    private VideoAdapter mVideoAdapter;
    private List<Video> olist_videos;
    private int video_size;
    private ImageView back, update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_video_layout);
        instance = this;
        init_view();
        back = (ImageView) findViewById(R.id.back);
        update = (ImageView) findViewById(R.id.update);
        back.setOnClickListener(new myonclick());
        update.setOnClickListener(new myonclick());
    }

    //图片按钮监听事件
    class myonclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back:
                    finish();
                    break;
            }
        }
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.setClass(LocalVideoActivity.this, VideoPlayer.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("video", olist_videos.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    /**
     * Load images.
     */
    private void loadImages() {
        final Object data = getLastNonConfigurationInstance();
        if (data == null) {
            new LoadImagesFromSDCard().execute();
        } else {
            final LoadedImage[] photos = (LoadedImage[]) data;
            if (photos.length == 0) {
                new LoadImagesFromSDCard().execute();
            }
            for (LoadedImage photo : photos) {
                addImage(photo);
            }
        }
    }

    private void addImage(LoadedImage... value) {
        for (LoadedImage image : value) {
            mVideoAdapter.addPhoto(image);
            mVideoAdapter.notifyDataSetChanged();
        }
    }


//    public Object onRetainNonConfigurationInstance(){
//        final ListView grid = mlv_list;
//        final int count = grid.getChildCount();
//        final LoadedImage[] list = new LoadedImage[count];
//
//        for (int i = 0; i < count; i++) {
//            final ImageView v = (ImageView) grid.getChildAt(i);
//            list[i] = new LoadedImage(
//                    ((BitmapDrawable) v.getDrawable()).getBitmap());
//        }
//
//        return list;
//    }

    /**
     * 获取视频缩略图
     *
     * @param videoPath
     * @param width
     * @param height
     * @param kind
     * @return
     */
    private Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    class LoadImagesFromSDCard extends AsyncTask<Object, LoadedImage, Object> {
        @Override
        protected Object doInBackground(Object... params) {
            Bitmap bitmap = null;
            for (int i = 0; i < video_size; i++) {
                bitmap = getVideoThumbnail(olist_videos.get(i).getPath(), 120, 120, MediaStore.Video.Thumbnails.MINI_KIND);
                if (bitmap != null) {
                    publishProgress(new LoadedImage(bitmap));
                }
            }
            return null;
        }

        @Override
        public void onProgressUpdate(LoadedImage... value) {
            addImage(value);
        }
    }

    private void init_view() {
        mlv_list = (ListView) findViewById(R.id.lv_list);
        AbstructProvider provider = new VideoProvider(instance);
        olist_videos = provider.getList();
        video_size = olist_videos.size();
        mVideoAdapter = new VideoAdapter(olist_videos, this);
        mlv_list.setAdapter(mVideoAdapter);
        mlv_list.setOnItemClickListener(listener);
        loadImages();
    }

}
